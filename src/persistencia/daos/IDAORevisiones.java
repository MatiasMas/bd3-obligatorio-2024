package persistencia.daos;

import java.util.List;

import logica.valueObjects.RevisionVO;

public interface IDAORevisiones {
	public boolean member (int numero);
	public void insert (RevisionVO rev);
	public RevisionVO find (int numero);
	public List<RevisionVO> listarRevisiones ();
}
