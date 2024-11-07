package persistencia.daos;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logica.entidades.Revision;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VORevision;
import persistencia.consultas.Consultas;
import poolConexiones.Conexion;
import poolConexiones.IConexion;

public class DAORevisionesMySQL implements IDAORevisiones, Serializable {

	private static final long serialVersionUID = 444443211990804249L;
	private String codFolio;

	public DAORevisionesMySQL() {

	}

	public DAORevisionesMySQL(String codF) {
		this.codFolio = codF;
	}

	public void insback(IConexion icon, Revision rev) throws PersistenciaException {
		try {

			Consultas consultas = new Consultas();
			String query = consultas.agregarRevision();
			Conexion con = (Conexion) icon;
			PreparedStatement pstmt = con.getCon().prepareStatement(query);

			pstmt.setInt(1, rev.getNumero());
			pstmt.setString(2, this.codFolio);
			pstmt.setString(3, rev.getDescripcion());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			throw new PersistenciaException(e.getMessage());
		}
	}

	public int largo(IConexion icon) throws PersistenciaException {
		int largo = 0;

		try {

			Consultas consultas = new Consultas();
			String query = consultas.contarRevisionesPorFolio();
			Conexion con = (Conexion) icon;
			PreparedStatement pstmt = con.getCon().prepareStatement(query);

			pstmt.setString(1, this.codFolio);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				largo = rs.getInt(1);
			}

			pstmt.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}

		return largo;
	}

	public Revision kesimo(IConexion icon, int numero) throws PersistenciaException {
		Revision revision = null;

		try {

			Consultas consultas = new Consultas();
			String query = consultas.existeRevision();
			Conexion con = (Conexion) icon;
			PreparedStatement pstmt = con.getCon().prepareStatement(query);

			pstmt.setString(1, this.codFolio);
			pstmt.setInt(2, numero);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				revision = new Revision(rs.getInt("numero"), rs.getString("descripcion"));
			}

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}

		return revision;
	}

	public List<VORevision> listarRevisiones(IConexion icon) throws PersistenciaException {
		List<VORevision> revisiones = new ArrayList<>();

		try {

			Consultas consultas = new Consultas();
			String query = consultas.listarRevisionesPorFolio();
			Conexion con = (Conexion) icon;
			PreparedStatement pstmt = con.getCon().prepareStatement(query);

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
		} catch (SQLException e) {
			throw new PersistenciaException();
		}

		return revisiones;
	}

	public void borrarRevisiones(IConexion icon) throws PersistenciaException {
		try {

			Consultas consultas = new Consultas();
			String query = consultas.eliminarRevisionesPorFolio();
			Conexion con = (Conexion) icon;
			PreparedStatement pstmt = con.getCon().prepareStatement(query);

			pstmt.setString(1, this.codFolio);
			pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e) {
			throw new PersistenciaException();
		}
	}
}
