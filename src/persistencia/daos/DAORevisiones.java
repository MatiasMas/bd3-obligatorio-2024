package persistencia.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logica.Revision;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VORevision;
import persistencia.consultas.Consultas;

public class DAORevisiones {

	private String url;
	private String usr;
	private String pwd;

	private String codFolio = "";	
	
	public DAORevisiones(String codF) {
		super();
		this.codFolio = codF;
	}

	public DAORevisiones(String url, String usr, String pwd) {
		this.url = url;
		this.usr = usr;
		this.pwd = pwd;
	}

	public boolean member(int numero) {
		boolean existeRevision = false;

		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);
			Consultas consultas = new Consultas();
			String query = consultas.existeRevision();

			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setInt(1, numero);

			ResultSet rs = pstmt1.executeQuery();
			if (rs.next()) {
				existeRevision = true;
			}

			rs.close();
			pstmt1.close();
			con.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}

		return existeRevision;
	}

	public void insert(Revision rev) {
		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);

			Consultas consultas = new Consultas();
			String query = consultas.agregarRevision();
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setInt(1, rev.getNumero());
			pstmt.setString(2, rev.getDescripcion());
			pstmt.setString(3, this.codFolio);

			pstmt.executeUpdate();

			pstmt.close();
			con.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}
	}
	
	public Revision find(int numero) {
		Revision revision = null;

		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);

			Consultas consultas = new Consultas();
			String query = consultas.existeRevision();
			PreparedStatement pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, this.codFolio);
			pstmt.setInt(2, numero);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				revision = new Revision(rs.getInt("numero"), rs.getString("descripcion"));
			}

			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}
		return revision;
	}
	
	@Deprecated
	public VORevision find(String codFolio, int numero) {
		VORevision revision = null;

		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);

			Consultas consultas = new Consultas();
			String query = consultas.existeRevision();
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setString(1, codFolio);
			pstmt.setInt(2, numero);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				revision = new VORevision();
				revision.setNumero(rs.getInt("numero"));
				revision.setDescripcion(rs.getString("descripcion"));
				revision.setCodFolio(rs.getString("codFolio"));
			}

			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}

		return revision;
	}

	public List<VORevision> listarRevisiones() {
		List<VORevision> revisiones = new ArrayList<VORevision>();

		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);

			Consultas consultas = new Consultas();
			String query = consultas.listarRevisiones();
			PreparedStatement pstmt = con.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				VORevision revision = new VORevision();
				revision.setNumero(rs.getInt("numero"));
				revision.setDescripcion(rs.getString("descripcion"));
				revision.setCodFolio(rs.getString("codFolio"));

				revisiones.add(revision);
			}

			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}

		return revisiones;
	}
	
	public int size() {
		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);

			Consultas consultas = new Consultas();
			String query = consultas.contarRevisiones();
			PreparedStatement pstmt = con.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();
			int sz = 0;
			if (rs.next()) {
				sz = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			con.close();
			return sz;
			
		} catch (SQLException e) {
			throw new PersistenciaException();
		}
	}
	
	public void borrarRevisiones() {
	   try {
		   Connection con = DriverManager.getConnection(url, usr, pwd);

		   Consultas consultas = new Consultas();
		   String query = consultas.eliminarRevisionesPorFolio();

	       PreparedStatement pstmt = con.prepareStatement(query);

	       pstmt.setString(1, this.codFolio);
	       pstmt.executeUpdate();
	       
	       pstmt.close();
	       con.close();

	    } catch (SQLException e) {
	        throw new PersistenciaException();
	    }
	}
	
	

}
