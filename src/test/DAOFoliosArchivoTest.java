package test;

import java.util.List;

import logica.entidades.Folio;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import persistencia.daos.DAOFoliosArchivo;

public class DAOFoliosArchivoTest {

	private DAOFoliosArchivo daoFoliosArchivo;
	private String codigoFolioTemporal = "FGH-0015";

	public DAOFoliosArchivoTest() {
		daoFoliosArchivo = new DAOFoliosArchivo();
	}

	public static void main(String[] args) {
		DAOFoliosArchivoTest tester = new DAOFoliosArchivoTest();

		try {
			tester.testInsert();
			tester.testFind();
			tester.testListarFolios();
			tester.testDelete();
			System.out.println("Todas las pruebas se completaron correctamente.");
		} catch (Exception e) {
			System.out.println("Error en la prueba: " + e.getMessage());
		}
	}

	private void testInsert() throws PersistenciaException {
		// Crear un folio temporal
		Folio folioTemporal = new Folio(codigoFolioTemporal, "Caratula de prueba", 10);
		daoFoliosArchivo.insert(null, folioTemporal);

		// Verificar que el folio fue insertado
		if (daoFoliosArchivo.member(null, codigoFolioTemporal)) {
			System.out.println("testInsert paso.");
		} else {
			throw new RuntimeException("testInsert fallo: el folio no fue insertado.");
		}
	}

	private void testFind() throws PersistenciaException {
		Folio folioEncontrado = daoFoliosArchivo.find(null, codigoFolioTemporal);

		if (folioEncontrado != null && folioEncontrado.getCodigo().equals(codigoFolioTemporal)) {
			System.out.println("testFind paso.");
		} else {
			throw new RuntimeException("testFind fallo: el folio no fue encontrado.");
		}
	}

	private void testListarFolios() throws PersistenciaException {
		// Inserta un folio temporal para garantizar que haya al menos un folio en la
		// lista
		Folio folioTemporal = new Folio("TEMP-001", "Folio temporal", 5);
		daoFoliosArchivo.insert(null, folioTemporal);

		// Ahora intenta listar los folios
		List<VOFolio> folios = daoFoliosArchivo.listarFolios(null);

		// Verifica si se encontró al menos un folio
		if (folios != null && !folios.isEmpty()) {
			System.out.println("testListarFolios paso.");
		} else {
			throw new RuntimeException("testListarFolios fallo: no se encontraron folios.");
		}

		// Opcion para eliminar el folio temporal despues de la prueba (si es necesario)
		daoFoliosArchivo.delete(null, "TEMP-001");
	}

	private void testDelete() throws PersistenciaException {
		daoFoliosArchivo.delete(null, codigoFolioTemporal);

		if (!daoFoliosArchivo.member(null, codigoFolioTemporal)) {
			System.out.println("testDelete paso.");
		} else {
			throw new RuntimeException("testDelete fallo: el folio no fue eliminado.");
		}
	}
}
