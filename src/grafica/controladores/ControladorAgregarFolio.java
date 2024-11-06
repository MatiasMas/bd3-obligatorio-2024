package grafica.controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import grafica.ventanas.AgregarFolio;
import logica.IFachada;
import logica.valueObjects.VOFolio;
import logica.excepciones.FolioYaExisteException;
import logica.excepciones.PersistenciaException;

public class ControladorAgregarFolio {

	private AgregarFolio caf;

	public ControladorAgregarFolio(AgregarFolio ventana) {
		this.caf = ventana;
	}

	public void agregarFolio(String codigo, String caratula, int paginas) throws PersistenciaException, FolioYaExisteException {

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
			VOFolio vo = new VOFolio(codigo, caratula, paginas);
			fachada.agregarFolio(vo);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			throw new PersistenciaException("Error en fachada:" + e);
		}
	}

}
