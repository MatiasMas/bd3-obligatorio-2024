package logica.valueObjects;

import java.io.Serializable;

public class VODescripcionRetornada implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String descripcion;

	public VODescripcionRetornada(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}
}