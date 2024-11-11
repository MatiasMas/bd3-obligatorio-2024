package grafica.controladores;

import java.rmi.RemoteException;

import logica.IFachada;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.InstanciacionException;
import logica.excepciones.NoSePudoConectarServidorException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ValorInvalidoException;
import logica.valueObjects.VOBorrarFolio;
import rmi.Cliente;
import utilidades.Validador;

public class ControladorEliminarFolioRevisiones {

	public ControladorEliminarFolioRevisiones() {}

	public void eliminarFolioRevisiones(String codigoFolio) throws ValorInvalidoException, NoSePudoConectarServidorException, RemoteException, PersistenciaException, FolioNoExisteException, InstanciacionException {
		// valido los parametros ingresados
		camposValidos(codigoFolio);

		// hago lookup de la fachada del servidor
		IFachada fachada = Cliente.obtenerFachada();
		// creo vo para pasar a la capa logica
		VOBorrarFolio vobf = new VOBorrarFolio(codigoFolio);
		fachada.borrarFolioRevisiones(vobf);
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
