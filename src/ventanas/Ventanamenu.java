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

	public Ventanamenu() {
		cvm = new ControladorventanaMenu(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 200);
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
					e1.printStackTrace();
				}
			}
		});

		btnAgregarFolio.setBounds(28, 11, 141, 23);
		contentPane.add(btnAgregarFolio);

		JButton btnAgregarRevision = new JButton("Agregar Revision");
		btnAgregarRevision.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cvm.agregarRevision();
				} catch (IOException | NotBoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAgregarRevision.setBounds(28, 70, 141, 23);
		contentPane.add(btnAgregarRevision);

		JButton btnEliminarFolioRevisiones = new JButton("Eliminar Folio y Revisiones");
		btnEliminarFolioRevisiones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cvm.eliminarFolioRevisiones();
				} catch (IOException | NotBoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnEliminarFolioRevisiones.setBounds(153, 114, 235, 23);
		contentPane.add(btnEliminarFolioRevisiones);

		JButton btnListarFolios = new JButton("Listar Folios");
		btnListarFolios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cvm.listarFolios();
				} catch (IOException | NotBoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnListarFolios.setBounds(197, 11, 141, 23);
		contentPane.add(btnListarFolios);

		JButton btnFolioMasRevisado = new JButton("Folio mas revisado");
		btnFolioMasRevisado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cvm.folioMasRevisado();
				} catch (IOException | NotBoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnFolioMasRevisado.setBounds(366, 11, 160, 23);
		contentPane.add(btnFolioMasRevisado);

		JButton btnListarRevisiones = new JButton("Listar Revisiones");
		btnListarRevisiones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cvm.listarRevisiones();
				} catch (IOException | NotBoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnListarRevisiones.setBounds(197, 70, 141, 23);
		contentPane.add(btnListarRevisiones);

		JButton btnDarDescripcion = new JButton("Dar Descripcion");
		btnDarDescripcion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cvm.darDescripcion();
				} catch (IOException | NotBoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDarDescripcion.setBounds(366, 70, 160, 23);
		contentPane.add(btnDarDescripcion);
	}

	public void agregarFolio() throws FileNotFoundException, IOException, NotBoundException {
		agregarFolio af = new agregarFolio();
		af.visible();
	}

	public void agregarRevision() throws FileNotFoundException, IOException, NotBoundException {
		agregarRevision ar = new agregarRevision();
		ar.visible();
	}

	public void listarFolios() throws FileNotFoundException, IOException, NotBoundException {
		listarFolios lf = new listarFolios();
		lf.visible();
	}

	public void listarRevisiones() throws FileNotFoundException, IOException, NotBoundException {
		listarRevisiones lr = new listarRevisiones();
		lr.visible();
	}

	public void folioMasRevisado() throws FileNotFoundException, IOException, NotBoundException {
		folioMasRevisado fmr = new folioMasRevisado();
		fmr.visible();
	}

	public void darDescripcion() throws FileNotFoundException, IOException, NotBoundException {
		darDescripcion dd = new darDescripcion();
		dd.visible();
	}

	public void eliminarFolioRevisiones() throws FileNotFoundException, IOException, NotBoundException {
		eliminarFolioRevisiones efr = new eliminarFolioRevisiones();
		efr.visible();
	}
}
