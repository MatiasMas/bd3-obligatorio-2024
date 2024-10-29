package persistencia.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logica.entidades.Revision;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VORevision;
import persistencia.consultas.Consultas;
import utilidades.GetProperties;

public class DAORevisiones {
	private String url = GetProperties.getInstancia().getString("url");
	private String usr = GetProperties.getInstancia().getString("user");
	private String pwd = GetProperties.getInstancia().getString("password");
	private String codFolio;

	public DAORevisiones(String codF) {
		this.codFolio = codF;
	}

	public void insback(Revision rev) {
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

	public int largo() {
		int largo = 0;

		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);

			Consultas consultas = new Consultas();
			String query = consultas.contarRevisionesPorFolio();
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setString(1, this.codFolio);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				largo = rs.getInt(1);
			}

			pstmt.close();
			con.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}

		return largo;
	}

	public Revision kesimo(int numero) {
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

	public List<VORevision> listarRevisiones() {
		List<VORevision> revisiones = new ArrayList<>();

		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);

			Consultas consultas = new Consultas();
			String query = consultas.listarRevisionesPorFolio();
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setString(1, this.codFolio);

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
