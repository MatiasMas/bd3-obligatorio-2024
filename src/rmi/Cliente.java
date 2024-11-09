package rmi;

import grafica.ventanas.VentanaMenu;

public class Cliente {
	public static void main(String[] args) {
		// Ejecuta la ventana en el hilo de la interfaz gráfica
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				VentanaMenu ventana = new VentanaMenu();
				ventana.setVisible(true);
			}
		});
	}
}
