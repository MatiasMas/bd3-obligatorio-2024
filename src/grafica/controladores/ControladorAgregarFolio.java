package grafica.controladores;

import java.rmi.RemoteException;

import logica.IFachada;
import logica.excepciones.FolioYaExisteException;
import logica.excepciones.InstanciacionException;
import logica.excepciones.NoSePudoConectarServidorException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ValorInvalidoException;
import logica.valueObjects.VOFolio;
import rmi.Cliente;
import utilidades.Validador;

public class ControladorAgregarFolio {

	public ControladorAgregarFolio() {}

	public void agregarFolio(String codigo, String caratula, String paginas) throws RemoteException, PersistenciaException, FolioYaExisteException, InstanciacionException, ValorInvalidoException, NoSePudoConectarServidorException {

		// valido los parametros ingresados
		camposValidos(codigo, caratula, paginas);

		// hago lookup de la fachada del servidor
		IFachada fachada = Cliente.obtenerFachada();
		// creo vo para pasar a la capa logica
		VOFolio vo = new VOFolio(codigo, caratula, Integer.parseInt(paginas));

		fachada.agregarFolio(vo);

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
