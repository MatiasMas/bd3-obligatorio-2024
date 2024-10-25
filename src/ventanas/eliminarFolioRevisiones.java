package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import grafica.controladores.Controlador;
import logicaPersistencia.excepciones.FolioException;
import logicaPersistencia.excepciones.PersistenciaException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.awt.event.ActionEvent;

public class eliminarFolioRevisiones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodigoFolio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					eliminarFolioRevisiones frame = new eliminarFolioRevisiones();
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
	public eliminarFolioRevisiones() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel labelCodigoFolio = new JLabel("Codigo Folio:");
		labelCodigoFolio.setBounds(10, 11, 109, 14);
		contentPane.add(labelCodigoFolio);

		txtCodigoFolio = new JTextField();
		txtCodigoFolio.setBounds(155, 8, 156, 20);
		contentPane.add(txtCodigoFolio);
		txtCodigoFolio.setColumns(10);

		JButton btnEliminarFolioRevision = new JButton("Eliminar Folio y Revisiones");
		btnEliminarFolioRevision.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					Controlador controlador = new Controlador();
					controlador.borrarFolioRevisiones(txtCodigoFolio.getText());
				
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
				} catch (FolioException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEliminarFolioRevision.setBounds(104, 60, 207, 23);
		contentPane.add(btnEliminarFolioRevision);
	}
}
