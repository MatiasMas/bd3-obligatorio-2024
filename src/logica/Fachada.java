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
import persistencia.daos.DAOFolios;

public class Fachada extends java.rmi.server.UnicastRemoteObject implements IFachada {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Esta tiene que ser el DAO ahora
	private DAOFolios diccio = new DAOFolios();
	private static Fachada instancia;

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
	}

	public static Fachada getInstancia() throws PersistenciaException, ClassNotFoundException, FileNotFoundException,
			InstantiationException, IllegalAccessException, IOException, RemoteException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		if (instancia == null)
			instancia = new Fachada();
		return instancia;
	}

	public void agregarFolio(VOFolio voF) throws RemoteException, PersistenciaException, FolioYaExisteException {

		String codigo = voF.getCodigo();
		
		if (!diccio.member(codigo)) {
			
			String caratula = voF.getCaratula();
			int paginas = voF.getPaginas();
			Folio folio = new Folio(codigo, caratula, paginas);
			
			diccio.insert(folio);
		} else
			throw new FolioYaExisteException();
	}

	public void agregarRevision(VORevision voR) throws RemoteException, PersistenciaException, FolioNoExisteException {
		Folio folio;
		Revision rev;

		if (!diccio.member(voR.getCodFolio())) {
			throw new FolioNoExisteException();
		}

		folio = diccio.find(voR.getCodFolio());
		rev = new Revision(voR.getNumero(), voR.getDescripcion());

		folio.addRevision(rev);
	}

	public void borrarFolioRevisiones(VOBorrarFolio voF)
			throws RemoteException, PersistenciaException, FolioNoExisteException {
		if (!diccio.member(voF.getCodFolio())) {
			throw new FolioNoExisteException();
		}

		diccio.delete(voF.getCodFolio());
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