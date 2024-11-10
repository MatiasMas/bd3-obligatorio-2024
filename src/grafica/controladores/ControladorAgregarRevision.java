package grafica.controladores;

import java.rmi.RemoteException;

import grafica.ventanas.AgregarRevision;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ValorInvalidoException;
import logica.valueObjects.VORevision;
import rmi.Cliente;
import utilidades.Validador;

public class ControladorAgregarRevision {

	public ControladorAgregarRevision() {
	}

	public void agregarRevision(String codigoFolio, String descripcion) throws Exception {

		// valido los parametros ingresados
		camposValidos(codigoFolio, descripcion);
		try {
			// hago lookup de la fachada del servidor
			IFachada fachada = Cliente.obtenerFachada();
			// creo vo para pasar a la capa logica
			VORevision vo = new VORevision(descripcion, codigoFolio);
			fachada.agregarRevision(vo);
		} catch (RemoteException e) {
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
