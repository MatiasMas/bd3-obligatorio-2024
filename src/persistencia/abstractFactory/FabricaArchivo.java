package persistencia.abstractFactory;

import java.rmi.RemoteException;

import logica.excepciones.PersistenciaException;
import persistencia.daos.DAOFoliosArchivo;
import persistencia.daos.DAORevisionesArchivo;
import persistencia.daos.IDAOFolios;
import persistencia.daos.IDAORevisiones;
import poolConexiones.IPoolConexiones;
import poolConexiones.PoolConexionesArchivo;

public class FabricaArchivo implements IFabricaAbstracta {
    @Override
    public IDAOFolios crearIDAOFolios() {
        return new DAOFoliosArchivo(); // DAO específico para archivos
    }

    @Override
    public IDAORevisiones crearIDAORevisiones(String codFolio) {
        return new DAORevisionesArchivo(codFolio); // DAO específico para archivos
    }
    
    @Override
    public IPoolConexiones crearIPoolConexiones() throws RemoteException, ClassNotFoundException, PersistenciaException {
        return new PoolConexionesArchivo(); 
    }
}
