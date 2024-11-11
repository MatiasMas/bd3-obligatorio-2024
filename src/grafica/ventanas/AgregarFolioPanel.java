package grafica.ventanas;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import grafica.controladores.ControladorAgregarFolio;
import logica.excepciones.FolioYaExisteException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ValorInvalidoException;

public class AgregarFolioPanel extends JPanel {

    public AgregarFolioPanel(VentanaMenu ventanaMenu) {
    	int alturaPanel = 65;
    	
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Disposición vertical

        // Crear campos de texto personalizados con bordes redondeados y altura ajustada
        JTextField txtCodigo = new CampoTextoPersonalizado(20);
        JTextField txtCaratula = new CampoTextoPersonalizado(20);
        JTextField txtPaginas = new CampoTextoPersonalizado(20);

        // Crear labels
        JLabel lblCodigo = new JLabel("Código");
        JLabel lblCaratula = new JLabel("Carátula");
        JLabel lblPaginas = new JLabel("Páginas");

        // Crear paneles para alinear los labels a la izquierda y ajustar la altura
        JPanel panelCodigo = new JPanel();
        panelCodigo.setLayout(new BoxLayout(panelCodigo, BoxLayout.Y_AXIS));
        panelCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCodigo.add(lblCodigo);
        panelCodigo.add(Box.createRigidArea(new Dimension(0, 5))); // Separación entre label y campo de texto
        panelCodigo.add(txtCodigo);
        panelCodigo.setMaximumSize(new Dimension(400, alturaPanel)); // Ajustar la altura del panel

        JPanel panelCaratula = new JPanel();
        panelCaratula.setLayout(new BoxLayout(panelCaratula, BoxLayout.Y_AXIS));
        panelCaratula.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblCaratula.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtCaratula.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCaratula.add(lblCaratula);
        panelCaratula.add(Box.createRigidArea(new Dimension(0, 5))); // Separación entre label y campo de texto
        panelCaratula.add(txtCaratula);
        panelCaratula.setMaximumSize(new Dimension(400, alturaPanel)); // Ajustar la altura del panel

        JPanel panelPaginas = new JPanel();
        panelPaginas.setLayout(new BoxLayout(panelPaginas, BoxLayout.Y_AXIS));
        panelPaginas.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblPaginas.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtPaginas.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelPaginas.add(lblPaginas);
        panelPaginas.add(Box.createRigidArea(new Dimension(0, 5))); // Separación entre label y campo de texto
        panelPaginas.add(txtPaginas);
        panelPaginas.setMaximumSize(new Dimension(400, alturaPanel)); // Ajustar la altura del panel

        // Crear un panel para contener los campos de texto
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fieldsPanel.setMaximumSize(new Dimension(400, 300)); // Aumentar la altura máxima del panel

        // Añadir los paneles al panel principal
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Espacio superior
        fieldsPanel.add(panelCodigo);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre campos
        fieldsPanel.add(panelCaratula);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre campos
        fieldsPanel.add(panelPaginas);

        // Crear el botón "Crear Folio"
        JButton btnCrearFolio = ventanaMenu.crearBoton("Crear Folio", new Dimension(400, 50), "");

        // Crear botón "Volver al Menú"
        JButton btnVolver = ventanaMenu.crearBoton("Volver al Menú", new Dimension(400, 50), "MenuPrincipal");

        // Acción del botón "Crear Folio"
        btnCrearFolio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
				try {
					ControladorAgregarFolio caf = new ControladorAgregarFolio();
					caf.agregarFolio(txtCodigo.getText().trim(), txtCaratula.getText().trim(), txtPaginas.getText().trim());
					
					JOptionPane.showMessageDialog(null, "Nuevo Folio ingresado correctamente.");
					txtCodigo.setText("");
					txtCaratula.setText("");
					txtPaginas.setText("");
				} catch (PersistenciaException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (FolioYaExisteException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (ValorInvalidoException e4) {
					JOptionPane.showMessageDialog(null, e4.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception eG) {
					JOptionPane.showMessageDialog(null, eG.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
            	
            }
        });

        // Agregar componentes al panel
        add(fieldsPanel); // Panel de campos de texto
        add(Box.createRigidArea(new Dimension(0, 20))); // Espacio entre campos de texto y botones
        add(btnCrearFolio);
        add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre botones
        add(btnVolver);
        add(Box.createVerticalStrut(20)); // Espacio adicional inferior
    }
}
