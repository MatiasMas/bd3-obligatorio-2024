package persistencia.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logica.entidades.Folio;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import persistencia.consultas.Consultas;
import poolConexiones.Conexion;
import poolConexiones.IConexion;

public class DAOFoliosMySQL implements IDAOFolios{
	public DAOFoliosMySQL() {

	}

	public boolean member(IConexion icon, String codigo) throws PersistenciaException {

		boolean existeFolio = false;

		try {

			Consultas consultas = new Consultas();
			Conexion con = (Conexion) icon;
			String query = consultas.existeFolio();

			PreparedStatement pstmt1 = con.getCon().prepareStatement(query);
			pstmt1.setString(1, codigo);

			ResultSet rs = pstmt1.executeQuery();

			if (rs.next())
				existeFolio = true;

			rs.close();
			pstmt1.close();
		} catch (SQLException e) {
			System.out.println(e);
			throw new PersistenciaException();
		}

		return existeFolio;
	}

	public void insert(IConexion icon, Folio fol) throws PersistenciaException {
		try {
//			Connection con = DriverManager.getConnection(url, usr, pwd);

			Consultas consultas = new Consultas();
			String query = consultas.agregarFolio();
			Conexion con = (Conexion) icon;
			PreparedStatement pstmt = con.getCon().prepareStatement(query);

			pstmt.setString(1, fol.getCodigo());
			pstmt.setString(2, fol.getCaratula());
			pstmt.setInt(3, fol.getPaginas());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}
	}

	public Folio find(IConexion icon, String cod) throws PersistenciaException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Folio folio = null;

		try {

			Consultas consultas = new Consultas();
			String query = consultas.existeFolio();
			Conexion con = (Conexion) icon;
			PreparedStatement pstmt = con.getCon().prepareStatement(query);

			pstmt.setString(1, cod);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				folio = new Folio(rs.getString("codigo"), rs.getString("caratula"), rs.getInt("paginas"));
			}

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}

		return folio;
	}

	public void delete(IConexion icon, String cod) throws PersistenciaException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		try {
			Consultas consultas = new Consultas();
			String deleteRevisiones = consultas.eliminarFolio();
			Conexion con = (Conexion) icon;
			PreparedStatement borrarR = null;

			Folio f = this.find(icon, cod);
			f.borrarRevisiones(icon);
			borrarR = con.getCon().prepareStatement(deleteRevisiones);
			borrarR.setString(1, cod);
			borrarR.executeUpdate();
			borrarR.close();

		} catch (SQLException e) {
			throw new PersistenciaException("Error en la persistencia");
		}
	}

	public List<VOFolio> listarFolios(IConexion icon) throws PersistenciaException {
		List<VOFolio> folios = new ArrayList<>();

		try {
			Conexion con = (Conexion) icon;

			Consultas consultas = new Consultas();
			String query = consultas.listarFolios();

			PreparedStatement pstmt = con.getCon().prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				VOFolio folio = new VOFolio(rs.getString("codigo"), rs.getString("caratula"), rs.getInt("paginas"));

				folios.add(folio);
			}

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}

		return folios;
	}

	public boolean esVacio(IConexion icon) throws PersistenciaException {
		boolean existenFolios = false;

		try {
			Conexion con = (Conexion) icon;
			Consultas consultas = new Consultas();
			String query = consultas.contarFolios();

			Statement stm = con.getCon().createStatement();

			ResultSet rs = stm.executeQuery(query);

			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					existenFolios = true;
				}
			}

			rs.close();
			stm.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}

		return !existenFolios;
	}

	public VOFolioMaxRev folioMasRevisado(IConexion icon) throws PersistenciaException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		VOFolioMaxRev voFolio = null;
		int maxRevisiones = -1;

		try {
			Consultas consultas = new Consultas();
			String query = consultas.listarFolios();
			Conexion con = (Conexion) icon;
			Statement stm = con.getCon().createStatement();

			ResultSet rs = stm.executeQuery(query);

			while (rs.next()) {
				Folio folio = new Folio(rs.getString("codigo"), rs.getString("caratula"), rs.getInt("paginas"));

				int cantidadRevisiones = folio.cantidadRevisiones(con);

				if (cantidadRevisiones > maxRevisiones) {
					voFolio = new VOFolioMaxRev(folio.getCodigo(), folio.getCaratula(), folio.getPaginas(),
							cantidadRevisiones);
					maxRevisiones = cantidadRevisiones;
				}
			}

			rs.close();
			stm.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}

		return voFolio;
	}
}