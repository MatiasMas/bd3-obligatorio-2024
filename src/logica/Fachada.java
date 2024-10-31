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
			// System.out.print(propiedades.getProperty("fabrica"));
		} catch (FileNotFoundException e) {
			System.out.println("Error, el archivo de configuracion no existe!");
		} catch (IOException e) {
			System.out.println("Error, no se puede leer el archivo de configuracion!");
		}
		pool = (IPoolConexiones) Class.forName(nomPool).getDeclaredConstructor().newInstance();
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
//		IConexion icon = null;

		try {

			String codigo = voF.getCodigo();
			existeFolio = diccio.member(codigo);

			if (!diccio.member(codigo)) {
//				icon = pool.obtenerConexion(true);
				String caratula = voF.getCaratula();
				int paginas = voF.getPaginas();
				Folio folio = new Folio(codigo, caratula, paginas);

				diccio.insert(folio);
			} else
				msgError = "Folio ya existe";

		} catch (Exception e) {
//			pool.liberarConexion(icon, false);
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

		try {
			if (diccio.member(voR.getCodFolio())) {
				Folio folio = diccio.find(voR.getCodFolio());
				int numero = folio.cantidadRevisiones() + 1;

				// Crea nueva revision
				Revision rev = new Revision(numero, voR.getDescripcion());
				DAORevisiones dicRevision = new DAORevisiones(voR.getCodFolio());
				dicRevision.insback(rev);
			} else {
				noExisteFolio = true;
				msgError = "Folio no existe";
			}

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

		try {
			if (diccio.member(voF.getCodFolio())) {

				// Primero elimino revisiones
				DAORevisiones dicRevisiones = new DAORevisiones();
				dicRevisiones.borrarRevisiones();

				// Luego elimino Folio
				DAOFolios dicFilio = new DAOFolios();
				dicFilio.delete(voF.getCodFolio());

			} else {
				noExisteFolio = true;
				msgError = "Folio NO existe";
			}
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
//		if (!diccio.member(voF.getCodFolio())) {
//			throw new FolioNoExisteException();
//		}
//
//		diccio.delete(voF.getCodFolio());
	}

	public VODescripcionRetornada darDescripcion(VODarDescripcion voD)
			throws RemoteException, PersistenciaException, FolioNoExisteException {
		if (!diccio.member(voD.getCodFolio())) {
			throw new FolioNoExisteException();
		}

		Folio folio = diccio.find(voD.getCodFolio());
		Revision revision = folio.obtenerRevision(voD.getNumRevision());

		return new VODescripcionRetornada(revision.getDescripcion());
	}

	public List<VOFolio> listarFolios() throws RemoteException, PersistenciaException, FolioNoExisteException {
		return diccio.listarFolios();
	}

	public List<VORevision> listarRevisiones(VOListarRevisiones voL)
			throws RemoteException, PersistenciaException, FolioNoExisteException {
		if (!diccio.member(voL.getCodFolio())) {
			throw new FolioNoExisteException();
		}

		Folio folio = diccio.find(voL.getCodFolio());

		return folio.listarRevisiones();
	}

	public VOFolioMaxRev folioMasRevisado() throws RemoteException, PersistenciaException, NoExistenFoliosException {
		if (diccio.esVacio()) {
			throw new NoExistenFoliosException();
		}

		return diccio.folioMasRevisado();
	}
}