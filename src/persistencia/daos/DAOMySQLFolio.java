package persistencia.daos;

import java.sql.Connection;
import java.sql.DriverManager;
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
import utilidades.Configuracion;

public class DAOMySQLFolio implements IDAOFolios {
	
	private String url = Configuracion.getInstancia().getUrl();
	private String usr = Configuracion.getInstancia().getUser();
	private String pwd = Configuracion.getInstancia().getPassword();

	public DAOMySQLFolio() {

	}

	public boolean member(String codigo) throws PersistenciaException {

		boolean existeFolio = false;

		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);

			System.out.println("url: " + url + " usr: " + usr + " pwd: " + pwd);
			Consultas consultas = new Consultas();
			String query = consultas.existeFolio();

			PreparedStatement pstmt1 = con.prepareStatement(query);
			pstmt1.setString(1, codigo);

			ResultSet rs = pstmt1.executeQuery();

			if (rs.next())
				existeFolio = true;

			rs.close();
			pstmt1.close();
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("url: " + url + " usr: " + usr + " pwd: " + pwd);
			throw new PersistenciaException();
		}

		return existeFolio;
	}

	public void insert(Folio fol) throws PersistenciaException {
		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);

			Consultas consultas = new Consultas();
			String query = consultas.agregarFolio();
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setString(1, fol.getCodigo());
			pstmt.setString(2, fol.getCaratula());
			pstmt.setInt(3, fol.getPaginas());

			pstmt.executeUpdate();

			pstmt.close();
			con.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}
	}

	public Folio find(String cod) throws PersistenciaException {
		Folio folio = null;

		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);

			Consultas consultas = new Consultas();
			String query = consultas.existeFolio();

			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setString(1, cod);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				folio = new Folio(rs.getString("codigo"), rs.getString("caratula"), rs.getInt("paginas"));
			}

			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}

		return folio;
	}

	public void delete(String cod) throws PersistenciaException {
		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);

			Consultas consultas = new Consultas();
			String queryExisteFolio = consultas.existeFolio();

			//TODO: Mover esto a la fachada, es parte de la logica
			PreparedStatement pstmt = con.prepareStatement(queryExisteFolio);

			pstmt.setString(1, cod);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Folio folio = new Folio(rs.getString("codigo"), rs.getString("caratula"), rs.getInt("paginas"));
				folio.borrarRevisiones();

				String queryBorrarFolio = consultas.eliminarFolio();
				PreparedStatement pstmt2 = con.prepareStatement(queryBorrarFolio);
				pstmt2.setString(1, cod);

				pstmt2.executeUpdate();

				pstmt2.close();
			}

			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}
	}

	public List<VOFolio> listarFolios() throws PersistenciaException {
		List<VOFolio> folios = new ArrayList<>();

		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);

			Consultas consultas = new Consultas();
			String query = consultas.listarFolios();

			PreparedStatement pstmt = con.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				VOFolio folio = new VOFolio(rs.getString("codigo"), rs.getString("caratula"), rs.getInt("paginas"));

				folios.add(folio);
			}

			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}

		return folios;
	}

	public boolean esVacio() throws PersistenciaException {
		boolean existenFolios = false;

		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);
			Consultas consultas = new Consultas();
			String query = consultas.contarFolios();

			Statement stm = con.createStatement();

			ResultSet rs = stm.executeQuery(query);

			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					existenFolios = true;
				}
			}

			rs.close();
			stm.close();
			con.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}

		return !existenFolios;
	}

	public VOFolioMaxRev folioMasRevisado() throws PersistenciaException {
		VOFolioMaxRev voFolio = null;
		int maxRevisiones = -1;

		try {
			Connection con = DriverManager.getConnection(url, usr, pwd);
			Consultas consultas = new Consultas();
			String query = consultas.listarFolios();

			Statement stm = con.createStatement();

			ResultSet rs = stm.executeQuery(query);

			while (rs.next()) {
				Folio folio = new Folio(rs.getString("codigo"), rs.getString("caratula"), rs.getInt("paginas"));

				int cantidadRevisiones = folio.cantidadRevisiones();

				if (cantidadRevisiones > maxRevisiones) {
					voFolio = new VOFolioMaxRev(folio.getCodigo(), folio.getCaratula(), folio.getPaginas(),
							cantidadRevisiones);
				}
			}

			rs.close();
			stm.close();
			con.close();
		} catch (SQLException e) {
			throw new PersistenciaException();
		}

		return voFolio;
	}


}
