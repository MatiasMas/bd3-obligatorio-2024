package ventanas;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import grafica.controladores.ControladorventanaMenu;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ventanamenu extends JFrame {

//	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ControladorventanaMenu cvm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventanamenu frame = new Ventanamenu();
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
	public Ventanamenu() {
		cvm = new ControladorventanaMenu(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 170);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnAgregarFolio = new JButton("Agregar Folio");
		btnAgregarFolio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cvm.agregarFolio();
				} catch (IOException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnAgregarFolio.setBounds(28, 11, 141, 23);
		contentPane.add(btnAgregarFolio);

		JButton btnAgregarRevision = new JButton("Agregar Revision");
		btnAgregarRevision.setBounds(23, 70, 141, 23);
		contentPane.add(btnAgregarRevision);

		JButton btnEliminarFolioRevisiones = new JButton("Eliminar Folio y Revisiones");
		btnEliminarFolioRevisiones.setBounds(351, 70, 179, 23);
		contentPane.add(btnEliminarFolioRevisiones);

		JButton btnListarFolios = new JButton("Listar Folios");
		btnListarFolios.setBounds(197, 11, 141, 23);
		contentPane.add(btnListarFolios);

		JButton btnFolioMasRevisado = new JButton("Folio mas revisado");
		btnFolioMasRevisado.setBounds(366, 11, 160, 23);
		contentPane.add(btnFolioMasRevisado);

		JButton btnListarRevisiones = new JButton("Listar Revisiones");
		btnListarRevisiones.setBounds(187, 70, 141, 23);
		contentPane.add(btnListarRevisiones);
	}

	public void agregarFolio() throws FileNotFoundException, IOException, NotBoundException {
		agregarFolio af = new agregarFolio();
		af.visible();
	}
}
