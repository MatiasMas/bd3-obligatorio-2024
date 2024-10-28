//package persistencia.daos;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import logica.valueObjects.RevisionVO;
//import persistencia.consultas.Consultas;
//
//public class DAORevisiones {
//
//	private String url;
//	private String usr;
//	private String pwd;
//
//	public DAORevisiones() {
//		super();
//	}
//
//	public DAORevisiones(String url, String usr, String pwd) {
//		this.url = url;
//		this.usr = usr;
//		this.pwd = pwd;
//	}
//
//	public boolean member(int numero) {
//		boolean existeRevision = false;
//
//		try {
//			Connection con = DriverManager.getConnection(url, usr, pwd);
//			Consultas consultas = new Consultas();
//			String query = consultas.existeRevision();
//
//			PreparedStatement pstmt1 = con.prepareStatement(query);
//			pstmt1.setInt(1, numero);
//
//			ResultSet rs = pstmt1.executeQuery();
//			if (rs.next()) {
//				existeRevision = true;
//			}
//
//			rs.close();
//			pstmt1.close();
//			con.close();
//		} catch (SQLException e) {
//			throw new PersistenciaException();
//		}
//
//		return existeRevision;
//	}
//
//	public void insert(RevisionVO rev) {
//		try {
//			Connection con = DriverManager.getConnection(url, usr, pwd);
//
//			Consultas consultas = new Consultas();
//			String query = consultas.agregarRevision();
//			PreparedStatement pstmt = con.prepareStatement(query);
//
//			pstmt.setInt(1, rev.getNumero());
//			pstmt.setString(2, rev.getDescripcion());
//			pstmt.setString(3, rev.getCodigoFolio());
//
//			pstmt.executeUpdate();
//
//			pstmt.close();
//			con.close();
//		} catch (SQLException e) {
//			throw new PersistenciaException();
//		}
//	}
//
//	public RevisionVO find(String codFolio, int numero) {
//		RevisionVO revision = null;
//
//		try {
//			Connection con = DriverManager.getConnection(url, usr, pwd);
//
//			Consultas consultas = new Consultas();
//			String query = consultas.existeRevision();
//			PreparedStatement pstmt = con.prepareStatement(query);
//
//			pstmt.setString(1, codFolio);
//			pstmt.setInt(2, numero);
//
//			ResultSet rs = pstmt.executeQuery();
//			if (rs.next()) {
//				revision = new RevisionVO();
//				revision.setNumero(rs.getInt("numero"));
//				revision.setDescripcion(rs.getString("descripcion"));
//				revision.setCodFolio(rs.getString("codFolio"));
//			}
//
//			rs.close();
//			pstmt.close();
//			con.close();
//		} catch (SQLException e) {
//			throw new PersistenciaException();
//		}
//
//		return revision;
//	}
//
//	public List<RevisionVO> listarRevisiones() {
//		List<RevisionVO> revisiones = new ArrayList<>();
//
//		try {
//			Connection con = DriverManager.getConnection(url, usr, pwd);
//
//			Consultas consultas = new Consultas();
//			String query = consultas.listarRevisiones();
//			PreparedStatement pstmt = con.prepareStatement(query);
//
//			ResultSet rs = pstmt.executeQuery();
//			while (rs.next()) {
//				RevisionVO revision = new RevisionVO();
//				revision.setNumero(rs.getInt("numero"));
//				revision.setDescripcion(rs.getString("descripcion"));
//				revision.setCodFolio(rs.getString("codFolio"));
//
//				revisiones.add(revision);
//			}
//
//			rs.close();
//			pstmt.close();
//			con.close();
//		} catch (SQLException e) {
//			throw new PersistenciaException();
//		}
//
//		return revisiones;
//	}
//
//}
