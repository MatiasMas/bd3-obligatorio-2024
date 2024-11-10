package logica;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import logica.excepciones.FolioNoExisteException;
import logica.excepciones.FolioYaExisteException;
import logica.excepciones.InstanciacionException;
import logica.excepciones.NoExistenFoliosException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.RevisionNoExisteException;
import logica.valueObjects.VOBorrarFolio;
import logica.valueObjects.VODarDescripcion;
import logica.valueObjects.VODescripcionRetornada;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import logica.valueObjects.VOListarRevisiones;
import logica.valueObjects.VORevision;

public interface IFachada extends Remote {
	public void agregarFolio(VOFolio voF) throws RemoteException, PersistenciaException, FolioYaExisteException, InstanciacionException;

	public void agregarRevision(VORevision voR) throws RemoteException, PersistenciaException, FolioNoExisteException, InstanciacionException;

	public void borrarFolioRevisiones(VOBorrarFolio voF) throws RemoteException, PersistenciaException, FolioNoExisteException, InstanciacionException;

	public VODescripcionRetornada darDescripcion(VODarDescripcion voD) throws RemoteException, PersistenciaException, FolioNoExisteException, RevisionNoExisteException, InstanciacionException;

	public List<VOFolio> listarFolios() throws RemoteException, PersistenciaException, InstanciacionException;

	public List<VORevision> listarRevisiones(VOListarRevisiones voL) throws RemoteException, PersistenciaException, FolioNoExisteException, InstanciacionException;

	public VOFolioMaxRev folioMasRevisado() throws RemoteException, PersistenciaException, NoExistenFoliosException, InstanciacionException;
}
