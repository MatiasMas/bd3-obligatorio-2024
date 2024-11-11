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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import grafica.controladores.ControladorListarFolios;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.NoExistenFoliosException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;

public class ListarFoliosPanel extends JPanel {
	private DefaultTableModel tableModel;

	public ListarFoliosPanel(VentanaMenu ventanaMenu) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Disposición vertical

		// Crear el modelo de la tabla
		String[] columnNames = { "Código", "Carátula", "Páginas" };
		tableModel = new DefaultTableModel(columnNames, 0);
		JTable table = new JTable(tableModel);

		// Ajustar el color de fondo de la tabla
		table.setBackground(new Color(240, 255, 240));

		// Ajustar el tamaño de las celdas y las cabeceras de columna
		table.setRowHeight(45);

		// Ajustar el color de las cabeceras de columna
		JTableHeader header = table.getTableHeader();
		header.setBackground(new Color(175, 255, 175));
		header.setPreferredSize(new Dimension(500, 40)); // Ajustar la altura de la cabecera de columna

		// Centrar el texto en las cabeceras de columna y ajustar la fuente
		DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader()
				.getDefaultRenderer();
		headerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		headerRenderer.setFont(new Font("Arial", Font.BOLD, 16)); // Ajustar fuente de la cabecera de columna

		// Centrar el contenido de las celdas, tanto horizontalmente como verticalmente
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		cellRenderer.setVerticalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, cellRenderer);

		// Ajustar el tamaño de la tabla
		table.setPreferredScrollableViewportSize(new Dimension(500, 200));
		table.setFillsViewportHeight(true);

		// Ajustar el ancho de las columnas
		TableColumn columnCodigo = table.getColumnModel().getColumn(0);
		columnCodigo.setPreferredWidth(100);

		TableColumn columnCaratula = table.getColumnModel().getColumn(1);
		columnCaratula.setPreferredWidth(300);

		TableColumn columnPaginas = table.getColumnModel().getColumn(2);
		columnPaginas.setPreferredWidth(100);

		// Crear un panel para la tabla y añadir la tabla con un scroll pane
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
		tablePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JScrollPane scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane);

		// Crear el botón "Obtener Folios"
		JButton btnObtenerFolios = ventanaMenu.crearBoton("Obtener Folios", new Dimension(400, 50), "");

		// Crear botón "Volver al Menú"
		JButton btnVolver = ventanaMenu.crearBoton("Volver al Menú", new Dimension(400, 50), "MenuPrincipal");

		// Acción del botón "Obtener Folios"
		btnObtenerFolios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					ControladorListarFolios clf = new ControladorListarFolios();

					List<VOFolio> folios = clf.listarFolios();

					tableModel.setRowCount(0);

					if (folios != null) {
						for (VOFolio folio : folios) {
							tableModel.addRow(
									new Object[] { folio.getCodigo(), folio.getCaratula(), folio.getPaginas() });
						}
					}
				} catch (PersistenciaException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NoExistenFoliosException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e3) {
					JOptionPane.showMessageDialog(null, e3.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		// Agregar componentes al panel
		add(tablePanel); // Panel de la tabla
		add(Box.createRigidArea(new Dimension(0, 30))); // Espacio entre la tabla y los botones
		add(btnObtenerFolios);
		add(Box.createRigidArea(new Dimension(0, 15))); // Espacio entre botones
		add(btnVolver);
		add(Box.createVerticalStrut(40)); // Espacio adicional inferior
	}

	public void limpiarCampos() {
		tableModel.setRowCount(0);
	}
}
