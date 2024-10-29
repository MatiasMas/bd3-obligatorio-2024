package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorlistarFolios;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class listarFolios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableFolios;
	private ControladorlistarFolios clf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					listarFolios frame = new listarFolios();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public listarFolios() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 901, 299);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		DefaultTableModel modeloFolio = new DefaultTableModel();
		tableFolios = new JTable();
		tableFolios.setEnabled(false);
		tableFolios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableFolios.setRowSelectionAllowed(false);
		String[] columnNames = { "Codigo", "Caratula", "Paginas" };
		modeloFolio.setColumnIdentifiers(columnNames);
		tableFolios.setBounds(10, 11, 865, 209);
		contentPane.add(tableFolios);

		JButton btnObtenerFolios = new JButton("Obtener Folios");
		btnObtenerFolios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnObtenerFolios.setBounds(381, 231, 156, 23);
		contentPane.add(btnObtenerFolios);

	}

	public void visible() {
		this.setVisible(true);
	}
}
