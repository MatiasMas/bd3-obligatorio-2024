package persistencia.daos;

import java.util.ArrayList;
import java.util.List;

import logica.entidades.Folio;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;

public class DAOArchivoFolios implements IDAOFolios {

	@Override
	public boolean member(String codigo) throws PersistenciaException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void insert(Folio fol) throws PersistenciaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Folio find(String codigo) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VOFolio> listarFolios() throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String cod) throws PersistenciaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean esVacio() throws PersistenciaException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public VOFolioMaxRev folioMasRevisado() throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}
	
	//hay que implementar todos estos metodos pero para archivos

}
