package grafica.controladores;

import ventanas.agregarFolio;

public class ControladoragregarFolio {

	private agregarFolio caf;

	public ControladoragregarFolio(agregarFolio ventana) {
		this.caf = ventana;
	}

	public void agregarFolio(String codigo, String caratula, int paginas) {
		System.out.println("Codigo: "+codigo+" Caratula: "+caratula+" Paginas: "+paginas);
	}

}
