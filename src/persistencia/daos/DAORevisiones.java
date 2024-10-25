package persistencia.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logica.valueObjects.VORevision;
import persistencia.consultas.Consultas;

public class DAORevisiones {
	private String url = "";
	private String usr = "";
	private String pwd = "";
	private String codigoFolio;

	public DAORevisiones(String codF) {
		this.codigoFolio = codF;
	}

	public void insback(VORevision rev) {
		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);

			Consultas consultas = new Consultas();
			String query = consultas.agregarRevision();
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setInt(1, rev.getNumero());
			pstmt.setString(2, rev.getDescripcion());
			pstmt.setString(3, rev.getCodigoFolio());

			pstmt.executeUpdate();

			pstmt.close();
			con.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}
	}

	public int largo() {
		return 1;
	}

	public boolean ksimo(int numero) {
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
		List<VORevision> revisiones = new ArrayList<>();

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

	public void borrarRevisiones() {

	}
}