package grafica.controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Properties;

import grafica.ventanas.ListarRevisiones;
import logica.IFachada;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOListarRevisiones;
import logica.valueObjects.VORevision;

public class ControladorListarRevisiones {

	private ListarRevisiones lr;

	public ControladorListarRevisiones(ListarRevisiones ventana) {
		this.lr = ventana;
	}
	
	public List<VORevision> listarRevisiones(String codigoFolio) throws PersistenciaException, FolioNoExisteException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		

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
			VOListarRevisiones vo = new VOListarRevisiones(codigoFolio);
			return fachada.listarRevisiones(vo);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			throw new PersistenciaException("Error en fachada:" + e);
		}
		
	} 
	

}
