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
import logicaPersistencia.valueObjects.VOFolio;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class listarFolios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableFolios;

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
				try {

					Controlador controlador = new Controlador();
					List<VOFolio> listaFolios = new ArrayList<VOFolio>(controlador.listarFolios());
					Object[] fila = new Object[modeloFolio.getColumnCount()];

					for (VOFolio folio : listaFolios) {
						fila[0] = folio.getCodigo();
						fila[1] = folio.getCaratula();
						fila[2] = folio.getPaginas();
						modeloFolio.addRow(fila);// agregamos una fila a nuestro modelo de tabla
					}
					// Actualiza contenido de la tabla
					tableFolios.setModel(modeloFolio);
					
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
				} catch (FolioException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnObtenerFolios.setBounds(381, 231, 156, 23);
		contentPane.add(btnObtenerFolios);

	}
}
