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

import grafica.controladores.ControladorEliminarFolioRevisiones;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.PersistenciaException;

public class EliminarFolioRevisiones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodigoFolio;
	private ControladorEliminarFolioRevisiones cefr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EliminarFolioRevisiones frame = new EliminarFolioRevisiones();
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
	public EliminarFolioRevisiones() {
		this.cefr = new ControladorEliminarFolioRevisiones(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
					cefr.eliminarFolioRevisiones(txtCodigoFolio.getText());
					JOptionPane.showMessageDialog(null, "Folio y Revisiones eliminadas correctamente.");
					txtCodigoFolio.setText("");
				} catch (PersistenciaException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (FolioNoExisteException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
			}
		});
		btnEliminarFolioRevision.setBounds(104, 60, 207, 23);
		contentPane.add(btnEliminarFolioRevision);
	}

	public void visible() {
		this.setVisible(true);
	}
}
