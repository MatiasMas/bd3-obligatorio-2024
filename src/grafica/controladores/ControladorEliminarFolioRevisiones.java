package grafica.controladores;

import java.rmi.RemoteException;

import grafica.ventanas.EliminarFolioRevisiones;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ValorInvalidoException;
import logica.valueObjects.VOBorrarFolio;
import rmi.Cliente;
import utilidades.Validador;

public class ControladorEliminarFolioRevisiones {

	private EliminarFolioRevisiones efr;

	public ControladorEliminarFolioRevisiones(EliminarFolioRevisiones ventana) {
		this.efr = ventana;
	}

	public void eliminarFolioRevisiones(String codigoFolio) throws Exception {
		System.out.println("codigoFolio: " + codigoFolio);

		camposValidos(codigoFolio);

		try {
			IFachada fachada = Cliente.obtenerFachada();
			VOBorrarFolio vobf = new VOBorrarFolio(codigoFolio);
			fachada.borrarFolioRevisiones(vobf);
		} catch (RemoteException e) {
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
