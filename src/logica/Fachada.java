package logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Properties;

import logica.entidades.Folio;
import logica.entidades.Revision;
import logica.excepciones.DarDescripcionException;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.FolioYaExisteException;
import logica.excepciones.NoExistenFoliosException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOBorrarFolio;
import logica.valueObjects.VODarDescripcion;
import logica.valueObjects.VODescripcionRetornada;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import logica.valueObjects.VOListarRevisiones;
import logica.valueObjects.VORevision;
import poolConexiones.IConexion;
import poolConexiones.IPoolConexiones;
import persistencia.daos.DAOFolios;
import persistencia.daos.DAORevisiones;
import persistencia.daos.IDAOFolios;

public class Fachada extends java.rmi.server.UnicastRemoteObject implements IFachada {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Esta tiene que ser el DAO ahora
	private DAOFolios diccio = new DAOFolios();
	private static Fachada instancia;
	private IPoolConexiones pool;

	// TODO: Juntar excepciones en una personalizada
	public Fachada() throws RemoteException, InstantiationException, ClassNotFoundException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		super();

		String nomPool = null;

		try {
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("config/server.properties"));
			nomPool = propiedades.getProperty("pool");
		} catch (FileNotFoundException e) {
			System.out.println("Error, el archivo de configuracion no existe!");
		} catch (IOException e) {
			System.out.println("Error, no se puede leer el archivo de configuracion!");
		}
		pool = (IPoolConexiones) Class.forName(nomPool).newInstance();
	}

	public static Fachada getInstancia() throws PersistenciaException, ClassNotFoundException, FileNotFoundException,
			InstantiationException, IllegalAccessException, IOException, RemoteException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		if (instancia == null)
			instancia = new Fachada();
		return instancia;
	}

	public void agregarFolio(VOFolio voF) throws RemoteException, PersistenciaException, FolioYaExisteException {

		boolean errorPersistencia = false;
		boolean existeFolio = false;
		String msgError = null;
		IConexion icon = null;

		try {

			String codigo = voF.getCodigo();
			icon = pool.obtenerConexion(true);
			if (!diccio.member(icon, codigo)) {
				String caratula = voF.getCaratula();
				int paginas = voF.getPaginas();
				Folio folio = new Folio(codigo, caratula, paginas);
				diccio.insert(icon, folio);
			} else {
				existeFolio = true;
				msgError = "Folio ya existe";
			}
			pool.liberarConexion(icon, true);

		} catch (Exception e) {
			errorPersistencia = true;
			msgError = "Error de acceso a los datos";
		} finally {
			if (existeFolio)
				throw new FolioYaExisteException(msgError);
			if (errorPersistencia)
				throw new PersistenciaException(msgError);
		}
	}

	public void agregarRevision(VORevision voR) throws RemoteException, PersistenciaException, FolioNoExisteException {
		String msgError = null;
		boolean noExisteFolio = false;
		boolean errorPersistencia = false;
		IConexion icon = null;
		try {
			icon = pool.obtenerConexion(true);
			if (diccio.member(icon, voR.getCodFolio())) {
				Folio folio = diccio.find(icon, voR.getCodFolio());
				int numero = folio.cantidadRevisiones(icon) + 1;

				// Crea nueva revision
				Revision rev = new Revision(numero, voR.getDescripcion());
				folio.addRevision(icon, rev);
			} else {
				noExisteFolio = true;
				msgError = "Folio no existe";
			}
			pool.liberarConexion(icon, true);

		} catch (Exception e) {
			e.printStackTrace();
			errorPersistencia = true;
			msgError = "Error de persistencia";
		} finally {
			if (noExisteFolio)
				throw new FolioNoExisteException(msgError);
			if (errorPersistencia) {
				throw new PersistenciaException(msgError);
			}
		}
	}

	public void borrarFolioRevisiones(VOBorrarFolio voF)
			throws RemoteException, PersistenciaException, FolioNoExisteException {

		String msgError = null;
		boolean noExisteFolio = false;
		boolean errorPersistencia = false;
		IConexion icon = null;

		try {
			icon = pool.obtenerConexion(true);
			if (diccio.member(icon, voF.getCodFolio())) {
				// Primero elimino revisiones
//				DAORevisiones dicRevisiones = new DAORevisiones();
				diccio.delete(icon, voF.getCodFolio());
			} else {
				noExisteFolio = true;
				msgError = "Folio NO existe";
			}
			pool.liberarConexion(icon, true);
		} catch (Exception e) {
			e.printStackTrace();
			errorPersistencia = true;
			msgError = "Error de persistencia";
		} finally {
			if (noExisteFolio)
				throw new FolioNoExisteException(msgError);
			if (errorPersistencia) {
				throw new PersistenciaException(msgError);
			}
		}
	}

	public String darDescripcion(VODarDescripcion voD)
			throws RemoteException, PersistenciaException, FolioNoExisteException, DarDescripcionException {

		String msgError = null;
		String descripcion = null;
		boolean noExisteFolio = false;
		boolean errorConexion = false;
		boolean noExisteRevision = false;
		IConexion icon = null;

		try {
			icon = pool.obtenerConexion(true);
			if (diccio.member(icon, voD.getCodFolio())) {
				DAOFolios dicFilio = new DAOFolios();
				Folio fol = dicFilio.find(icon, voD.getCodFolio());
				if (fol.tieneRevision(voD.getNumRevision())) {
					Revision rev = fol.obtenerRevision(voD.getNumRevision());
					descripcion = rev.getDescripcion();
				} else {
					noExisteRevision = true;
					msgError = "No existe una revision con ese n�mero";
				}
			} else {
				noExisteFolio = true;
				msgError = "Folio no existe";
			}

		} catch (PersistenciaException e) {
			throw new DarDescripcionException(msgError);
		} catch (Exception e1) {
			errorConexion = true;
			msgError = "Error de persistencia";
		} finally {
			if (noExisteFolio)
				throw new DarDescripcionException(msgError);
			if (noExisteRevision)
				throw new DarDescripcionException(msgError);
			if (errorConexion)
				throw new DarDescripcionException(msgError);
		}

		return descripcion;
	}

	public List<VOFolio> listarFolios() throws RemoteException, PersistenciaException, FolioNoExisteException {
		return diccio.listarFolios();
	}

	public List<VORevision> listarRevisiones(VOListarRevisiones voL)
			throws RemoteException, PersistenciaException, FolioNoExisteException {
		IConexion icon = null;
		icon = pool.obtenerConexion(true);
		if (!diccio.member(icon, voL.getCodFolio())) {
			throw new FolioNoExisteException();
		}

		Folio folio = diccio.find(icon, voL.getCodFolio());

		return folio.listarRevisiones();
	}

	public VOFolioMaxRev folioMasRevisado() throws RemoteException, PersistenciaException, NoExistenFoliosException {
		IConexion icon = null;
		icon = pool.obtenerConexion(true);
		if (diccio.esVacio()) {
			throw new NoExistenFoliosException();
		}

		return diccio.folioMasRevisado(icon);
	}
}