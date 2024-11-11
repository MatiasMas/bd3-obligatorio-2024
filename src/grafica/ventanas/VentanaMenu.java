package grafica.ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;

public class VentanaMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private AgregarFolioPanel agregarFolioPanel;
    private AgregarRevisionPanel agregarRevisionPanel;
    private ListarFoliosPanel listarFoliosPanel;
    private ListarRevisionesPanel listarRevisionesPanel;
    private FolioMasRevisadoPanel folioMasRevisadoPanel;
    private DarDescripcionPanel darDescripcionPanel;
    private EliminarFolioRevisionesPanel eliminarFolioRevisionesPanel;

    public VentanaMenu() {
        setTitle("Gestión de Folios y Revisiones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel, BorderLayout.CENTER);

        agregarFolioPanel = new AgregarFolioPanel(this);
        agregarRevisionPanel = new AgregarRevisionPanel(this);
        listarFoliosPanel = new ListarFoliosPanel(this);
        listarRevisionesPanel = new ListarRevisionesPanel(this);
        folioMasRevisadoPanel = new FolioMasRevisadoPanel(this);
        darDescripcionPanel = new DarDescripcionPanel(this);
        eliminarFolioRevisionesPanel = new EliminarFolioRevisionesPanel(this);
        
        mainPanel.add(crearMenuPrincipal(), "MenuPrincipal");
        mainPanel.add(agregarFolioPanel, "AgregarFolio");
        mainPanel.add(agregarRevisionPanel, "AgregarRevision");
        mainPanel.add(listarFoliosPanel, "ListarFolios");
        mainPanel.add(listarRevisionesPanel, "ListarRevisiones");
        mainPanel.add(folioMasRevisadoPanel, "FolioMasRevisado");
        mainPanel.add(darDescripcionPanel, "DarDescripcion");
        mainPanel.add(eliminarFolioRevisionesPanel, "BorrarFolio");

        cardLayout.show(mainPanel, "MenuPrincipal");
    }

    private JPanel crearMenuPrincipal() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        Dimension buttonSize = new Dimension(400, 50);

        JButton btnAgregarFolio = crearBoton("Agregar Folio", buttonSize, "AgregarFolio");
        JButton btnAgregarRevision = crearBoton("Agregar Revisión", buttonSize, "AgregarRevision");
        JButton btnListarFolios = crearBoton("Listar Folios", buttonSize, "ListarFolios");
        JButton btnListarRevisiones = crearBoton("Listar Revisiones", buttonSize, "ListarRevisiones");
        JButton btnFolioMasRevisado = crearBoton("Folio Más Revisado", buttonSize, "FolioMasRevisado");
        JButton btnDarDescripcion = crearBoton("Dar Descripción", buttonSize, "DarDescripcion");
        JButton btnBorrarFolio = crearBoton("Borrar Folio", buttonSize, "BorrarFolio");
        
		btnBorrarFolio.setBackground(new Color(255, 156, 156));

		// Colores
		Color fondoNormal = new Color(255, 156, 156);
		Color fondoHover = new Color(255, 116, 116);
		Color bordeColor = Color.LIGHT_GRAY;

		// Estilo del botón
		btnBorrarFolio.setBackground(fondoNormal);
		btnBorrarFolio.setForeground(Color.BLACK);
		btnBorrarFolio.setFocusPainted(false);

		// Añadir efecto hover
		btnBorrarFolio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnBorrarFolio.setBackground(fondoHover);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnBorrarFolio.setBackground(fondoNormal);
			}
		});
        
        JButton btnSalir = crearBoton("Salir", buttonSize, "Salir");

        btnSalir.addActionListener(e -> System.exit(0));

        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(btnAgregarFolio);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuPanel.add(btnAgregarRevision);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuPanel.add(btnListarFolios);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuPanel.add(btnListarRevisiones);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuPanel.add(btnFolioMasRevisado);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuPanel.add(btnDarDescripcion);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuPanel.add(btnBorrarFolio);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        menuPanel.add(btnSalir);
        menuPanel.add(Box.createVerticalGlue());

        return menuPanel;
    }

    public JButton crearBoton(String texto, Dimension size, String vista) {
    	BotonPersonalizado boton = new BotonPersonalizado(texto, 20);
        boton.setPreferredSize(size);
        boton.setMaximumSize(size);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Colores
        Color fondoNormal = new Color(226, 234, 244);
        Color fondoHover = new Color(200, 216, 236); // Gris más oscuro para el hover
        Color bordeColor = Color.LIGHT_GRAY;

        // Estilo del botón
        boton.setBackground(fondoNormal);
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);

        // Añadir efecto hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(fondoHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(fondoNormal);
            }
        });

        boton.addActionListener(e -> mostrarVista(vista));
        return boton;
    }

    public void mostrarVista(String vista) {
        if (vista.equals("Salir")) {
            System.exit(0);
        } else if(vista.equals("AgregarFolio")) {
            agregarFolioPanel.limpiarCampos();
        } else if(vista.equals("AgregarRevision")) {
            agregarRevisionPanel.limpiarCampos();
        } else if(vista.equals("ListarFolios")) {
            listarFoliosPanel.limpiarCampos();
        } else if(vista.equals("ListarRevisiones")) {
            listarRevisionesPanel.limpiarCampos();
        } else if(vista.equals("FolioMasRevisado")) {
            folioMasRevisadoPanel.limpiarCampos();
        } else if(vista.equals("DarDescripcion")) {
            darDescripcionPanel.limpiarCampos();
        } else if(vista.equals("BorrarFolio")) {
            eliminarFolioRevisionesPanel.limpiarCampos();
        } 
        
        cardLayout.show(mainPanel, vista);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaMenu frame = new VentanaMenu();
            frame.setVisible(true);
        });
    }
}
