package sistema;

import javax.swing.UIManager;

import ventanas.listarFolios;

public class Principal {
	
	public static void main(String[] args) throws Exception {
		try {
			//seteo estilos para las panatallas
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			throw new Exception("No se pudieron cargar los estilos de Java Swing.");
		}

		listarFolios v = new listarFolios();
		v.setVisible(true);
	}

}
