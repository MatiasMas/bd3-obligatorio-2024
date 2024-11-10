package logica.valueObjects;

import java.io.Serializable;

public class VODarDescripcion implements Serializable {

	private static final long serialVersionUID = 1L;
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
