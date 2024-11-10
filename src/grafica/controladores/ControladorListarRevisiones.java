package grafica.controladores;

import java.rmi.RemoteException;
import java.util.List;

import grafica.ventanas.ListarRevisiones;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ValorInvalidoException;
import logica.valueObjects.VOListarRevisiones;
import logica.valueObjects.VORevision;
import rmi.Cliente;
import utilidades.Validador;

public class ControladorListarRevisiones {

	private ListarRevisiones lr;

	public ControladorListarRevisiones(ListarRevisiones ventana) {
		this.lr = ventana;
	}
	
	public List<VORevision> listarRevisiones(String codigoFolio) throws Exception {
		
		// valido los parametros ingresados
		camposValidos(codigoFolio);
		
		try {
			// hago lookup de la fachada del servidor
			IFachada fachada = Cliente.obtenerFachada();
			// creo vo para pasar a la capa logica
			VOListarRevisiones vo = new VOListarRevisiones(codigoFolio);
			return fachada.listarRevisiones(vo);
		} catch ( RemoteException e) {
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
