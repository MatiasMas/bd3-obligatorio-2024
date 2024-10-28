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
}
