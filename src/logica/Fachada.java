package logica;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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

public class Fachada {
	// Esta tiene que ser el DAO ahora
	private DAOFolios diccio = new DAOFolios();

	public Fachada() {

	}

	public void agregarFolio(VOFolio voF) throws RemoteException, PersistenciaException, FolioYaExisteException {
		Folio folio = new Folio(voF.getCodigo(), voF.getCaratula(), voF.getPaginas());
		
		if (diccio.member(voF.getCodigo())) {
			throw new FolioYaExisteException();
		}

		diccio.insert(folio);
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

	public void borrarFolioRevisiones(VOBorrarFolio voF) throws RemoteException, PersistenciaException, FolioNoExisteException {
		if (!diccio.member(voF.getCodFolio())) {
			throw new FolioNoExisteException();
		}
		
		diccio.delete(voF.getCodFolio());
	}

	public VODescripcionRetornada darDescripcion(VODarDescripcion voD) throws RemoteException, PersistenciaException, FolioNoExisteException {
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

	public List<VORevision> listarRevisiones(VOListarRevisiones voL) throws RemoteException, PersistenciaException, FolioNoExisteException {
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