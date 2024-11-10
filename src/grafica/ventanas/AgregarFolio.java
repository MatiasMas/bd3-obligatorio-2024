package grafica.ventanas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import grafica.controladores.ControladorAgregarFolio;
import logica.excepciones.FolioYaExisteException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.ValorInvalidoException;

public class AgregarFolio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtCaratula;
	private JTextField txtPaginas;
	private ControladorAgregarFolio caf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgregarFolio frame = new AgregarFolio();
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
	public AgregarFolio() {
		this.caf = new ControladorAgregarFolio(this);
		// this.caf = new ControladoragregarFolio(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel labelCodigo = new JLabel("Codigo:");
		labelCodigo.setBounds(10, 11, 109, 14);
		contentPane.add(labelCodigo);

		JLabel labelCaratula = new JLabel("Caratula:");
		labelCaratula.setBounds(10, 57, 109, 14);
		contentPane.add(labelCaratula);

		JLabel labelPaginas = new JLabel("Paginas:");
		labelPaginas.setBounds(10, 102, 109, 14);
		contentPane.add(labelPaginas);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(155, 8, 156, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		txtCaratula = new JTextField();
		txtCaratula.setBounds(155, 54, 156, 20);
		contentPane.add(txtCaratula);
		txtCaratula.setColumns(10);

		txtPaginas = new JTextField();
		txtPaginas.setBounds(155, 99, 156, 20);
		contentPane.add(txtPaginas);
		txtPaginas.setColumns(10);

		JButton btnNuevoFolio = new JButton("Crear nuevo Folio");
		btnNuevoFolio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					caf.agregarFolio(txtCodigo.getText(), txtCaratula.getText(), txtPaginas.getText());
					
					JOptionPane.showMessageDialog(null, "Nuevo Folio ingresado correctamente.");
					txtCodigo.setText("");
					txtCaratula.setText("");
					txtPaginas.setText("");

				} catch (PersistenciaException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (FolioYaExisteException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				} catch (ValorInvalidoException e4) {
					JOptionPane.showMessageDialog(null, e4.getMessage());
				} catch (Exception eG) {
					JOptionPane.showMessageDialog(null, eG.getMessage());
				}
			}
		});
		btnNuevoFolio.setBounds(155, 166, 156, 23);
		contentPane.add(btnNuevoFolio);
	}

	public void visible() {
		this.setVisible(true);
	}
}
