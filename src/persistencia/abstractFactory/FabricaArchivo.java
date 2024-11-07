package persistencia.abstractFactory;

import persistencia.daos.DAOFoliosArchivo;
import persistencia.daos.DAORevisionesArchivo;
import persistencia.daos.IDAOFolios;
import persistencia.daos.IDAORevisiones;

public class FabricaArchivo implements IFabricaAbstracta {
    @Override
    public IDAOFolios crearFolioDAO() {
        return new DAOFoliosArchivo(); // DAO específico para archivos
    }

    @Override
    public IDAORevisiones crearRevisionDAO() {
        return new DAORevisionesArchivo(); // DAO específico para archivos
    }
}
