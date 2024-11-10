package grafica.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorListarRevisiones;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VORevision;

public class ListarRevisiones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableRevision;
	private JLabel lblCodigoFolio;
	private JTextField txtCodigoFolio;
	private ControladorListarRevisiones clr;

	// creo el frame de la ventana, todos los componentes visuales
	public ListarRevisiones() {
		this.clr = new ControladorListarRevisiones(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 901, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		DefaultTableModel modeloRevision = new DefaultTableModel();
		tableRevision = new JTable(modeloRevision);

		tableRevision.setEnabled(false);
		tableRevision.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableRevision.setRowSelectionAllowed(false);

		String[] columnNames = { "Numero", "Codigo Folio", "Descripcion" };
		modeloRevision.setColumnIdentifiers(columnNames);

		JScrollPane scrollPane = new JScrollPane(tableRevision);
		scrollPane.setBounds(10, 10, 865, 209);
		contentPane.add(scrollPane);

		lblCodigoFolio = new JLabel("Codigo de Folio:");
		lblCodigoFolio.setBounds(10, 230, 197, 14);
		contentPane.add(lblCodigoFolio);

		txtCodigoFolio = new JTextField();
		txtCodigoFolio.setBounds(120, 230, 319, 20);
		contentPane.add(txtCodigoFolio);
		txtCodigoFolio.setColumns(10);

		// codigo del click del boton
		JButton btnObtenerRevisiones = new JButton("Obtener Revisiones");
		btnObtenerRevisiones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					List<VORevision> revisiones = clr.listarRevisiones(txtCodigoFolio.getText());

					modeloRevision.setRowCount(0);
					for (VORevision rev : revisiones) {
						modeloRevision
								.addRow(new Object[] { rev.getNumero(), rev.getCodFolio(), rev.getDescripcion() });
					}
				} catch (PersistenciaException e1) {
					JOptionPane.showMessageDialog(null, "Error al obtener las Revisiones: " + e1.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (FolioNoExisteException e2) {
					JOptionPane.showMessageDialog(null, "Error NO existe Folio: " + e2.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (Exception eG) {
					JOptionPane.showMessageDialog(null, eG.getMessage());
				}
			}
		});
		// seteo la ubicacion y tamaño del boton
		btnObtenerRevisiones.setBounds(450, 230, 156, 23);
		// agrego el boton al frame
		contentPane.add(btnObtenerRevisiones);
	}

	public void visible() {
		this.setVisible(true);
	}
}
