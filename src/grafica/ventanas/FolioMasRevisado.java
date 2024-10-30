package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorFolioMasVisitado;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class FolioMasRevisado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablefolioMasRevisado;
	private ControladorFolioMasVisitado cfmv;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FolioMasRevisado frame = new FolioMasRevisado();
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
	public FolioMasRevisado() {
		this.cfmv = new ControladorFolioMasVisitado(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 901, 299);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		DefaultTableModel modeloFolio = new DefaultTableModel();
		tablefolioMasRevisado = new JTable();
		tablefolioMasRevisado.setEnabled(false);
		tablefolioMasRevisado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablefolioMasRevisado.setRowSelectionAllowed(false);
		String[] columnNames = { "Codigo", "Caratula", "Paginas", "cantidad_revisiones" };
		modeloFolio.setColumnIdentifiers(columnNames);
		tablefolioMasRevisado.setBounds(10, 11, 865, 50);
		contentPane.add(tablefolioMasRevisado);

		JButton btnfolioMasRevisado = new JButton("Obtener Folios mas Revisado ");
		btnfolioMasRevisado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnfolioMasRevisado.setBounds(251, 72, 383, 23);
		contentPane.add(btnfolioMasRevisado);
	}
	
	public void visible() {
		this.setVisible(true);
	}
}
