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

import grafica.controladores.ControladorDarDescripcion;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODescripcionRetornada;

public class DarDescripcion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtNroRevision;
	private ControladorDarDescripcion cdd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DarDescripcion frame = new DarDescripcion();
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
	public DarDescripcion() {
		this.cdd = new ControladorDarDescripcion(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
					VODescripcionRetornada vo = cdd.darDescripcion(txtCodigo.getText(),txtNroRevision.getText());
					
					lblResultadoDescripcion.setText(vo.getDescripcion());
				} catch (PersistenciaException e2) {
					JOptionPane.showMessageDialog(null, "Error al obtener los folios: " + e2.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (FolioNoExisteException e1) {
//					JOptionPane.showMessageDialog(null, "Error NO existe folio: " + e1.getMessage(), "Error",
//							JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e3) {
					JOptionPane.showMessageDialog(null, e3.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnObtenerDescr.setBounds(155, 227, 156, 23);
		contentPane.add(btnObtenerDescr);
	}

	public void visible() {
		this.setVisible(true);
	}
}
