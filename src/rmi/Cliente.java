package rmi;

import java.rmi.Naming;

import grafica.ventanas.VentanaMenu;
import logica.IFachada;
import logica.excepciones.NoSePudoConectarServidorException;
import utilidades.Configuracion;

public class Cliente {

	private static IFachada instancia;

	public static void main(String[] args) {
		// Ejecuta la ventana en el hilo de la interfaz gráfica
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				VentanaMenu ventana = new VentanaMenu();
				ventana.setVisible(true);
			}
		});
	}

	private Cliente() throws NoSePudoConectarServidorException {

		try {
			// Obtengo la configuracion
			Configuracion fileConfig = Configuracion.getInstancia();
			instancia = (IFachada) Naming.lookup(fileConfig.getFachada());
		} catch (Exception e) {
			throw new NoSePudoConectarServidorException("Error conectando con el servidor");
		}
	}

	public static IFachada obtenerFachada() throws NoSePudoConectarServidorException {
		if (instancia == null) {
			instancia = new Cliente().instancia;
		}
		return instancia;
	}
}
