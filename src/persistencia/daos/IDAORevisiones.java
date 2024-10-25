package persistencia.daos;

import java.util.List;

import logica.valueObjects.VORevision;

public interface IDAORevisiones {
	public boolean member (int numero);
	public void insert (VORevision rev);
	public VORevision find (int numero);
	public List<VORevision> listarRevisiones ();
}
