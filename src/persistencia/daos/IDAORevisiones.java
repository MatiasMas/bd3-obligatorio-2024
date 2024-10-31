package persistencia.daos;

import java.util.List;

import logica.entidades.Revision;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VORevision;

public interface IDAORevisiones {
	public void insback(Revision rev) throws PersistenciaException;
	public int largo() throws PersistenciaException;
	public Revision kesimo(int numero) throws PersistenciaException;
	public List<VORevision> listarRevisiones() throws PersistenciaException;
	public void borrarRevisiones() throws PersistenciaException;


}