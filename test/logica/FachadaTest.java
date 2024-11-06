package logica;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
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
import logica.entidades.Revision;
import logica.excepciones.FolioNoExisteException;
import logica.excepciones.FolioYaExisteException;
import logica.excepciones.NoHayRevisionesException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOBorrarFolio;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VORevision;
import utilidades.Configuracion;

class FachadaTest {
	static Connection con = null;
	
	@BeforeAll
	static void conectarBase() throws SQLException {
		String url = Configuracion.getInstancia().getUrl();
		String usr = Configuracion.getInstancia().getUser();
		String pwd = Configuracion.getInstancia().getPassword();
		
		con = DriverManager.getConnection(url, usr, pwd);
	}

	@AfterAll
	static void cerrarConexionBase() throws SQLException {
		con.close();
	}

	@Test
	void testAgregarFolio_FolioNoExiste() throws RemoteException, PersistenciaException, FolioYaExisteException, SQLException, InstantiationException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		try {
			Fachada fachada = new Fachada();
			VOFolio voF = new VOFolio("CodigoA", "CaratulaGenerica A", 30);

			fachada.agregarFolio(voF);

			String consultaSeleccion = "SELECT * FROM folios WHERE codigo = 'CodigoA'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(consultaSeleccion);
			rs.next();
			
			Folio folio = new Folio(rs.getString("codigo"), rs.getString("caratula"), rs.getInt("paginas"));

			rs.close();
			stm.close();
			
			assertEquals("CodigoA", folio.getCodigo());
			assertEquals("CaratulaGenerica A", folio.getCaratula());
			assertEquals(30, folio.getPaginas());
		} finally {
			String consultaBorrado = "DELETE FROM folios WHERE codigo = 'CodigoA'";
			Statement stm = con.createStatement();
			assertEquals(1, stm.executeUpdate(consultaBorrado));
			
			stm.close();
		}
	}
	
	@Test
	void testAgregarFolio_FolioYaExiste() throws RemoteException, PersistenciaException, SQLException, InstantiationException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, FolioYaExisteException {
		try {
			Fachada fachada = new Fachada();
			VOFolio voF1 = new VOFolio("CodigoB", "CaratulaGenerica B", 40);
			VOFolio voF2 = new VOFolio("CodigoB", "CaratulaGenerica B", 40);

			fachada.agregarFolio(voF1);
			
			assertThrows(FolioYaExisteException.class, () -> {
				fachada.agregarFolio(voF2);
			});
		} finally {		
			String consultaBorrado = "DELETE FROM folios WHERE codigo = 'CodigoB'";
			Statement stm = con.createStatement();
			assertEquals(1, stm.executeUpdate(consultaBorrado));

			stm.close();
		}
	}

	@Test
	void testAgregarRevision_FolioNoExiste() throws RemoteException, InstantiationException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Fachada fachada = new Fachada();
		VORevision voR = new VORevision(1, "Descripcion Revision 1", "CodigoC");

		assertThrows(FolioNoExisteException.class, () -> {
			fachada.agregarRevision(voR);
		});
	}
	
	@Test
	void testAgregarRevision_FolioExiste() throws RemoteException, InstantiationException, ClassNotFoundException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException, PersistenciaException, FolioYaExisteException, SQLException, FolioNoExisteException {
		try {
			Fachada fachada = new Fachada();
			VOFolio voF = new VOFolio("CodigoC", "CaratulaGenerica C", 50);
			VORevision voR = new VORevision(1, "Descripcion Revision 1", "CodigoC");

			fachada.agregarFolio(voF);

			fachada.agregarRevision(voR);
			
			String consultaSeleccion = "SELECT * FROM revisiones WHERE codFolio = 'CodigoC'";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(consultaSeleccion);
			rs.next();
			
			int revNumero = rs.getInt(1);
			String revCodFolio = rs.getString(2);
			String revDescripcion = rs.getString(3);

			rs.close();
			stm.close();
			
			assertEquals(1, revNumero);
			assertEquals("CodigoC", revCodFolio);
			assertEquals("Descripcion Revision 1", revDescripcion);
		} finally {
			String consultaBorradoRevision = "DELETE FROM revisiones WHERE codFolio = 'CodigoC'";
			Statement stm1 = con.createStatement();
			assertEquals(1, stm1.executeUpdate(consultaBorradoRevision));

			String consultaBorradoFolio = "DELETE FROM folios WHERE codigo = 'CodigoC'";
			Statement stm2 = con.createStatement();
			assertEquals(1, stm2.executeUpdate(consultaBorradoFolio));

			stm1.close();
			stm2.close();
		}
	}

	@Test
	void testBorrarFolioRevisiones_NoExisteFolio() throws RemoteException, InstantiationException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Fachada fachada = new Fachada();
		VOBorrarFolio voB = new VOBorrarFolio("CodigoD");

		assertThrows(FolioNoExisteException.class, () -> {
			fachada.borrarFolioRevisiones(voB);
		});
	}
	
//	@Test
//	void testBorrarFolioRevisiones_NoExistenRevisiones() throws RemoteException, InstantiationException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, PersistenciaException, FolioYaExisteException, SQLException {
//		try {
//			Fachada fachada = new Fachada();
//			VOFolio voF = new VOFolio("CodigoD", "CaratulaGenerica D", 60);
//			VOBorrarFolio voB = new VOBorrarFolio("CodigoD");
//			
//			fachada.agregarFolio(voF);
//
//			assertThrows(NoHayRevisionesException.class, () -> {
//				fachada.borrarFolioRevisiones(voB);
//			});
//		} finally {
//			String consultaBorradoFolio = "DELETE FROM folios WHERE codigo = 'CodigoD'";
//			Statement stm1 = con.createStatement();
//			assertEquals(1, stm1.executeUpdate(consultaBorradoFolio));
//
//			stm1.close();
//		}
//	}
	
	@Test
	void testBorrarFolioRevisiones_ExisteFolio() throws RemoteException, InstantiationException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, PersistenciaException, FolioYaExisteException, SQLException, FolioNoExisteException {
		Fachada fachada = new Fachada();
		VOFolio voF = new VOFolio("CodigoD", "CaratulaGenerica D", 60);
		VOBorrarFolio voB = new VOBorrarFolio("CodigoD");
		VORevision voR1 = new VORevision(1, "Descripcion Revision 1", "CodigoD");
		VORevision voR2 = new VORevision(2, "Descripcion Revision 2", "CodigoD");

		fachada.agregarFolio(voF);
		fachada.agregarRevision(voR1);
		fachada.agregarRevision(voR2);

		fachada.borrarFolioRevisiones(voB);

		String consultaSeleccionFolio = "SELECT * FROM folios WHERE codigo = 'CodigoD'";
		Statement stm1 = con.createStatement();
		ResultSet rs1 = stm1.executeQuery(consultaSeleccionFolio);
		
		String consultaSeleccionRevisiones = "SELECT * FROM revisiones WHERE codFolio = 'CodigoD'";
		Statement stm2 = con.createStatement();
		ResultSet rs2 = stm2.executeQuery(consultaSeleccionRevisiones);

		boolean folioEncontrado = rs1.next();
		boolean revisionesEncontradas = rs2.next();

		rs1.close();
		stm1.close();
		rs2.close();
		stm2.close();

		assertFalse(folioEncontrado);
		assertFalse(revisionesEncontradas);
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
