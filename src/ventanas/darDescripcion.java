package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import grafica.controladores.Controlador;
import logicaPersistencia.excepciones.FolioException;
import logicaPersistencia.excepciones.PersistenciaException;
import logicaPersistencia.excepciones.SQLexcepcion;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class darDescripcion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtNroRevision;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					darDescripcion frame = new darDescripcion();
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
	public darDescripcion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel labelCodigo = new JLabel("Codigo:");
		labelCodigo.setBounds(10, 11, 109, 14);
		contentPane.add(labelCodigo);

		JLabel labelNroRevision = new JLabel("Numero de Revision:");
		labelNroRevision.setBounds(10, 57, 135, 14);
		contentPane.add(labelNroRevision);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(155, 8, 156, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		txtNroRevision = new JTextField();
		txtNroRevision.setBounds(155, 54, 156, 20);
		contentPane.add(txtNroRevision);
		txtNroRevision.setColumns(10);

		JLabel lblTituloDescripcion = new JLabel("Descripcion:");
		lblTituloDescripcion.setBounds(155, 85, 156, 14);
		contentPane.add(lblTituloDescripcion);

		JLabel lblResultadoDescripcion = new JLabel("");
		lblResultadoDescripcion.setBounds(10, 111, 414, 105);
		contentPane.add(lblResultadoDescripcion);

		JButton btnObtenerDescr = new JButton("Obtener descripcion");
		btnObtenerDescr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					Controlador controlador = new Controlador();
					lblResultadoDescripcion.setText(controlador.darDescripcion(txtCodigo.getText(),
							Integer.parseInt(txtNroRevision.getText())));

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
		btnObtenerDescr.setBounds(155, 227, 156, 23);
		contentPane.add(btnObtenerDescr);

	}
}
