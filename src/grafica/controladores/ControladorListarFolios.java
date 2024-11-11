package grafica.controladores;

import java.rmi.RemoteException;
import java.util.List;

import logica.IFachada;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.InstanciacionException;
import logica.excepciones.NoExistenFoliosException;
import logica.excepciones.NoSePudoConectarServidorException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import rmi.Cliente;

public class ControladorListarFolios {

	public ControladorListarFolios() {
	}

	public List<VOFolio> listarFolios() throws PersistenciaException, FolioNoExisteException, NoExistenFoliosException, NoSePudoConectarServidorException, InstanciacionException {

		try {
			// hago lookup de la fachada del servidor
			IFachada fachada = Cliente.obtenerFachada();
			return fachada.listarFolios();
		} catch (RemoteException e) {
			throw new PersistenciaException("Error en fachada:" + e);
		}

	}

}
