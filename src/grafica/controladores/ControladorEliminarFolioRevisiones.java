package grafica.controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import grafica.ventanas.EliminarFolioRevisiones;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ValorInvalidoException;
import logica.valueObjects.VOBorrarFolio;
import utilidades.Validador;

public class ControladorEliminarFolioRevisiones {

	private EliminarFolioRevisiones efr;

	public ControladorEliminarFolioRevisiones(EliminarFolioRevisiones ventana) {
		this.efr = ventana;
	}

	public void eliminarFolioRevisiones(String codigoFolio) throws Exception{
		System.out.println("codigoFolio: " + codigoFolio);

		Properties p = new Properties();
		String nomArch = "config/cliente.properties";
		try {
			p.load(new FileInputStream(nomArch));
		} catch (IOException e1) {
			throw new PersistenciaException("Error en leer archivo de configuracion");
		}

		String path = p.getProperty("fachada");
		camposValidos(codigoFolio);
		
		try {
			IFachada fachada = (IFachada) Naming.lookup(path);
			VOBorrarFolio vobf = new VOBorrarFolio(codigoFolio);
			fachada.borrarFolioRevisiones(vobf);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			throw new PersistenciaException("Error en fachada:" + e);
		}
	}
	
	// valido los datos ingresados
	private void camposValidos(String codigoFolio) throws ValorInvalidoException {
		if (codigoFolio.isEmpty()) {
			String msg = "El codigo no puede ser vacío.";
			throw new ValorInvalidoException(msg);
		} else if (!Validador.esAlfaNumerico(codigoFolio)) {
			String msg = "El codigo debe ser alfanumerico.";
			throw new ValorInvalidoException(msg);
		}
	}
}
