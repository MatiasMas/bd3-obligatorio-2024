package logica.valueObjects;

import java.io.Serializable;

public class VOFolio implements Serializable {

	private static final long serialVersionUID = 1L;
	private String codigo;
	private String caratula;
	private int paginas;

	public VOFolio() {
//		super();
	}

	public VOFolio(String codigo, String caratula, int paginas) {
		this.codigo = codigo;
		this.caratula = caratula;
		this.paginas = paginas;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCaratula() {
		return caratula;
	}

	public void setCaratula(String caratula) {
		this.caratula = caratula;
	}

	public int getPaginas() {
		return paginas;
	}

	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}
}
