package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorListarFolios;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class ListarFolios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableFolios;
	private ControladorListarFolios clf;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListarFolios frame = new ListarFolios();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ListarFolios() {
		this.clf = new ControladorListarFolios(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 901, 299);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		DefaultTableModel modeloFolio = new DefaultTableModel();
		tableFolios = new JTable(modeloFolio);
		tableFolios.setEnabled(false);
		tableFolios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableFolios.setRowSelectionAllowed(false);
		String[] columnNames = { "Codigo", "Caratula", "Paginas" };
		modeloFolio.setColumnIdentifiers(columnNames);

		JScrollPane scrollPane = new JScrollPane(tableFolios);
		scrollPane.setBounds(10, 11, 865, 209);
		contentPane.add(scrollPane);

		JButton btnObtenerFolios = new JButton("Obtener Folios");
		btnObtenerFolios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					List<VOFolio> folios = clf.listarFolios();

					modeloFolio.setRowCount(0);

					for (VOFolio folio : folios) {
						modeloFolio.addRow(new Object[] { folio.getCodigo(), folio.getCaratula(), folio.getPaginas() });
					}
				} catch (PersistenciaException e2) {
					JOptionPane.showMessageDialog(null, "Error al obtener los folios: " + e2.getMessage(), 
						"Error", JOptionPane.ERROR_MESSAGE);
				} catch (FolioNoExisteException e1) {
					JOptionPane.showMessageDialog(null, "Error NO existe folio: " + e1.getMessage(), 
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnObtenerFolios.setBounds(381, 231, 156, 23);
		contentPane.add(btnObtenerFolios);

	}

	public void visible() {
		this.setVisible(true);
	}
}
