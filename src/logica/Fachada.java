package logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;

import logica.entidades.Folio;
import logica.entidades.Revision;
import logica.excepciones.DarDescripcionException;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.FolioYaExisteException;
import logica.excepciones.NoExistenFoliosException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOBorrarFolio;
import logica.valueObjects.VODarDescripcion;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import logica.valueObjects.VOListarRevisiones;
import logica.valueObjects.VORevision;
import persistencia.abstractFactory.IFabricaAbstracta;
import persistencia.daos.DAOFoliosMySQL;
import persistencia.daos.IDAOFolios;
import poolConexiones.IConexion;
import poolConexiones.IPoolConexiones;
import utilidades.Configuracion;

public class Fachada extends java.rmi.server.UnicastRemoteObject implements IFachada {

	private static final long serialVersionUID = 1L;
	private IDAOFolios diccio;
	private static Fachada instancia;
	private IPoolConexiones pool;

	// TODO: Juntar excepciones en una personalizada
	public Fachada() throws RemoteException, InstantiationException, ClassNotFoundException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, PersistenciaException {
		super();

		String nomFab = Configuracion.getInstancia().getMetodoPersistencia();
		IFabricaAbstracta fabrica = (IFabricaAbstracta) Class.forName(nomFab).newInstance();
		diccio = fabrica.crearIDAOFolios();
		pool = fabrica.crearIPoolConexiones();
	}

	public static Fachada getInstancia() throws PersistenciaException, ClassNotFoundException, FileNotFoundException,
			InstantiationException, IllegalAccessException, IOException, RemoteException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		if (instancia == null)
			instancia = new Fachada();
		return instancia;
	}

	public void agregarFolio(VOFolio voF) throws RemoteException, PersistenciaException, FolioYaExisteException, InstantiationException, IllegalAccessException, ClassNotFoundException {

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

		} catch (PersistenciaException e) {
			errorPersistencia = true;
			msgError = "Error de acceso a los datos";
			pool.liberarConexion(icon, false);
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
			pool.liberarConexion(icon, false);
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
			pool.liberarConexion(icon, false);
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
				DAOFoliosMySQL dicFilio = new DAOFoliosMySQL();
				Folio fol = dicFilio.find(icon, voD.getCodFolio());
				if (fol.tieneRevision(icon, voD.getNumRevision())) {
					Revision rev = fol.obtenerRevision(icon, voD.getNumRevision());
					descripcion = rev.getDescripcion();
				} else {
					noExisteRevision = true;
					msgError = "No existe una revision con ese n�mero";
				}
			} else {
				noExisteFolio = true;
				msgError = "Folio no existe";
			}
			pool.liberarConexion(icon, true);

		} catch (PersistenciaException e) {
			throw new DarDescripcionException(msgError);
		} catch (Exception e1) {
			errorConexion = true;
			msgError = "Error de persistencia";
			pool.liberarConexion(icon, false);
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
		IConexion icon = pool.obtenerConexion(true);
		List<VOFolio> folios = diccio.listarFolios(icon);
		pool.liberarConexion(icon, true);
		return folios;
	}

	public List<VORevision> listarRevisiones(VOListarRevisiones voL)
			throws RemoteException, PersistenciaException, FolioNoExisteException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		IConexion icon = pool.obtenerConexion(false);

		if (!diccio.member(icon, voL.getCodFolio())) {
			pool.liberarConexion(icon, false);
			throw new FolioNoExisteException();
		}
			
		Folio folio = diccio.find(icon, voL.getCodFolio());

		pool.liberarConexion(icon, true);

		return folio.listarRevisiones(icon);
	}

	public VOFolioMaxRev folioMasRevisado() throws RemoteException, PersistenciaException, NoExistenFoliosException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		IConexion icon = null;
		icon = pool.obtenerConexion(true);

		if (diccio.esVacio(icon)) {
			throw new NoExistenFoliosException();
		}

		pool.liberarConexion(icon, true);

		return diccio.folioMasRevisado(icon);
	}

}
