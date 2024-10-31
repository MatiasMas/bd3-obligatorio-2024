package persistencia.abstractFactory;

import persistencia.daos.DAOArchivoFolios;
import persistencia.daos.DAOArchivoRevisiones;
import persistencia.daos.IDAOFolios;
import persistencia.daos.IDAORevisiones;

public class FabricaArchivo implements IFabricaAbstracta {
    @Override
    public IDAOFolios crearFolioDAO() {
        return new DAOArchivoFolios(); // DAO específico para archivos
    }

    @Override
    public IDAORevisiones crearRevisionDAO() {
        return new DAOArchivoRevisiones(); // DAO específico para archivos
    }
}
