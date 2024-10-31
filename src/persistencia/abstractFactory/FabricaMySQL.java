package persistencia.abstractFactory;

import persistencia.daos.DAOMySQLFolio;
import persistencia.daos.DAOMySQLRevision;
import persistencia.daos.IDAOFolios;
import persistencia.daos.IDAORevisiones;

public class FabricaMySQL implements IFabricaAbstracta {
	@Override
    public IDAOFolios crearFolioDAO() {
        return new DAOMySQLFolio(); 
    }

    @Override
    public IDAORevisiones crearRevisionDAO() {
        return new DAOMySQLRevision(null); 
    }
}
