package grafica.ventanas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import grafica.controladores.ControladorListarFolios;
import grafica.controladores.ControladorListarRevisiones;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VORevision;

public class ListarRevisionesPanel extends JPanel {

    public ListarRevisionesPanel(VentanaMenu ventanaMenu) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Disposición vertical

        // Crear campo de texto personalizado para Código
        JTextField txtCodigo = new CampoTextoPersonalizado(20);
        Dimension fieldSize = new Dimension(450, 40); // Ajustar tamaño del campo de texto
        txtCodigo.setPreferredSize(fieldSize);
        txtCodigo.setMinimumSize(fieldSize);
        txtCodigo.setMaximumSize(fieldSize);

        // Crear label para el campo Código
        JLabel lblCodigo = new JLabel("Código");

        // Crear panel para el campo Código
        JPanel panelCodigo = new JPanel();
        panelCodigo.setLayout(new BoxLayout(panelCodigo, BoxLayout.Y_AXIS));
        panelCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtCodigo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCodigo.add(lblCodigo);
        panelCodigo.add(Box.createRigidArea(new Dimension(0, 5))); // Separación entre label y campo de texto
        panelCodigo.add(txtCodigo);
        panelCodigo.setMaximumSize(new Dimension(450, 70)); // Ajustar la altura del panel

        // Panel contenedor para alinear al centro
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        containerPanel.add(panelCodigo);

        // Crear el modelo de la tabla
        String[] columnNames = {"Número", "Código", "Descripción"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        // Ajustar el color de fondo de la tabla
        table.setBackground(new Color(240, 255, 240));

        // Ajustar el tamaño de las celdas y las cabeceras de columna
        table.setRowHeight(40);

        // Ajustar el color de las cabeceras de columna
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(175, 255, 175));
        header.setPreferredSize(new Dimension(450, 35)); // Ajustar la altura de la cabecera de columna

        // Centrar el texto en las cabeceras de columna y ajustar la fuente
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        headerRenderer.setFont(new Font("Arial", Font.BOLD, 16)); // Ajustar fuente de la cabecera de columna

        // Centrar el contenido de las celdas, tanto horizontalmente como verticalmente
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        cellRenderer.setVerticalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, cellRenderer);

        // Ajustar el tamaño de la tabla
        table.setPreferredScrollableViewportSize(new Dimension(450, 180));
        table.setFillsViewportHeight(true);

        // Ajustar el ancho de las columnas
        TableColumn columnNumero = table.getColumnModel().getColumn(0);
        columnNumero.setPreferredWidth(80);

        TableColumn columnCodigo = table.getColumnModel().getColumn(1);
        columnCodigo.setPreferredWidth(150);

        TableColumn columnDescripcion = table.getColumnModel().getColumn(2);
        columnDescripcion.setPreferredWidth(220);

        // Crear un panel para la tabla y añadir la tabla con un scroll pane
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);

        // Crear el botón "Obtener Revisiones"
        JButton btnObtenerRevisiones = ventanaMenu.crearBoton("Obtener Revisiones", new Dimension(450, 45), "");

        // Crear botón "Volver al Menú"
        JButton btnVolver = ventanaMenu.crearBoton("Volver al Menú", new Dimension(450, 45), "MenuPrincipal");

        // Acción del botón "Obtener Revisiones"
        btnObtenerRevisiones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
				try {
					ControladorListarRevisiones clr = new ControladorListarRevisiones();
					
					List<VORevision> revisiones = clr.listarRevisiones(txtCodigo.getText());

					tableModel.setRowCount(0);
					for (VORevision rev : revisiones) {
						tableModel.addRow(new Object[] { rev.getNumero(), rev.getCodFolio(), rev.getDescripcion() });
					}
				} catch (PersistenciaException e1) {
					JOptionPane.showMessageDialog(null, "Error al obtener las Revisiones: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (FolioNoExisteException e2) {
					JOptionPane.showMessageDialog(null, "Error NO existe Folio: " + e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception eG) {
					JOptionPane.showMessageDialog(null, eG.getMessage());
				}
            	
            }
        });

        // Agregar componentes al panel
        add(containerPanel); // Panel del campo de texto
        add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre el campo de texto y la tabla
        add(tablePanel); // Panel de la tabla
        add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre la tabla y los botones
        add(btnObtenerRevisiones);
        add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre botones
        add(btnVolver);
        add(Box.createVerticalStrut(15)); // Espacio adicional inferior
    }
}
