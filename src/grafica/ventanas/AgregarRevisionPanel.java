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

import grafica.controladores.ControladorAgregarRevision;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.PersistenciaException;

public class AgregarRevisionPanel extends JPanel {
	private JTextField txtCodigo;
	private JTextField txtDescripcion;

    public AgregarRevisionPanel(VentanaMenu ventanaMenu) {
        int alturaPanel = 65;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Disposición vertical

        // Crear campos de texto personalizados con bordes redondeados y altura ajustada
        txtCodigo = new CampoTextoPersonalizado(20);
        txtDescripcion = new CampoTextoPersonalizado(20);

        // Crear labels
        JLabel lblCodigo = new JLabel("Código");
        JLabel lblDescripcion = new JLabel("Descripción");

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

        JPanel panelDescripcion = new JPanel();
        panelDescripcion.setLayout(new BoxLayout(panelDescripcion, BoxLayout.Y_AXIS));
        panelDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelDescripcion.add(lblDescripcion);
        panelDescripcion.add(Box.createRigidArea(new Dimension(0, 5))); // Separación entre label y campo de texto
        panelDescripcion.add(txtDescripcion);
        panelDescripcion.setMaximumSize(new Dimension(400, alturaPanel)); // Ajustar la altura del panel

        // Crear un panel para contener los campos de texto
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fieldsPanel.setMaximumSize(new Dimension(400, 300)); // Aumentar la altura máxima del panel

        // Añadir los paneles al panel principal
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 80))); // Espacio superior
        fieldsPanel.add(panelCodigo);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre campos
        fieldsPanel.add(panelDescripcion);

        // Crear el botón "Agregar Revisión"
        JButton btnAgregarRevision = ventanaMenu.crearBoton("Agregar Revisión", new Dimension(400, 50), "");

        // Crear botón "Volver al Menú"
        JButton btnVolver = ventanaMenu.crearBoton("Volver al Menú", new Dimension(400, 50), "MenuPrincipal");

        // Acción del botón "Agregar Revisión"
        btnAgregarRevision.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
				try {
					ControladorAgregarRevision car = new ControladorAgregarRevision();
					car.agregarRevision(txtCodigo.getText().trim(), txtDescripcion.getText().trim());
					JOptionPane.showMessageDialog(null, "Nueva Revision de Folio ingresada correctamente.");
					limpiarCampos();
				} catch (PersistenciaException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (FolioNoExisteException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception eG) {
					JOptionPane.showMessageDialog(null, eG.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
            }
        });

        // Agregar componentes al panel
        add(fieldsPanel); // Panel de campos de texto
        add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre campos de texto y botones
        add(btnAgregarRevision);
        add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre botones
        add(btnVolver);
        add(Box.createVerticalStrut(20)); // Espacio adicional inferior
    }

	public void limpiarCampos() {
		txtCodigo.setText("");
		txtDescripcion.setText("");
	}
}
