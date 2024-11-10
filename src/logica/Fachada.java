package logica;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import logica.entidades.Folio;
import logica.entidades.Revision;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.FolioYaExisteException;
import logica.excepciones.InstanciacionException;
import logica.excepciones.NoExistenFoliosException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.RevisionNoExisteException;
import logica.valueObjects.VOBorrarFolio;
import logica.valueObjects.VODarDescripcion;
import logica.valueObjects.VODescripcionRetornada;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import logica.valueObjects.VOListarRevisiones;
import logica.valueObjects.VORevision;
import persistencia.abstractFactory.IFabricaAbstracta;
import persistencia.daos.IDAOFolios;
import poolConexiones.IConexion;
import poolConexiones.IPoolConexiones;
import utilidades.Configuracion;

public class Fachada extends UnicastRemoteObject implements IFachada {

	private static final long serialVersionUID = 1L;
	private IDAOFolios diccio;
	private static Fachada instancia;
	private IPoolConexiones pool;

	public Fachada() throws RemoteException, InstanciacionException {
		super();
		String nomFab = Configuracion.getInstancia().getMetodoPersistencia();

		try {
			IFabricaAbstracta fabrica = (IFabricaAbstracta) Class.forName(nomFab).newInstance();

			diccio = fabrica.crearIDAOFolios();
			pool = fabrica.crearIPoolConexiones();
		} catch (Exception e) {
			throw new InstanciacionException(e.getMessage());
		}
	}

	public static Fachada getInstancia() throws InstanciacionException {
		try {
			if (instancia == null)
				instancia = new Fachada();
			return instancia;
		} catch (Exception e) {
			throw new InstanciacionException(e.getMessage());
		}
	}

	public void agregarFolio(VOFolio voF)
			throws RemoteException, FolioYaExisteException, PersistenciaException, InstanciacionException {
		boolean errorPersistencia = false;
		boolean existeFolio = false;
		String msgError = null;
		IConexion icon = null;
		boolean errorGenerico = false;

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
		} catch (Exception e) {
			errorGenerico = true;
			msgError = "Ha habido un problema en la instanciacion";

			pool.liberarConexion(icon, false);
		} finally {
			if (existeFolio)
				throw new FolioYaExisteException(msgError);
			if (errorPersistencia)
				throw new PersistenciaException(msgError);
			if (errorGenerico)
				throw new InstanciacionException(msgError);
		}
	}

	public void agregarRevision(VORevision voR)
			throws RemoteException, PersistenciaException, FolioNoExisteException, InstanciacionException {
		String msgError = null;
		boolean noExisteFolio = false;
		boolean errorPersistencia = false;
		IConexion icon = null;
		boolean errorGenerico = false;

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
		} catch (PersistenciaException e) {
			errorPersistencia = true;
			msgError = "Error de persistencia";

			pool.liberarConexion(icon, false);
		} catch (Exception e) {
			errorGenerico = true;
			msgError = "Ha habido un problema en la instanciacion";

			pool.liberarConexion(icon, false);
		} finally {
			if (noExisteFolio)
				throw new FolioNoExisteException(msgError);
			if (errorPersistencia)
				throw new PersistenciaException(msgError);
			if (errorGenerico)
				throw new InstanciacionException(msgError);
		}
	}

	public void borrarFolioRevisiones(VOBorrarFolio voF)
			throws RemoteException, PersistenciaException, FolioNoExisteException, InstanciacionException {
		String msgError = null;
		boolean noExisteFolio = false;
		boolean errorPersistencia = false;
		IConexion icon = null;
		boolean errorGenerico = false;

		try {
			icon = pool.obtenerConexion(true);
			if (diccio.member(icon, voF.getCodFolio())) {
				diccio.delete(icon, voF.getCodFolio());
			} else {
				noExisteFolio = true;
				msgError = "Folio NO existe";
			}

			pool.liberarConexion(icon, true);
		} catch (PersistenciaException e) {
			errorPersistencia = true;
			msgError = "Error de persistencia";

			pool.liberarConexion(icon, false);
		} catch (Exception e) {
			errorGenerico = true;
			msgError = "Ha habido un problema en la instanciacion";

			pool.liberarConexion(icon, false);
		} finally {
			if (noExisteFolio)
				throw new FolioNoExisteException(msgError);
			if (errorPersistencia)
				throw new PersistenciaException(msgError);
			if (errorGenerico)
				throw new InstanciacionException(msgError);
		}
	}

	public VODescripcionRetornada darDescripcion(VODarDescripcion voD) throws RemoteException, PersistenciaException,
			FolioNoExisteException, RevisionNoExisteException, InstanciacionException {
		String msgError = null;
		VODescripcionRetornada voDescripcion = null;
		boolean noExisteFolio = false;
		boolean noExisteRevision = false;
		boolean errorPersistencia = false;
		IConexion icon = null;
		boolean errorGenerico = false;

		try {
			icon = pool.obtenerConexion(true);

			if (diccio.member(icon, voD.getCodFolio())) {
				Folio fol = diccio.find(icon, voD.getCodFolio());
				if (fol.tieneRevision(icon, voD.getNumRevision())) {
					Revision rev = fol.obtenerRevision(icon, voD.getNumRevision());
					voDescripcion = rev.getVoDescripcion();
				} else {
					noExisteRevision = true;
					msgError = "No existe una revision con ese numero";
				}
			} else {
				noExisteFolio = true;
				msgError = "Folio no existe";
			}

			pool.liberarConexion(icon, true);
		} catch (PersistenciaException e) {
			errorPersistencia = true;
			msgError = "Error de persistencia";

			pool.liberarConexion(icon, false);
		} catch (Exception e1) {
			errorGenerico = true;
			msgError = "Ha habido un problema en la instanciacion";

			pool.liberarConexion(icon, false);
		} finally {
			if (noExisteFolio)
				throw new FolioNoExisteException(msgError);
			if (noExisteRevision)
				throw new RevisionNoExisteException(msgError);
			if (errorPersistencia)
				throw new PersistenciaException(msgError);
			if (errorGenerico)
				throw new InstanciacionException(msgError);
		}

		return voDescripcion;
	}

	public List<VOFolio> listarFolios() throws RemoteException, PersistenciaException, InstanciacionException {
		String msgError = null;
		boolean errorPersistencia = false;
		boolean errorGenerico = false;
		IConexion icon = null;
		List<VOFolio> folios = null;

		try {
			icon = pool.obtenerConexion(true);
			folios = diccio.listarFolios(icon);

			pool.liberarConexion(icon, true);
		} catch (PersistenciaException e) {
			errorPersistencia = true;
			msgError = "Error de persistencia";

			pool.liberarConexion(icon, false);
		} catch (Exception e1) {
			errorGenerico = true;
			msgError = "Ha habido un problema en la instanciacion";

			pool.liberarConexion(icon, false);
		} finally {
			if (errorPersistencia)
				throw new PersistenciaException(msgError);
			if (errorGenerico)
				throw new InstanciacionException(msgError);
		}

		return folios;
	}

	public List<VORevision> listarRevisiones(VOListarRevisiones voL)
			throws RemoteException, PersistenciaException, FolioNoExisteException, InstanciacionException {
		String msgError = null;
		boolean errorPersistencia = false;
		boolean errorGenerico = false;
		boolean noExisteFolio = false;
		IConexion icon = null;
		Folio folio = null;
		List<VORevision> listaRevisiones = null;

		try {
			icon = pool.obtenerConexion(false);

			if (!diccio.member(icon, voL.getCodFolio())) {
				noExisteFolio = true;
				msgError = "Folio no existe";
			} else {
				folio = diccio.find(icon, voL.getCodFolio());
				listaRevisiones = folio.listarRevisiones(icon);
			}

			pool.liberarConexion(icon, true);
		} catch (PersistenciaException e) {
			errorPersistencia = true;
			msgError = "Error de persistencia";

			pool.liberarConexion(icon, false);
		} catch (Exception e1) {
			errorGenerico = true;
			msgError = "Ha habido un problema en la instanciacion";

			pool.liberarConexion(icon, false);
		} finally {
			if (noExisteFolio)
				throw new FolioNoExisteException(msgError);
			if (errorPersistencia)
				throw new PersistenciaException(msgError);
			if (errorGenerico)
				throw new InstanciacionException(msgError);
		}

		return listaRevisiones;
	}

	public VOFolioMaxRev folioMasRevisado()
			throws RemoteException, PersistenciaException, NoExistenFoliosException, InstanciacionException {
		String msgError = null;
		boolean errorPersistencia = false;
		boolean errorGenerico = false;
		boolean noExistenFolio = false;
		IConexion icon = null;
		VOFolioMaxRev folioMasRevisado = null;

		try {
			icon = pool.obtenerConexion(true);

			if (diccio.esVacio(icon)) {
				noExistenFolio = true;
				msgError = "No existen folios";
			} else {
				folioMasRevisado = diccio.folioMasRevisado(icon);
			}

			pool.liberarConexion(icon, true);
		} catch (PersistenciaException e) {
			errorPersistencia = true;
			msgError = "Error de persistencia";

			pool.liberarConexion(icon, false);
		} catch (Exception e1) {
			errorGenerico = true;
			msgError = "Ha habido un problema en la instanciacion";

			pool.liberarConexion(icon, false);
		} finally {
			if (noExistenFolio)
				throw new NoExistenFoliosException(msgError);
			if (errorPersistencia)
				throw new PersistenciaException(msgError);
			if (errorGenerico)
				throw new InstanciacionException(msgError);
		}

		return folioMasRevisado;
	}
}
