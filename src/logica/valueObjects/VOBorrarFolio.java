package logica.valueObjects;

import java.io.Serializable;

public class VOBorrarFolio implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codFolio;

	public VOBorrarFolio(String codigo) {
		this.codFolio = codigo;
	}

	public String getCodFolio() {
		return this.codFolio;
	}
}