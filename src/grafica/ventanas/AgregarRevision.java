package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import grafica.controladores.ControladorAgregarRevision;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.PersistenciaException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AgregarRevision extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodigoFolio;
	private JTextField txtDescripcion;
	private ControladorAgregarRevision car;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgregarRevision frame = new AgregarRevision();
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
	public AgregarRevision() {
		this.car = new ControladorAgregarRevision(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel labelCodigoFolio = new JLabel("Codigo Folio:");
		labelCodigoFolio.setBounds(10, 11, 109, 14);
		contentPane.add(labelCodigoFolio);

		JLabel labelDescripcion = new JLabel("Descripcion:");
		labelDescripcion.setBounds(10, 57, 109, 14);
		contentPane.add(labelDescripcion);

		txtCodigoFolio = new JTextField();
		txtCodigoFolio.setBounds(155, 8, 156, 20);
		contentPane.add(txtCodigoFolio);
		txtCodigoFolio.setColumns(10);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(155, 54, 156, 20);
		contentPane.add(txtDescripcion);
		txtDescripcion.setColumns(10);

		JButton btnNuevaRevision = new JButton("Crear nueva Revision");
		btnNuevaRevision.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					car.agregarRevision(txtCodigoFolio.getText(), txtDescripcion.getText());
					JOptionPane.showMessageDialog(null, "Nueva Revision de Folio ingresada correctamente.");
					txtCodigoFolio.setText("");
					txtDescripcion.setText("");
				} catch (PersistenciaException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (FolioNoExisteException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
			}
		});
		btnNuevaRevision.setBounds(155, 112, 156, 23);
		contentPane.add(btnNuevaRevision);
	}

	public void visible() {
		this.setVisible(true);
	}
}
