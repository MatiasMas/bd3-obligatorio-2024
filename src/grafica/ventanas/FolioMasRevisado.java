package grafica.ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorFolioMasVisitado;
import logica.valueObjects.VOFolioMaxRev;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class FolioMasRevisado extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablefolioMasRevisado;
	private ControladorFolioMasVisitado cfmv;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FolioMasRevisado frame = new FolioMasRevisado();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public FolioMasRevisado() {
	    this.cfmv = new ControladorFolioMasVisitado(this);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setBounds(100, 100, 901, 299);
	    contentPane = new JPanel();
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    setContentPane(contentPane);
	    contentPane.setLayout(null);

	    DefaultTableModel modeloFolio = new DefaultTableModel();
	    tablefolioMasRevisado = new JTable(modeloFolio);
	    tablefolioMasRevisado.setEnabled(false);
	    tablefolioMasRevisado.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    tablefolioMasRevisado.setRowSelectionAllowed(false);

	    String[] columnNames = { "Codigo", "Caratula", "Paginas", "Cantidad Revisiones" };
	    modeloFolio.setColumnIdentifiers(columnNames);

	    JScrollPane scrollPane = new JScrollPane(tablefolioMasRevisado);
	    scrollPane.setBounds(10, 10, 865, 150);
	    contentPane.add(scrollPane);

	  
	    JButton btnfolioMasRevisado = new JButton("Obtener Folios Más Revisado");
	    btnfolioMasRevisado.setSize(200, 30); 
	    btnfolioMasRevisado.setLocation((contentPane.getWidth() - btnfolioMasRevisado.getWidth()) / 2, 200); // Center horizontally
	    btnfolioMasRevisado.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            try {
	                VOFolioMaxRev folio = cfmv.getFolioMasRev();
	                if (folio != null) {
	                    modeloFolio.setRowCount(0);
	                    modeloFolio.addRow(new Object[] { folio.getCodigo(), folio.getCaratula(), folio.getPaginas(), folio.getCantRevisiones() });
	                }
	            } catch (Exception e2) {
	                JOptionPane.showMessageDialog(contentPane, "Error al obtener el folio más revisado.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
	    contentPane.add(btnfolioMasRevisado);
	    
	    contentPane.addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentResized(ComponentEvent e) {
	            btnfolioMasRevisado.setLocation((contentPane.getWidth() - btnfolioMasRevisado.getWidth()) / 2, 200);
	        }
	    });
	    
	}
	
	public void visible() {
		this.setVisible(true);
	}
}
