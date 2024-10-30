package grafica.controladores;

import grafica.ventanas.AgregarRevision;

public class ControladorAgregarRevision {

	private AgregarRevision ar;

	public ControladorAgregarRevision(AgregarRevision ventana) {
		this.ar = ventana;
	}

	public void agregarRevision(int codigoFolio, String descripcion) {
		System.out.println("CodigoFolio: " + codigoFolio + " Descripcion: " + descripcion);
	}
}
