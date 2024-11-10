package logica.entidades;

import java.io.Serializable;

import logica.valueObjects.VODescripcionRetornada;

public class Revision implements Serializable {

	private static final long serialVersionUID = 3308446847347329678L;
	private int numero;
	private String descripcion;

	public Revision(int numero, String descripcion) {
		this.numero = numero;
		this.descripcion = descripcion;
	}

	public int getNumero() {
		return this.numero;
	}

	public String getDescripcion() {
		return this.descripcion;
	}
	
	public VODescripcionRetornada getVoDescripcion() {
		return new VODescripcionRetornada(this.descripcion);
	}
}