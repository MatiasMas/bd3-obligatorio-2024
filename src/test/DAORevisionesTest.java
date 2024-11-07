package test;

import java.util.List;

import logica.entidades.Folio;
import logica.entidades.Revision;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VORevision;
import persistencia.daos.DAOFoliosMySQL;
import persistencia.daos.DAORevisionesMySQL;
import poolConexiones.IConexion;
import poolConexiones.PoolConexiones;

public class DAORevisionesTest {

	private PoolConexiones poolConexiones;
	private String codigoFolioTemporal = "F001";

	public DAORevisionesTest() {
		try {
			poolConexiones = new PoolConexiones();
		} catch (Exception e) {
			System.out.println("Error al inicializar PoolConexiones: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		DAORevisionesTest tester = new DAORevisionesTest();

		try {
			tester.clearDatabase();

			tester.testInsback();
			tester.testLargo();
			tester.testKesimo();
			tester.testListarRevisiones();
			tester.testBorrarRevisiones();

			System.out.println("Todas las pruebas se completaron correctamente.");
		} catch (Exception e) {
			System.out.println("Error en la prueba: " + e.getMessage());
		}
	}

	private void clearDatabase() throws PersistenciaException {
		DAOFoliosMySQL daoFolios = new DAOFoliosMySQL();
		IConexion conexion = poolConexiones.obtenerConexion(true);

		// Obtener todos los folios y eliminarlos
		List<VOFolio> foliosExistentes = daoFolios.listarFolios(conexion);
		for (VOFolio voFolio : foliosExistentes) {
			daoFolios.delete(conexion, voFolio.getCodigo());
		}

		poolConexiones.liberarConexion(conexion, true);
		System.out.println("Base de datos limpiada antes de las pruebas.");
	}

	private void testInsback() throws PersistenciaException {
		// Crear un DAOFolios para insertar un folio temporal
		DAOFoliosMySQL daoFolios = new DAOFoliosMySQL();
		IConexion conexion = poolConexiones.obtenerConexion(true);

		// Crear un folio temporal
		Folio folioTemporal = new Folio(codigoFolioTemporal, "Caratula de prueba", 10);
		daoFolios.insert(conexion, folioTemporal);

		// Crear un DAORevisiones para insertar la revision
		DAORevisionesMySQL daoRevisiones = new DAORevisionesMySQL(codigoFolioTemporal);

		// Insertar una revision para el folio temporal
		Revision revision = new Revision(1, "Descripcion de prueba");
		daoRevisiones.insback(conexion, revision);

		// Verificar si la revision se ha insertado correctamente
		int largo = daoRevisiones.largo(conexion);
		if (largo > 0) {
			System.out.println("testInsback paso.");
		} else {
			throw new RuntimeException("testInsback fallo: la revision no fue insertada.");
		}

		poolConexiones.liberarConexion(conexion, true);
	}

	private void testLargo() throws PersistenciaException {
		DAORevisionesMySQL dao = new DAORevisionesMySQL(codigoFolioTemporal);
		IConexion conexion = poolConexiones.obtenerConexion(true);

		int largo = dao.largo(conexion);
		if (largo >= 0) {
			System.out.println("testLargo paso.");
		} else {
			throw new RuntimeException("testLargo fallo: no se pudo obtener el largo.");
		}

		poolConexiones.liberarConexion(conexion, true);
	}

	private void testKesimo() throws PersistenciaException {
		DAORevisionesMySQL dao = new DAORevisionesMySQL(codigoFolioTemporal);
		IConexion conexion = poolConexiones.obtenerConexion(true);

		// Insertar una revision para la prueba
		Revision revision = new Revision(2, "Descripcion de prueba");
		dao.insback(conexion, revision);

		Revision revisionEncontrada = dao.kesimo(conexion, 2);
		if (revisionEncontrada != null) {
			System.out.println("testKesimo paso.");
		} else {
			throw new RuntimeException("testKesimo fallo: la revision no fue encontrada.");
		}

		// Limpiar la base de datos despues de la prueba
		dao.borrarRevisiones(conexion);
		poolConexiones.liberarConexion(conexion, true);
	}

	private void testListarRevisiones() throws PersistenciaException {
		DAORevisionesMySQL dao = new DAORevisionesMySQL(codigoFolioTemporal);
		IConexion conexion = poolConexiones.obtenerConexion(true);

		// Insertar una revision temporal
		Revision revision = new Revision(1, "Descripcion de prueba");
		dao.insback(conexion, revision);

		List<VORevision> revisiones = dao.listarRevisiones(conexion);
		if (revisiones != null && revisiones.size() > 0) {
			System.out.println("testListarRevisiones paso.");
		} else {
			throw new RuntimeException("testListarRevisiones fallo: no se encontraron revisiones.");
		}

		// Limpiar la base de datos despues de la prueba
		dao.borrarRevisiones(conexion);
		poolConexiones.liberarConexion(conexion, true);
	}

	private void testBorrarRevisiones() throws PersistenciaException {
		DAORevisionesMySQL dao = new DAORevisionesMySQL(codigoFolioTemporal);
		IConexion conexion = poolConexiones.obtenerConexion(true);

		// Insertar una revision para luego eliminarla
		Revision revision = new Revision(1, "Descripcion de prueba");
		dao.insback(conexion, revision);

		// Borrar revisiones
		dao.borrarRevisiones(conexion);

		int largo = dao.largo(conexion);
		if (largo == 0) {
			System.out.println("testBorrarRevisiones paso.");
		} else {
			throw new RuntimeException("testBorrarRevisiones fallo: las revisiones no fueron eliminadas.");
		}

		poolConexiones.liberarConexion(conexion, true);
	}
}
