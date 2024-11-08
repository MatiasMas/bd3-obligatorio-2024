package persistencia.abstractFactory;

import java.rmi.RemoteException;

import logica.excepciones.PersistenciaException;
import persistencia.daos.IDAOFolios;
import persistencia.daos.IDAORevisiones;
import poolConexiones.IPoolConexiones;

public interface IFabricaAbstracta {
	public IDAOFolios crearIDAOFolios();
    public IDAORevisiones crearIDAORevisiones(String codFolio);
    public IPoolConexiones crearIPoolConexiones() throws RemoteException, ClassNotFoundException, PersistenciaException;
}
