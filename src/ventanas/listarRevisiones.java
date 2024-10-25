package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.Controlador;
import logicaPersistencia.excepciones.FolioException;
import logicaPersistencia.excepciones.PersistenciaException;
import logicaPersistencia.excepciones.SQLexcepcion;
import logicaPersistencia.valueObjects.VORevision;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class listarRevisiones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableRevision;
	private JLabel lblCodigoFolio;
	private JTextField txtCodigoFolio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					listarRevisiones frame = new listarRevisiones();
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
	public listarRevisiones() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 901, 364);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		DefaultTableModel modeloRevision = new DefaultTableModel();
		tableRevision = new JTable();
		tableRevision.setEnabled(false);
		tableRevision.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableRevision.setRowSelectionAllowed(false);
		String[] columnNames = { "Numero", "Codigo Folio", "Descripcion" };
		modeloRevision.setColumnIdentifiers(columnNames);
		tableRevision.setBounds(10, 71, 865, 209);
		contentPane.add(tableRevision);

		JButton btnObtenerRevisiones = new JButton("Obtener Revisiones");
		btnObtenerRevisiones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					Controlador controlador = new Controlador();
					List<VORevision> listaRevisiones = new ArrayList<VORevision>(
							controlador.listarRevisiones(txtCodigoFolio.getText()));
					Object[] fila = new Object[modeloRevision.getColumnCount()];

					for (VORevision revision : listaRevisiones) {
						fila[0] = revision.getNumero();
						fila[1] = revision.getCodigoFolio();
						fila[2] = revision.getDescripcion();
						modeloRevision.addRow(fila);// agregamos una fila a nuestro modelo de tabla
					}
					// Actualiza contenido de la tabla
					tableRevision.setModel(modeloRevision);

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (PersistenciaException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLexcepcion e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FolioException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnObtenerRevisiones.setBounds(381, 291, 156, 23);
		contentPane.add(btnObtenerRevisiones);

		lblCodigoFolio = new JLabel("Codigo de Folio:");
		lblCodigoFolio.setBounds(10, 11, 197, 14);
		contentPane.add(lblCodigoFolio);

		txtCodigoFolio = new JTextField();
		txtCodigoFolio.setBounds(231, 8, 319, 20);
		contentPane.add(txtCodigoFolio);
		txtCodigoFolio.setColumns(10);

	}
}
