package test;

import java.util.List;

import logica.entidades.Folio;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import persistencia.daos.DAOFolios;
import poolConexiones.IConexion;
import poolConexiones.PoolConexiones;

public class DAOFoliosTest {

	private PoolConexiones poolConexiones;

	public DAOFoliosTest() {
		try {
			poolConexiones = new PoolConexiones();
		} catch (Exception e) {
			System.out.println("Error al inicializar PoolConexiones: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		DAOFoliosTest tester = new DAOFoliosTest();

		try {
			// Limpiar la base de datos antes de las pruebas
			tester.clearDatabase();

			tester.testMember();
			tester.testInsert();
			tester.testFind();
			tester.testDelete();
			tester.testListarFolios();
			tester.testEsVacio();
			tester.testFolioMasRevisado();

			System.out.println("Todas las pruebas se completaron correctamente.");
		} catch (Exception e) {
			System.out.println("Error en la prueba: " + e.getMessage());
		}
	}

	// Metodo para limpiar la base de datos antes de ejecutar las pruebas
	private void clearDatabase() throws PersistenciaException {
		DAOFolios dao = new DAOFolios();
		IConexion conexion = poolConexiones.obtenerConexion(true);

		// Obtener todos los folios y eliminarlos
		List<VOFolio> foliosExistentes = dao.listarFolios(conexion);
		for (VOFolio voFolio : foliosExistentes) {
			dao.delete(conexion, voFolio.getCodigo());
		}

		poolConexiones.liberarConexion(conexion, true);
		System.out.println("Base de datos limpiada antes de las pruebas.");
	}

	private void testMember() throws PersistenciaException {
		DAOFolios dao = new DAOFolios();
		IConexion conexion = poolConexiones.obtenerConexion(true);

		String codigoFolio = "F001";
		Folio folio = new Folio(codigoFolio, "Caratula de prueba", 10);

		// Insertamos el folio de prueba para verificar la existencia
		dao.insert(conexion, folio);

		// Verificamos si el folio existe
		boolean resultado = dao.member(conexion, codigoFolio);

		if (resultado) {
			System.out.println("testMember paso.");
		} else {
			throw new RuntimeException("testMember fallo: el folio deberia existir.");
		}

		// Eliminamos el folio de prueba para limpiar la base de datos
		dao.delete(conexion, codigoFolio);
		poolConexiones.liberarConexion(conexion, true);
	}

	private void testInsert() throws PersistenciaException {
		DAOFolios dao = new DAOFolios();
		IConexion conexion = poolConexiones.obtenerConexion(true);

		Folio nuevoFolio = new Folio("F002", "Caratula de prueba", 10);
		dao.insert(conexion, nuevoFolio);

		boolean existe = dao.member(conexion, "F002");

		if (existe) {
			System.out.println("testInsert paso.");
		} else {
			throw new RuntimeException("testInsert fallo: el folio no fue insertado correctamente.");
		}

		poolConexiones.liberarConexion(conexion, true);
	}

	private void testFind() throws PersistenciaException {
		DAOFolios dao = new DAOFolios();
		IConexion conexion = poolConexiones.obtenerConexion(true);

		String codigoFolio = "F001";
		Folio folio = new Folio(codigoFolio, "Caratula de prueba", 10);

		// Insertamos el folio de prueba para que luego pueda ser encontrado
		dao.insert(conexion, folio);

		// Buscamos el folio recien insertado
		Folio folioEncontrado = dao.find(conexion, codigoFolio);

		if (folioEncontrado != null && folioEncontrado.getCodigo().equals(codigoFolio)) {
			System.out.println("testFind paso.");
		} else {
			throw new RuntimeException("testFind fallo: el folio deberia haber sido encontrado.");
		}

		// Eliminamos el folio de prueba para limpiar la base de datos
		dao.delete(conexion, codigoFolio);
		poolConexiones.liberarConexion(conexion, true);
	}

	private void testDelete() throws PersistenciaException {
		DAOFolios dao = new DAOFolios();
		IConexion conexion = poolConexiones.obtenerConexion(true);

		String codigo = "F002";
		dao.delete(conexion, codigo);

		boolean existe = dao.member(conexion, codigo);

		if (!existe) {
			System.out.println("testDelete paso.");
		} else {
			throw new RuntimeException("testDelete fallo: el folio no fue eliminado correctamente.");
		}

		poolConexiones.liberarConexion(conexion, true);
	}

	private void testListarFolios() throws PersistenciaException {
		DAOFolios dao = new DAOFolios();
		IConexion conexion = poolConexiones.obtenerConexion(false);

		// Insertar folios temporales
		Folio folio1 = new Folio("T001", "Folio temporal 1", 5);
		Folio folio2 = new Folio("T002", "Folio temporal 2", 10);
		Folio folio3 = new Folio("T003", "Folio temporal 3", 15);

		dao.insert(conexion, folio1);
		dao.insert(conexion, folio2);
		dao.insert(conexion, folio3);

		// Listar los folios y verificar si los temporales estan presentes
		List<VOFolio> folios = dao.listarFolios(conexion);

		if (folios != null && folios.size() == 3) {
			System.out.println("testListarFolios paso.");
		} else {
			throw new RuntimeException(
					"testListarFolios fallo: los folios temporales no fueron listados correctamente.");
		}

		// Eliminar folios temporales
		dao.delete(conexion, "T001");
		dao.delete(conexion, "T002");
		dao.delete(conexion, "T003");

		poolConexiones.liberarConexion(conexion, true);
		System.out.println("Folios temporales eliminados despues de listar.");
	}

	private void testEsVacio() throws PersistenciaException {
		DAOFolios dao = new DAOFolios();
		IConexion conexion = poolConexiones.obtenerConexion(true);

		// Insertar un folio temporal para asegurar que no este vacio
		Folio folioTemporal = new Folio("TEMP001", "Folio temporal", 1);
		dao.insert(conexion, folioTemporal);

		boolean vacio = dao.esVacio(conexion);

		if (!vacio) {
			System.out.println("testEsVacio paso.");
		} else {
			throw new RuntimeException("testEsVacio fallo: la tabla de folios no deberia estar vacia.");
		}

		// Eliminar el folio temporal para limpiar la base de datos
		dao.delete(conexion, "TEMP001");

		poolConexiones.liberarConexion(conexion, true);
		System.out.println("Folio temporal eliminado despues de la prueba.");
	}

	private void testFolioMasRevisado() throws PersistenciaException {
		DAOFolios dao = new DAOFolios();
		IConexion conexion = poolConexiones.obtenerConexion(true);

		// Insertar algunos folios temporales para la prueba
		Folio folioTemporal1 = new Folio("TEMP002", "Folio temporal 1", 5);
		Folio folioTemporal2 = new Folio("TEMP003", "Folio temporal 2", 10);
		Folio folioTemporal3 = new Folio("TEMP004", "Folio temporal 3", 15);

		dao.insert(conexion, folioTemporal1);
		dao.insert(conexion, folioTemporal2);
		dao.insert(conexion, folioTemporal3);

		// Obtener el folio mas revisado
		VOFolioMaxRev folioMasRevisado = dao.folioMasRevisado(conexion);

		if (folioMasRevisado != null) {
			System.out.println("testFolioMasRevisado paso.");
		} else {
			throw new RuntimeException("testFolioMasRevisado fallo: no se encontro el folio mas revisado.");
		}

		// Eliminar los folios temporales para limpiar la base de datos
		dao.delete(conexion, "TEMP002");
		dao.delete(conexion, "TEMP003");
		dao.delete(conexion, "TEMP004");

		poolConexiones.liberarConexion(conexion, true);
		System.out.println("Folios temporales eliminados despues de la prueba.");
	}

}
