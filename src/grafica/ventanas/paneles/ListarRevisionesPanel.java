package grafica.ventanas.paneles;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import grafica.ventanas.VentanaMenu;

public class ListarRevisionesPanel extends JPanel {
    public ListarRevisionesPanel(VentanaMenu ventanaMenu) {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Listado de Revisiones", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        JButton btnVolver = new JButton("Volver al Menú");
//        btnVolver.addActionListener(e -> mostrarVista("MenuPrincipal"));
        add(btnVolver, BorderLayout.SOUTH);
    }
}