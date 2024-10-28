//package logica;
//
//import java.util.List;
//
//import persistencia.daos.DAORevisiones;
//
//public class Folio {
//
//	private String codigo;
//	private String caratula;
//	private int pagina;
//	private DAORevisiones secuencia;
//
//	public Folio(String codigo, String caratula, int pagina) {
//		this.codigo = codigo;
//		this.caratula = caratula;
//		this.pagina = pagina;
//		this.secuencia = new DAORevisiones();
//	}
//
//	public String getCodigo() {
//		return codigo;
//	}
//
//	public String getCaratula() {
//		return caratula;
//	}
//
//	public int getPagina() {
//		return pagina;
//	}
//
//	public boolean tieneRevision(int numeroRevision) {
//		return secuencia.tieneRevision(numeroRevision);
//	}
//
//	public int cantidadRevisiones() {
//		return secuencia.cantidadRevisiones();
//	}
//
//	public void addRevision(Revision rev) {
//		secuencia.addRevision(rev);
//	}
//
//	public Revision obtenerRevision(int numeroRevision) {
//		return secuencia.obtenerRevision(numeroRevision);
//	}
//
//	public List<VOREvision> listarRevisiones() {
//		return secuencia.listarRevisiones();
//	}
//
//	public void borrarRevisiones() {
//		secuencia.borrarRevisiones();
//	}
//}