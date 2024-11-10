package grafica.ventanas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import grafica.controladores.ControladorDarDescripcion;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODescripcionRetornada;

public class DarDescripcionPanel extends JPanel {

    public DarDescripcionPanel(VentanaMenu ventanaMenu) {
        int alturaPanel = 65;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Disposición vertical

        // Crear campos de texto personalizados con bordes redondeados y altura ajustada
        JTextField txtCodigo = new CampoTextoPersonalizado(20);
        JTextField txtNumeroRevision = new CampoTextoPersonalizado(20);

        // Crear labels
        JLabel lblCodigo = new JLabel("Código");
        JLabel lblNumeroRevision = new JLabel("Número de Revisión");

        // Crear panel de descripción
        JTextArea txtDescripcion = new JTextArea();
        txtDescripcion.setBackground(new Color(240, 255, 240));
        txtDescripcion.setEditable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setPreferredSize(new Dimension(400, 200));
        txtDescripcion.setMinimumSize(new Dimension(400, 200));
        txtDescripcion.setMaximumSize(new Dimension(400, 200));
        JPanel panelDescripcion = new JPanel();
        panelDescripcion.setLayout(new BoxLayout(panelDescripcion, BoxLayout.Y_AXIS));
        panelDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelDescripcion.add(txtDescripcion);
        panelDescripcion.setMaximumSize(new Dimension(400, 120)); // Ajustar la altura del panel

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

        JPanel panelNumeroRevision = new JPanel();
        panelNumeroRevision.setLayout(new BoxLayout(panelNumeroRevision, BoxLayout.Y_AXIS));
        panelNumeroRevision.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblNumeroRevision.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtNumeroRevision.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelNumeroRevision.add(lblNumeroRevision);
        panelNumeroRevision.add(Box.createRigidArea(new Dimension(0, 5))); // Separación entre label y campo de texto
        panelNumeroRevision.add(txtNumeroRevision);
        panelNumeroRevision.setMaximumSize(new Dimension(400, alturaPanel)); // Ajustar la altura del panel

        // Crear un panel para contener los campos de texto
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fieldsPanel.setMaximumSize(new Dimension(400, 400)); // Aumentar la altura máxima del panel

        // Añadir los paneles al panel principal
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Espacio superior
        fieldsPanel.add(panelCodigo);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre campos
        fieldsPanel.add(panelNumeroRevision);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre campos
        fieldsPanel.add(panelDescripcion);

        // Crear el botón "Obtener Descripción"
        JButton btnObtenerDescripcion = ventanaMenu.crearBoton("Obtener Descripción", new Dimension(400, 50), "");

        // Crear botón "Volver al Menú"
        JButton btnVolver = ventanaMenu.crearBoton("Volver al Menú", new Dimension(400, 50), "MenuPrincipal");

        // Acción del botón "Obtener Descripción"
        btnObtenerDescripcion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
				try {
					ControladorDarDescripcion cdd = new ControladorDarDescripcion();
					VODescripcionRetornada vo = cdd.darDescripcion(txtCodigo.getText(),txtNumeroRevision.getText());
					
					txtDescripcion.setText(vo.getDescripcion());
				} catch (PersistenciaException e2) {
					JOptionPane.showMessageDialog(null, "Error al obtener los folios: " + e2.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (FolioNoExisteException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e3) {
					JOptionPane.showMessageDialog(null, e3.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
            	
            }
        });

        // Agregar componentes al panel
        add(fieldsPanel); // Panel de campos de texto
        add(Box.createRigidArea(new Dimension(0, 20))); // Espacio entre campos de texto y botones
        add(btnObtenerDescripcion);
        add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre botones
        add(btnVolver);
        add(Box.createVerticalStrut(50)); // Espacio adicional inferior
    }
}
