package grafica.controladores;

import java.rmi.RemoteException;

import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.excepciones.RevisionNoExisteException;
import logica.excepciones.ValorInvalidoException;
import logica.valueObjects.VODarDescripcion;
import logica.valueObjects.VODescripcionRetornada;
import rmi.Cliente;
import utilidades.Validador;

public class ControladorDarDescripcion {

	public ControladorDarDescripcion() {
	}

	public VODescripcionRetornada darDescripcion(String codFolio, String numero) throws Exception {
		camposValidos(codFolio, numero);

		try {
			// hago lookup de la fachada del servidor
			IFachada fachada = Cliente.obtenerFachada();
			// creo vo para pasar a la capa logica
			VODarDescripcion vo = new VODarDescripcion(codFolio, Integer.parseInt(numero));
			return fachada.darDescripcion(vo);

		} catch (RemoteException e) {
			throw new PersistenciaException("Error en fachada:" + e);
		} catch (RevisionNoExisteException e) {
			throw new RevisionNoExisteException(e.getMessage());
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