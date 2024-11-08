package persistencia.abstractFactory;

import java.rmi.RemoteException;

import logica.excepciones.PersistenciaException;
import persistencia.daos.DAOFoliosMySQL;
import persistencia.daos.DAORevisionesMySQL;
import persistencia.daos.IDAOFolios;
import persistencia.daos.IDAORevisiones;
import poolConexiones.IPoolConexiones;
import poolConexiones.PoolConexiones;

public class FabricaMySQL implements IFabricaAbstracta {
	@Override
    public IDAOFolios crearIDAOFolios() {
        return new DAOFoliosMySQL(); 
    }

    @Override
    public IDAORevisiones crearIDAORevisiones(String codFolio) {
        return new DAORevisionesMySQL(codFolio); 
    }
    
    @Override
    public IPoolConexiones crearIPoolConexiones() throws RemoteException, ClassNotFoundException, PersistenciaException {
        return new PoolConexiones(); 
    }
}
