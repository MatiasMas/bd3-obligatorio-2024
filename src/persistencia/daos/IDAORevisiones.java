package persistencia.daos;

import java.util.List;

import logica.entidades.Revision;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VORevision;
import poolConexiones.IConexion;

public interface IDAORevisiones {
	public void insback(IConexion icon, Revision rev) throws PersistenciaException;

	public int largo(IConexion icon) throws PersistenciaException;

	public Revision kesimo(IConexion icon, int numero) throws PersistenciaException;

	public List<VORevision> listarRevisiones(IConexion icon) throws PersistenciaException;

	public void borrarRevisiones(IConexion icon) throws PersistenciaException;
}
