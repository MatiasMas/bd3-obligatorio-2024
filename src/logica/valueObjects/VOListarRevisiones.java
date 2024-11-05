package logica.valueObjects;

import java.io.Serializable;

public class VOListarRevisiones implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String codFolio;

	public VOListarRevisiones(String codFolio) {
		this.codFolio = codFolio;
	}

	public String getCodFolio() {
		return this.codFolio;
	}
}