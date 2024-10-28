package logica;

import java.util.List;

import logica.valueObjects.VORevision;
import persistencia.daos.DAORevisiones;

public class Folio {

	private String codigo;
	private String caratula;
	private int pagina;
	private DAORevisiones secuencia;

	public Folio(String codigo, String caratula, int pagina) {
		this.codigo = codigo;
		this.caratula = caratula;
		this.pagina = pagina;
		this.secuencia = new DAORevisiones(codigo);
	}

	public String getCodigo() {
		return codigo;
	}

	public String getCaratula() {
		return caratula;
	}

	public int getPagina() {
		return pagina;
	}

	public boolean tieneRevision(int numeroRevision) {
		return secuencia.member(numeroRevision);
	}

	public int cantidadRevisiones() {
		return secuencia.size();
	}

	public void addRevision(Revision rev) {
     	secuencia.insert(rev);
	}

	public Revision obtenerRevision(int numeroRevision) {
		return secuencia.find(numeroRevision);
	}

	public List<VORevision> listarRevisiones() {
		  return secuencia.listarRevisiones();
	}

	public void borrarRevisiones() {
		secuencia.borrarRevisiones();
	}
}