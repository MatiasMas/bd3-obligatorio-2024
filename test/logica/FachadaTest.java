package logica;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logica.entidades.Folio;
import logica.excepciones.FolioYaExisteException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import utilidades.GetProperties;

class FachadaTest {
	static Connection con = null;
	
	@BeforeAll
	static void conectarBase() throws SQLException {
		String url = GetProperties.getInstancia().getString("url");
		String usr = GetProperties.getInstancia().getString("user");
		String pwd = GetProperties.getInstancia().getString("password");
		
		con = DriverManager.getConnection(url, usr, pwd);
	}

	@AfterAll
	static void cerrarConexionBase() throws SQLException {
		String consultaBorrado = "DELETE FROM folios WHERE codigo = 'CodigoA'";
		Statement stm = con.createStatement();
		assertEquals(1, stm.executeUpdate(consultaBorrado));
		
		stm.close();
		con.close();
	}

	@Test
	void testAgregarFolio_CaminoFeliz() throws RemoteException, PersistenciaException, FolioYaExisteException, SQLException {
		Fachada fachada = new Fachada();
		VOFolio voF = new VOFolio("CodigoA", "CaratulaGenerica", 30);

		fachada.agregarFolio(voF);

		String consultaSeleccion = "SELECT * FROM folios WHERE codigo = 'CodigoA'";
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(consultaSeleccion);
		rs.next();
		
		Folio folio = new Folio(rs.getString("codigo"), rs.getString("caratula"), rs.getInt("paginas"));

		rs.close();
		stm.close();
		
		assertEquals("CodigoA", folio.getCodigo());
		assertEquals("CaratulaGenerica", folio.getCaratula());
		assertEquals(30, folio.getPaginas());
	}

	@Test
	void testAgregarRevision() {
		fail("Not yet implemented");
	}

	@Test
	void testBorrarFolioRevisiones() {
		fail("Not yet implemented");
	}

	@Test
	void testDarDescripcion() {
		fail("Not yet implemented");
	}

	@Test
	void testListarFolios() {
		fail("Not yet implemented");
	}

	@Test
	void testListarRevisiones() {
		fail("Not yet implemented");
	}

	@Test
	void testFolioMasRevisado() {
		fail("Not yet implemented");
	}

}
