package dao;

import java.util.List;

import logica.entidades.Revision;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VORevision;
import persistencia.daos.DAORevisionesArchivo;

public class DAORevisionesArchivoTest {

	private DAORevisionesArchivo daoRevisionesArchivo;
	private String codFolio = "F001"; // Codigo de folio para las pruebas

	public DAORevisionesArchivoTest() {
		daoRevisionesArchivo = new DAORevisionesArchivo(codFolio);
	}

	public static void main(String[] args) {
		DAORevisionesArchivoTest tester = new DAORevisionesArchivoTest();

		try {
			tester.testInsback();
			tester.testLargo();
			tester.testListarRevisiones();
			tester.testKesimo();
			tester.testBorrarRevisiones();
			System.out.println("Todas las pruebas se completaron correctamente.");
		} catch (Exception e) {
			System.out.println("Error en la prueba: " + e.getMessage());
		}
	}

	private void testInsback() throws PersistenciaException {
		Revision revision = new Revision(1, "Descripcion de la revision 1");
		daoRevisionesArchivo.insback(null, revision);

		if (daoRevisionesArchivo.kesimo(null, 1) != null) {
			System.out.println("testInsback paso.");
		} else {
			throw new RuntimeException("testInsback fallo: la revision no fue insertada.");
		}
	}

	private void testLargo() throws PersistenciaException {
		int largo = daoRevisionesArchivo.largo(null);

		if (largo >= 0) {
			System.out.println("testLargo paso.");
		} else {
			throw new RuntimeException("testLargo fallo: numero de revisiones invalido.");
		}
	}

	private void testKesimo() throws PersistenciaException {
		// Inserta una revision temporal para garantizar que haya una revision en la
		// lista
		Revision revisionTemporal = new Revision(1, "Descripcion de la revision 1");
		daoRevisionesArchivo.insback(null, revisionTemporal);

		Revision revision = daoRevisionesArchivo.kesimo(null, 1);

		if (revision != null) {
			System.out.println("testKesimo paso.");
		} else {
			throw new RuntimeException("testKesimo fallo: revision no encontrada.");
		}
	}

	private void testListarRevisiones() throws PersistenciaException {
		// Inserta una revision temporal para garantizar que haya al menos una revision
		// en la lista
		Revision revisionTemporal = new Revision(2, "Descripcion de la revision 2");
		daoRevisionesArchivo.insback(null, revisionTemporal);

		List<VORevision> revisiones = daoRevisionesArchivo.listarRevisiones(null);

		if (revisiones != null && !revisiones.isEmpty()) {
			System.out.println("testListarRevisiones paso.");
		} else {
			throw new RuntimeException("testListarRevisiones fallo: no se encontraron revisiones.");
		}

		// Opcion para eliminar la revision temporal despues de la prueba, si es
		// necesario
		daoRevisionesArchivo.borrarRevisiones(null);
	}

	private void testBorrarRevisiones() throws PersistenciaException {
		daoRevisionesArchivo.borrarRevisiones(null);

		if (daoRevisionesArchivo.largo(null) == 0) {
			System.out.println("testBorrarRevisiones paso.");
		} else {
			throw new RuntimeException("testBorrarRevisiones fallo: las revisiones no fueron eliminadas.");
		}
	}
}
