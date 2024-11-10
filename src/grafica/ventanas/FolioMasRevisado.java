package grafica.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorFolioMasVisitado;
import logica.valueObjects.VOFolioMaxRev;

public class FolioMasRevisado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablefolioMasRevisado;
	private ControladorFolioMasVisitado cfmv;

	// creo el frame de la ventana, todos los componentes visuales
	public FolioMasRevisado() {
		this.cfmv = new ControladorFolioMasVisitado(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 901, 299);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		DefaultTableModel modeloFolio = new DefaultTableModel();
		tablefolioMasRevisado = new JTable(modeloFolio);
		tablefolioMasRevisado.setEnabled(false);
		tablefolioMasRevisado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablefolioMasRevisado.setRowSelectionAllowed(false);

		String[] columnNames = { "Codigo", "Caratula", "Paginas", "Cantidad Revisiones" };
		modeloFolio.setColumnIdentifiers(columnNames);

		JScrollPane scrollPane = new JScrollPane(tablefolioMasRevisado);
		scrollPane.setBounds(10, 10, 865, 150);
		contentPane.add(scrollPane);

		// codigo del click del boton
		JButton btnfolioMasRevisado = new JButton("Obtener Folios Más Revisado");
		btnfolioMasRevisado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					VOFolioMaxRev folio = cfmv.getFolioMasRev();
					if (folio != null) {
						modeloFolio.setRowCount(0);
						modeloFolio.addRow(new Object[] { folio.getCodigo(), folio.getCaratula(), folio.getPaginas(),
								folio.getCantRevisiones() });
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, "Error al obtener el folio más revisado.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		// seteo la ubicacion y tamaño del boton
		btnfolioMasRevisado.setBounds(341, 191, 200, 30);
		// agrego el boton al frame
		contentPane.add(btnfolioMasRevisado);

	}

	public void visible() {
		this.setVisible(true);
	}
}
