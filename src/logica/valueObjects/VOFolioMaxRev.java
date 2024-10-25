
package logica.valueObjects;

public class VOFolioMaxRev extends FolioVO {
	private int cantRevisiones;

	public VOFolioMaxRev(String codigo, String caratula, int paginas, int cantRevisiones) {
		super(codigo, caratula, paginas);
		this.cantRevisiones = cantRevisiones;
	}

	public int getCantRevisiones() {
		return cantRevisiones;
	}

}
