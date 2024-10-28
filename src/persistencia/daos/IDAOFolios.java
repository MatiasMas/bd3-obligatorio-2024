package persistencia.daos;

import java.util.List;
import logica.valueObjects.VOFolio;

public interface IDAOFolios {
	public boolean member (String codigo);
	public void insert (VOFolio fol);
	public VOFolio find (String codigo);
	public List<VOFolio> listarFolios ();
}
