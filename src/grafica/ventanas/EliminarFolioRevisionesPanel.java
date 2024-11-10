package grafica.ventanas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import grafica.controladores.ControladorEliminarFolioRevisiones;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.PersistenciaException;

public class EliminarFolioRevisionesPanel extends JPanel {

    public EliminarFolioRevisionesPanel(VentanaMenu ventanaMenu) {
        int alturaPanel = 65;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Disposición vertical

        // Crear campo de texto personalizado con borde redondeado y altura ajustada
        JTextField txtCodigo = new CampoTextoPersonalizado(20);

        // Crear label
        JLabel lblCodigo = new JLabel("Código");

        // Crear panel para alinear el label a la izquierda y ajustar la altura
        JPanel panelCodigo = new JPanel();
        panelCodigo.setLayout(new BoxLayout(panelCodigo, BoxLayout.Y_AXIS));
        panelCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCodigo.add(lblCodigo);
        panelCodigo.add(Box.createRigidArea(new Dimension(0, 5))); // Separación entre label y campo de texto
        panelCodigo.add(txtCodigo);
        panelCodigo.setMaximumSize(new Dimension(400, alturaPanel)); // Ajustar la altura del panel

        // Crear un panel para contener el campo de texto
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fieldsPanel.setMaximumSize(new Dimension(400, 200)); // Ajustar la altura máxima del panel

        // Añadir el panel al panel principal
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 80))); // Espacio superior
        fieldsPanel.add(panelCodigo);

        // Crear el botón "Eliminar Folio"
        JButton btnEliminarFolio = ventanaMenu.crearBoton("Eliminar Folio", new Dimension(400, 50), "");
        btnEliminarFolio.setBackground(new Color(255, 156, 156));
        
        // Colores
        Color fondoNormal = new Color(255, 156, 156);
        Color fondoHover = new Color(255, 116, 116);
        Color bordeColor = Color.LIGHT_GRAY;

        // Estilo del botón
        btnEliminarFolio.setBackground(fondoNormal);
        btnEliminarFolio.setForeground(Color.BLACK);
        btnEliminarFolio.setFocusPainted(false);

        // Añadir efecto hover
        btnEliminarFolio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	btnEliminarFolio.setBackground(fondoHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	btnEliminarFolio.setBackground(fondoNormal);
            }
        });

        // Crear botón "Volver al Menú"
        JButton btnVolver = ventanaMenu.crearBoton("Volver al Menú", new Dimension(400, 50), "MenuPrincipal");

        // Acción del botón "Eliminar Folio"
        btnEliminarFolio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
				try {
					ControladorEliminarFolioRevisiones cefr = new ControladorEliminarFolioRevisiones();
					
					cefr.eliminarFolioRevisiones(txtCodigo.getText());
					JOptionPane.showMessageDialog(null, "Folio y Revisiones eliminadas correctamente.");
					txtCodigo.setText("");
				} catch (PersistenciaException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (FolioNoExisteException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				} catch (Exception eG) {
					JOptionPane.showMessageDialog(null, eG.getMessage());	
				}
            	
            }
        });

        // Agregar componentes al panel
        add(fieldsPanel); // Panel de campos de texto
        add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre campos de texto y botones
        add(btnEliminarFolio);
        add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre botones
        add(btnVolver);
        add(Box.createVerticalStrut(20)); // Espacio adicional inferior
    }
}
