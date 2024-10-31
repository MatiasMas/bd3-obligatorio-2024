package grafica.controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Properties;

import grafica.ventanas.ListarFolios;
import logica.IFachada;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;

public class ControladorListarFolios {
	private ListarFolios lf;

	public ControladorListarFolios(ListarFolios ventana) {
		this.lf = ventana;
	}
	
	public List<VOFolio> listarFolios() throws PersistenciaException, FolioNoExisteException {

		Properties p = new Properties();
		String nomArch = "config/cliente.properties";
		try {
			p.load(new FileInputStream(nomArch));
		} catch (IOException e1) {
			throw new PersistenciaException("Error en leer archivo de configuracion");
		}

		String path = p.getProperty("fachada");
		
		try {
			IFachada fachada = (IFachada) Naming.lookup(path);
			return fachada.listarFolios();
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			throw new PersistenciaException("Error en fachada:" + e);
		}
	
	}
	
}
