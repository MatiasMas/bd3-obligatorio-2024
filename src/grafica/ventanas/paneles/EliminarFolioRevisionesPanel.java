package grafica.ventanas.paneles;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import grafica.ventanas.VentanaMenu;

public class EliminarFolioRevisionesPanel extends JPanel {
	public EliminarFolioRevisionesPanel(VentanaMenu ventanaMenu) {
		setLayout(new BorderLayout());
		JLabel label = new JLabel("Borrar Folio", SwingConstants.CENTER);
		add(label, BorderLayout.CENTER);

		JButton btnVolver = new JButton("Volver al Menú");
//            btnVolver.addActionListener(e -> mostrarVista("MenuPrincipal"));
		add(btnVolver, BorderLayout.SOUTH);
	}
}