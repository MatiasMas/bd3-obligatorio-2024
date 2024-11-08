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
import utilidades.Validador;
import logica.excepciones.ValorInvalidoException;
import logica.excepciones.PersistenciaException;

public class ControladorAgregarFolio {

	private AgregarFolio caf;

	public ControladorAgregarFolio(AgregarFolio ventana) {
		this.caf = ventana;
	}

	public void agregarFolio(String codigo, String caratula, String paginas) throws Exception
	{	
	Properties p = new Properties();	
	String nomArch = "config/cliente.properties";
		
		try {
			p.load(new FileInputStream(nomArch));
		} catch (IOException e1) {
			throw new PersistenciaException("Error en leer archivo de configuracion");
		}

		String path = p.getProperty("fachada");
		
		camposValidos (codigo, caratula, paginas);

		try {
			IFachada fachada = (IFachada) Naming.lookup(path);
			VOFolio vo = new VOFolio(codigo, caratula, Integer.parseInt(paginas));
			
			fachada.agregarFolio(vo);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			throw new PersistenciaException("Error en fachada:" + e);
		}
		
	}

	// valido los datos ingresados
	private void camposValidos(String codigo, String caratula, String paginas) throws ValorInvalidoException {
		if (codigo.isEmpty()) {
			String msg = "El codigo no puede ser vacío.";
			throw new ValorInvalidoException(msg);
		} else if (!Validador.esAlfaNumerico(codigo)) {
			String msg = "El codigo debe ser alfanumerico.";
			throw new ValorInvalidoException(msg);
		} else if (caratula.isEmpty()) {
			String msg = "La caratula no puede ser vacía.";
			throw new ValorInvalidoException(msg);
		} else if (paginas.isEmpty()) {
			String msg = "Las paginas no pueden ser vacías.";
			throw new ValorInvalidoException(msg);			
		} else if (!Validador.esNumerico(paginas)) {
			String msg = "El valos de las paginas debe ser numérico.";
			throw new ValorInvalidoException(msg);
		}

	}
	
}
