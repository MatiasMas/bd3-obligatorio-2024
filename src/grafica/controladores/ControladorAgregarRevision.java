package grafica.controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import grafica.ventanas.AgregarRevision;
import logica.IFachada;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VORevision;

public class ControladorAgregarRevision {

	private AgregarRevision ar;

	public ControladorAgregarRevision(AgregarRevision ventana) {
		this.ar = ventana;
	}

	public void agregarRevision(String codigoFolio, String descripcion)
			throws PersistenciaException, FolioNoExisteException {
		System.out.println("CodigoFolio: " + codigoFolio + " Descripcion: " + descripcion);

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
			
			VORevision vo = new VORevision(descripcion, codigoFolio);
			fachada.agregarRevision(vo);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			throw new PersistenciaException("Error en fachada:" + e);
		}
	}
}
