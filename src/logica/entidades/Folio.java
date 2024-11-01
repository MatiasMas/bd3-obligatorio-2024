package logica.entidades;

import java.util.List;

import logica.excepciones.PersistenciaException;
import logica.valueObjects.VORevision;
import persistencia.daos.DAORevisiones;

public class Folio {
	private String codigo;
	private String caratula;
	private int paginas;
	private DAORevisiones secuencia;

	public Folio(String codigo, String caratula, int paginas) {
		this.codigo = codigo;
		this.caratula = caratula;
		this.paginas = paginas;
		this.secuencia = new DAORevisiones(this.codigo);
	}

	public String getCodigo() {
		return codigo;
	}

	public String getCaratula() {
		return caratula;
	}

	public int getPaginas() {
		return paginas;
	}

	public boolean tieneRevision(int numR) throws PersistenciaException {
		Revision revision = secuencia.kesimo(numR);
		
		return (revision != null);
	}

	public int cantidadRevisiones() throws PersistenciaException {
		return secuencia.largo();
	}

	public void addRevision(Revision rev) throws PersistenciaException {
     	secuencia.insback(rev);
	}

	public Revision obtenerRevision(int numR) throws PersistenciaException {
		return secuencia.kesimo(numR);
	}

	public List<VORevision> listarRevisiones() throws PersistenciaException {
		  return secuencia.listarRevisiones();
	}

	public void borrarRevisiones() throws PersistenciaException {
		secuencia.borrarRevisiones();
	}
}