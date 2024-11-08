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
import logica.excepciones.ValorInvalidoException;
import logica.valueObjects.VORevision;
import utilidades.Validador;

public class ControladorAgregarRevision {

	private AgregarRevision ar;

	public ControladorAgregarRevision(AgregarRevision ventana) {
		this.ar = ventana;
	}

	public void agregarRevision(String codigoFolio, String descripcion) throws  Exception {
		System.out.println("CodigoFolio: " + codigoFolio + " Descripcion: " + descripcion);

		Properties p = new Properties();
		String nomArch = "config/cliente.properties";
		try {
			p.load(new FileInputStream(nomArch));
		} catch (IOException e1) {
			throw new PersistenciaException("Error en leer archivo de configuracion");
		}

		String path = p.getProperty("fachada");
		
		camposValidos (codigoFolio, descripcion);
		try {
			IFachada fachada = (IFachada) Naming.lookup(path);
			
			VORevision vo = new VORevision(descripcion, codigoFolio);
			fachada.agregarRevision(vo);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			throw new PersistenciaException("Error en fachada:" + e);
		}
	}
	
	// valido los datos ingresados
	private void camposValidos(String codigoFolio, String descripcion) throws ValorInvalidoException {
		if (codigoFolio.isEmpty()) {
			String msg = "El codigo no puede ser vacío.";
			throw new ValorInvalidoException(msg);
		} else if (!Validador.esAlfaNumerico(codigoFolio)) {
			String msg = "El codigo debe ser alfanumerico.";
			throw new ValorInvalidoException(msg);
		} else if (descripcion.isEmpty()) {
			String msg = "La descripcion no puede ser vacía.";
			throw new ValorInvalidoException(msg);
		} 

	}
	
	
	
}
