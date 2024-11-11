package grafica.controladores;

import java.rmi.RemoteException;

import logica.IFachada;
import logica.excepciones.InstanciacionException;
import logica.excepciones.NoExistenFoliosException;
import logica.excepciones.NoSePudoConectarServidorException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolioMaxRev;
import rmi.Cliente;

public class ControladorFolioMasRevisado {

	public ControladorFolioMasRevisado() {}

	public VOFolioMaxRev getFolioMasRev() throws NoSePudoConectarServidorException, RemoteException, PersistenciaException, NoExistenFoliosException, InstanciacionException  {
		// hago lookup de la fachada del servidor
		IFachada fachada = Cliente.obtenerFachada();
		return fachada.folioMasRevisado();
	}
}
