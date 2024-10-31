package persistencia.daos;

import java.util.List;

import logica.entidades.Folio;
import logica.entidades.Revision;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VORevision;



public class DAOArchivoRevisiones implements IDAORevisiones{

	@Override
	public void insback(Revision rev) throws PersistenciaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int largo() throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Revision kesimo(int numero) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VORevision> listarRevisiones() throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void borrarRevisiones() throws PersistenciaException {
		// TODO Auto-generated method stub
		
	}

	

}
