package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import grafica.controladores.ControladoreliminarFolioRevisiones;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class eliminarFolioRevisiones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodigoFolio;
	private ControladoreliminarFolioRevisiones cefr;

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
		this.cefr = new ControladoreliminarFolioRevisiones(this);
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
			}
		});
		btnEliminarFolioRevision.setBounds(104, 60, 207, 23);
		contentPane.add(btnEliminarFolioRevision);
	}

	public void visible() {
		this.setVisible(true);
	}
}
