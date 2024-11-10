package grafica.controladores;

import grafica.ventanas.FolioMasRevisado;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolioMaxRev;
import rmi.Cliente;

public class ControladorFolioMasVisitado {

	private FolioMasRevisado fmr;

	public ControladorFolioMasVisitado(FolioMasRevisado ventana) {
		this.fmr = ventana;
	}

	public VOFolioMaxRev getFolioMasRev() throws Exception {

		try {
			// hago lookup de la fachada del servidor
			IFachada fachada = Cliente.obtenerFachada();
			return fachada.folioMasRevisado();

		} catch (Exception e) {
			throw new PersistenciaException("Error en fachada:" + e);
		}

	}

}
