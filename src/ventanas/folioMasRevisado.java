package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.Controlador;
import logicaPersistencia.excepciones.PersistenciaException;
import logicaPersistencia.excepciones.SQLexcepcion;
import logicaPersistencia.valueObjects.VOFolioMaxRev;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class folioMasRevisado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablefolioMasRevisado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					folioMasRevisado frame = new folioMasRevisado();
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
	public folioMasRevisado() {
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
				try {

					Controlador controlador = new Controlador();
					VOFolioMaxRev folioMasRevisado = new VOFolioMaxRev();
					folioMasRevisado = controlador.folioMasRevisado();

					Object[] fila = new Object[modeloFolio.getColumnCount()];

					fila[0] = folioMasRevisado.getCodigo();
					fila[1] = folioMasRevisado.getCaratula();
					fila[2] = folioMasRevisado.getPaginas();
					fila[3] = folioMasRevisado.getCantRevisiones();
					modeloFolio.addRow(fila);

					// Actualiza contenido de la tabla
					tablefolioMasRevisado.setModel(modeloFolio);

				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (PersistenciaException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLexcepcion e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnfolioMasRevisado.setBounds(251, 72, 383, 23);
		contentPane.add(btnfolioMasRevisado);

	}
}
