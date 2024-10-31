package persistencia.abstractFactory;

import persistencia.daos.IDAOFolios;
import persistencia.daos.IDAORevisiones;

public class FabricaArchivo implements IFabricaAbstracta {
    @Override
    public IDAOFolios crearFolioDAO() {
        return new ArchivoFoliosDAO(); // DAO específico para archivos
    }

    @Override
    public IDAORevisiones crearRevisionDAO() {
        return new ArchivoRevisionesDAO(); // DAO específico para archivos
    }
}
