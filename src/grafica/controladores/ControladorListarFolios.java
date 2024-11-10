package grafica.controladores;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import grafica.ventanas.ListarFolios;
import logica.IFachada;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.InstanciacionException;
import logica.excepciones.NoSePudoConectarServidorException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import rmi.Cliente;

public class ControladorListarFolios {
	private ListarFolios lf;

	public ControladorListarFolios(ListarFolios ventana) {
		this.lf = ventana;
	}

	public List<VOFolio> listarFolios() throws PersistenciaException, FolioNoExisteException, NoSePudoConectarServidorException, InstanciacionException {

		try {
			IFachada fachada = Cliente.obtenerFachada();
			return fachada.listarFolios();
		} catch (RemoteException e) {
			throw new PersistenciaException("Error en fachada:" + e);
		}

	}

}
