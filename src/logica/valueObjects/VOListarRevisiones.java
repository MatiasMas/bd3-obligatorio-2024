package logica.valueObjects;

public class VOListarRevisiones {
	private String codFolio;

	public VOListarRevisiones(String codFolio) {
		this.codFolio = codFolio;
	}

	public String getCodFolio() {
		return this.codFolio;
	}
}