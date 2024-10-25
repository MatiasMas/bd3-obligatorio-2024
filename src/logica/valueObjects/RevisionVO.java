package logica.valueObjects;

public class RevisionVO {
	private int numero;
	private String descripcion;
	private String codFolio;

	public RevisionVO() {
		super();
	}

	public RevisionVO(int numero, String descripcion, String codFolio) {
		this.numero = numero;
		this.descripcion = descripcion;
		this.codFolio = codFolio;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodFolio() {
		return codFolio;
	}

	public void setCodFolio(String codFolio) {
		this.codFolio = codFolio;
	}

}