package grafica.controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import logica.IFachada;
import logica.valueObjects.VOFolio;
import logica.excepciones.PersistenciaException;
import ventanas.agregarFolio;

public class ControladoragregarFolio {

	private agregarFolio caf;

	public ControladoragregarFolio(agregarFolio ventana) {
		this.caf = ventana;
	}

	public void agregarFolio(String codigo, String caratula, int paginas) throws PersistenciaException {
		System.out.println("Codigo: " + codigo + " Caratula: " + caratula + " Paginas: " + paginas);

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
