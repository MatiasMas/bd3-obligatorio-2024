package grafica.controladores;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import grafica.ventanas.DarDescripcion;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ValorInvalidoException;
import logica.valueObjects.VODarDescripcion;
import rmi.Cliente;
import utilidades.Validador;

public class ControladorDarDescripcion {

	private DarDescripcion dd;

	public ControladorDarDescripcion(DarDescripcion ventana) {
		this.dd = ventana;
	}

	public String darDescripcion(String codFolio, String numero) throws Exception {

		camposValidos(codFolio, numero);

		try {
			IFachada fachada = Cliente.obtenerFachada();
			VODarDescripcion vo = new VODarDescripcion(codFolio, Integer.parseInt(numero));
			return fachada.darDescripcion(vo);

		} catch (RemoteException e) {
			throw new PersistenciaException("Error en fachada:" + e);
		}
	}

	// valido los datos ingresados
	private void camposValidos(String codigoFolio, String numero) throws ValorInvalidoException {
		if (codigoFolio.isEmpty()) {
			String msg = "El codigo no puede ser vacío.";
			throw new ValorInvalidoException(msg);
		} else if (!Validador.esAlfaNumerico(codigoFolio)) {
			String msg = "El codigo debe ser alfanumerico.";
			throw new ValorInvalidoException(msg);
		} else if (!Validador.esNumerico(numero)) {
			String msg = "El numero de revision debe ser numerico.";
			throw new ValorInvalidoException(msg);
		}

	}

}
