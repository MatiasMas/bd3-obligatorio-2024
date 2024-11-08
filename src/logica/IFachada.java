package logica;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import logica.excepciones.DarDescripcionException;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.FolioYaExisteException;
import logica.excepciones.NoExistenFoliosException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOBorrarFolio;
import logica.valueObjects.VODarDescripcion;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import logica.valueObjects.VOListarRevisiones;
import logica.valueObjects.VORevision;

public interface IFachada extends Remote {
	public void agregarFolio(VOFolio voF) throws RemoteException, PersistenciaException, FolioYaExisteException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	public void agregarRevision(VORevision voR) throws RemoteException, PersistenciaException, FolioNoExisteException;

	public void borrarFolioRevisiones(VOBorrarFolio voF)
			throws RemoteException, PersistenciaException, FolioNoExisteException;

	public String darDescripcion(VODarDescripcion voD)
			throws RemoteException, PersistenciaException, FolioNoExisteException, DarDescripcionException;

	public List<VOFolio> listarFolios() throws RemoteException, PersistenciaException, FolioNoExisteException;

	public List<VORevision> listarRevisiones(VOListarRevisiones voL)
			throws RemoteException, PersistenciaException, FolioNoExisteException, InstantiationException, IllegalAccessException, ClassNotFoundException;

	public VOFolioMaxRev folioMasRevisado() throws RemoteException, PersistenciaException, NoExistenFoliosException, InstantiationException, IllegalAccessException, ClassNotFoundException;
}
