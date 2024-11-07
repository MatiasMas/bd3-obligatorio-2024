package logica.entidades;

import java.io.Serializable;
import java.util.List;

import logica.excepciones.PersistenciaException;
import logica.valueObjects.VORevision;
import persistencia.daos.DAORevisionesMySQL;
import persistencia.daos.IDAORevisiones;
import poolConexiones.IConexion;

public class Folio implements Serializable {

	private static final long serialVersionUID = -9054308125777347738L;

	private String codigo;
	private String caratula;
	private int paginas;
	private IDAORevisiones secuencia;

	public Folio() {
		super();
	}

	public Folio(String codigo, String caratula, int paginas) {
		this.codigo = codigo;
		this.caratula = caratula;
		this.paginas = paginas;
		//TODO: aca se va a instanciar usando abstract factory (pagina 63 del teorico 3 capas)
		this.secuencia = new DAORevisionesMySQL(this.codigo);
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

	public boolean tieneRevision(IConexion icon, int numR) throws PersistenciaException {
		Revision revision = secuencia.kesimo(icon, numR);

		return (revision != null);
	}

	public int cantidadRevisiones(IConexion icon) throws PersistenciaException {
		return secuencia.largo(icon);
	}

	public void addRevision(IConexion icon, Revision rev) throws PersistenciaException {
		secuencia.insback(icon, rev);
	}

	public Revision obtenerRevision(IConexion icon, int numR) throws PersistenciaException {
		return secuencia.kesimo(icon, numR);
	}

	public List<VORevision> listarRevisiones(IConexion icon) throws PersistenciaException {
		return secuencia.listarRevisiones(icon);
	}

	public void borrarRevisiones(IConexion icon) throws PersistenciaException {
		secuencia.borrarRevisiones(icon);
	}
}
