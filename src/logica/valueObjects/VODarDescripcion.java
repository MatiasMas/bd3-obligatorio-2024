package logica.valueObjects;

public class VODarDescripcion {
	private String codFolio;
	private int numRevision;

	public VODarDescripcion(String codFolio, int numRevision) {
		this.codFolio = codFolio;
		this.numRevision = numRevision;
	}

	public String getCodFolio() {
		return this.codFolio;
	}

	public int getNumRevision() {
		return this.numRevision;
	}
}
