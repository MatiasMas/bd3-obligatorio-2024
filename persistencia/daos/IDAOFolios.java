package persistencia.daos;

import java.util.List;
import logica.valueObjects.FolioVO;

public interface IDAOFolios {
	public boolean member (String codigo);
	public void insert (FolioVO fol);
	public FolioVO find (String codigo);
	public List<FolioVO> listarFolios ();
}
