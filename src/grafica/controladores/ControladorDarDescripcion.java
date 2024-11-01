package grafica.controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import grafica.ventanas.DarDescripcion;
import logica.IFachada;
import logica.valueObjects.VODarDescripcion;
import logica.valueObjects.VODescripcionRetornada;
import logica.excepciones.PersistenciaException;

public class ControladorDarDescripcion {
	
	private DarDescripcion dd;

	public ControladorDarDescripcion(DarDescripcion ventana) {
		this.dd = ventana;
	}	
	
	public VODescripcionRetornada darDescripcion (int numero, String codFolio) throws Exception {
		
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
			/*camposValidos(codFolio) y control de nro*/
			VODarDescripcion vo = new VODarDescripcion (codFolio,numero);
			return fachada.darDescripcion(vo);
		
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			throw new PersistenciaException("Error en fachada:" + e);
		}
	}

	
/*	// valido los datos ingresador
	private void camposValidos(String codFolio) {

		if (codFolio.isEmpty()) {
			String msg = "El codgio de folio no puede ser vacio";
			throw new ValorInvalidoException(msg);
		} }
*/
	


}


