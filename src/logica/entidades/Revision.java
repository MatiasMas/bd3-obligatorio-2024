package logica.entidades;

public class Revision {
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
}