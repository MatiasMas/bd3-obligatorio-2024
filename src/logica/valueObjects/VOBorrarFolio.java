package logica.valueObjects;

public class VOBorrarFolio {
	private String codFolio;

	public VOBorrarFolio(String codigo) {
		this.codFolio = codigo;
	}

	public String getCodFolio() {
		return this.codFolio;
	}
}