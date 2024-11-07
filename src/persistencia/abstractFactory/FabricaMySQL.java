package persistencia.abstractFactory;

import persistencia.daos.DAOFoliosMySQL;
import persistencia.daos.DAORevisionesMySQL;
import persistencia.daos.IDAOFolios;
import persistencia.daos.IDAORevisiones;

public class FabricaMySQL implements IFabricaAbstracta {
	@Override
    public IDAOFolios crearFolioDAO() {
        return new DAOFoliosMySQL(); 
    }

    @Override
    public IDAORevisiones crearRevisionDAO() {
        return new DAORevisionesMySQL(null); 
    }
}
