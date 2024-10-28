package grafica.controladores;

import ventanas.agregarRevision;

public class ControladoragregarRevision {

	private agregarRevision ar;

	public ControladoragregarRevision(agregarRevision ventana) {
		this.ar = ventana;
	}

	public void agregarRevision(int codigoFolio, String descripcion) {
		System.out.println("CodigoFolio: " + codigoFolio + " Descripcion: " + descripcion);
	}
}
