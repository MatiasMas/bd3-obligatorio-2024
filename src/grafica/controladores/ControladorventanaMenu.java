package grafica.controladores;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;

import ventanas.Ventanamenu;

public class ControladorventanaMenu {

	private Ventanamenu vm;

	public ControladorventanaMenu(Ventanamenu ventanamenu) {
		this.vm = ventanamenu;
	}

	public void agregarFolio() throws FileNotFoundException, IOException, NotBoundException {
		vm.agregarFolio();
	}

	public void agregarRevision() throws FileNotFoundException, IOException, NotBoundException {
		vm.agregarRevision();
	}

	public void listarFolios() throws FileNotFoundException, IOException, NotBoundException {
		vm.listarFolios();
	}

	public void darDescripcion() throws FileNotFoundException, IOException, NotBoundException {
		vm.darDescripcion();
	}

	public void listarRevisiones() throws FileNotFoundException, IOException, NotBoundException {
		vm.listarRevisiones();
	}

	public void folioMasRevisado() throws FileNotFoundException, IOException, NotBoundException {
		vm.folioMasRevisado();
	}

	public void eliminarFolioRevisiones() throws FileNotFoundException, IOException, NotBoundException {
		vm.eliminarFolioRevisiones();
	}
}
