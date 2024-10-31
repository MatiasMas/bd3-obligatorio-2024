package persistencia.daos;

import java.util.ArrayList;
import java.util.List;

import logica.entidades.Folio;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;

public class DAOArchivoFolios implements IDAOFolios {
	public boolean member (String codigo) throws PersistenciaException;
	public void insert (Folio fol)throws PersistenciaException;
	public Folio find (String codigo)throws PersistenciaException;
	public List<VOFolio> listarFolios ()throws PersistenciaException;
	public void delete(String cod) throws PersistenciaException ;
	public boolean esVacio() throws PersistenciaException;
	public VOFolioMaxRev folioMasRevisado() throws PersistenciaException;
	
}
