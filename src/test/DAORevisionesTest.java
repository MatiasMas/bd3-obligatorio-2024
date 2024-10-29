package test;

import java.util.List;

import logica.entidades.Folio;
import logica.entidades.Revision;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VORevision;
import persistencia.daos.DAOFolios;
import persistencia.daos.DAORevisiones;

public class DAORevisionesTest {

	public static void main(String[] args) {

		try {
			// para limpiar datos del main principal

			DAOFolios dao = new DAOFolios();
			// elimino los folios que podrian haber quedado
			List<VOFolio> folios = dao.listarFolios();
			for (VOFolio voFolio : folios) {
				dao.delete(voFolio.getCodigo());
			}

			pruebaInsback();
			pruebaLargo();
			pruebaKesimo();
			pruebaListarRevisiones();
			pruebaBorrarRevisiones();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// metodo que prueba el metodo insBack de DAORevisiones
	private static void pruebaInsback() {

		Revision revisionResult;
		Revision revision;

		// creo un folio para las revisiones
		DAOFolios daoFolios = new DAOFolios();
		Folio folio = new Folio("Folio_1", "caratula del folio 1", 10);
		daoFolios.insert(folio);

		DAORevisiones dao = new DAORevisiones(folio.getCodigo());
		boolean error = false;

		// inserto una revision
		revision = new Revision(1, "revision 1");
		dao.insback(revision);

		// busco la revision que inserte en el primer lugar
		revisionResult = dao.kesimo(1);
		if (revisionResult == null) {
			System.out.println("ERROR:No se inserto la revision");
			error = true;
		}

		// intento insertar otra revision con el mismo numero
		try {
			revision = new Revision(1, "revision 2");
			dao.insback(revision);
			System.out.println("ERROR:pude insertar dos revisiones con el mismo codigo");
			error = true;
		} catch (Exception e) {
			// si da una excepcion es correcto, no debo poder insertar dos veces una
			// revision con el mismo codigo
		}

		// inserto una segunda revision
		revision = new Revision(2, "revision 2");
		dao.insback(revision);

		if (dao.largo() != 2) {
			System.out.println("ERROR:no se insertaron ambas revisiones");
			error = true;
		}

		daoFolios.delete(folio.getCodigo());
		dao.borrarRevisiones();

		// TODO: no se si es correcto que no este, pero no se puede eliminar una
		// revision unicamente

		if (!error)
			System.out.println("Test DAORevisiones.insback OK");
	}

	// metodo que prueba el metodo largo de DAORevisiones
	private static void pruebaLargo() {

		Revision revision;

		// creo un folio para las revisiones
		DAOFolios daoFolios = new DAOFolios();
		Folio folio = new Folio("Folio_1", "caratula del folio 1", 10);
		daoFolios.insert(folio);

		DAORevisiones dao = new DAORevisiones(folio.getCodigo());
		boolean error = false;

		// verifico el largo cuando revisiones es vacio
		if (dao.largo() > 0) {
			System.out.println("ERROR:El largo es diferente de 0 con las revisiones vacias");
			error = true;
		}

		// inserto una revision
		revision = new Revision(1, "revision 1");
		dao.insback(revision);

		// verifico el largo con una revision
		if (dao.largo() != 1) {
			System.out.println("ERROR:El largo es diferente de 1");
			error = true;
		}

		// borro todas las anteriores para la siguiente prueba
		dao.borrarRevisiones();

		// verifico el largo con varias revisiones
		for (int i = 0; i < 15; i++) {
			// inserto una revision
			revision = new Revision(i, "revision " + i);
			dao.insback(revision);
		}

		if (dao.largo() != 15) {
			System.out.println("ERROR:El largo es diferente a la cantidad de revisiones insertadas");
			error = true;
		}

		daoFolios.delete(folio.getCodigo());
		dao.borrarRevisiones();

		// TODO: no se si es correcto que no este, pero no se puede eliminar una
		// revision unicamente

		if (!error)
			System.out.println("Test DAORevisiones.largo OK");
	}

	// metodo que prueba el metodo kesimo de DAORevisiones
	private static void pruebaKesimo() {
		DAOFolios daoFolios = new DAOFolios();

		Revision revision;
		Revision revResult;

		// inserto un folio
		Folio folio = new Folio("Folio_1", "caratula del folio 1", 10);
		daoFolios.insert(folio);

		DAORevisiones dao = new DAORevisiones(folio.getCodigo());
		boolean error = false;

		// inserto varias revisiones
		for (int i = 0; i < 15; i++) {
			// inserto una revision
			revision = new Revision(i, "revision " + i);
			dao.insback(revision);
		}

		// verifico si encuentra bien la primera
		revResult = dao.kesimo(0);

		if (!revResult.getDescripcion().equals("revision 0")) {
			System.out.println("ERROR:kesimo no devolvio la primera revision en la posicion 1");
			error = true;
		}

		// verifico si encuentra bien la ultima
		revResult = dao.kesimo(dao.largo() - 1);

		if (!revResult.getDescripcion().equals("revision 14")) {
			System.out.println("ERROR:kesimo no devolvio la ultima revision correctamente");
			error = true;
		}

		// verifico si no encuentra uno que no existe
		revResult = dao.kesimo(50);

		if (revResult != null) {
			System.out.println("ERROR:kesimo devolvio una revision en una posicion que no se inserto revision");
			error = true;
		}

		daoFolios.delete(folio.getCodigo());
		dao.borrarRevisiones();

		if (!error)
			System.out.println("Test DAORevisiones.Kesimo OK");
	}

	// metodo que prueba el metodo ListarRevisiones de DAORevisiones
	private static void pruebaListarRevisiones() {
		DAOFolios daoFolios = new DAOFolios();

		Revision revision;

		// inserto un folio
		Folio folio = new Folio("Folio_1", "caratula del folio 1", 10);
		daoFolios.insert(folio);

		DAORevisiones dao = new DAORevisiones(folio.getCodigo());
		boolean error = false;

		// inserto varias revisiones
		for (int i = 0; i < 15; i++) {
			// inserto una revision
			revision = new Revision(i, "revision " + i);
			dao.insback(revision);
		}

		List<VORevision> listaRevisiones = dao.listarRevisiones();

		// verifico si la lista vino en orden y todos los elementos
		for (int i = 0; i < 15; i++) {
			if (!listaRevisiones.get(i).getDescripcion().equals("revision " + i)) {
				System.out.println("ERROR:la revision de la posicion " + i + " no trajo la descripcion correcta");
				error = true;
			}
		}

		daoFolios.delete(folio.getCodigo());
		dao.borrarRevisiones();

		if (!error)
			System.out.println("Test DAORevisiones.ListarRevisiones OK");
	}

	// metodo que prueba el metodo borrarRevisiones de DAORevisiones
	private static void pruebaBorrarRevisiones() {
		DAOFolios daoFolios = new DAOFolios();

		Revision revision;

		// inserto un folio
		Folio folio = new Folio("Folio_1", "caratula del folio 1", 10);
		daoFolios.insert(folio);

		DAORevisiones dao = new DAORevisiones(folio.getCodigo());
		boolean error = false;

		// intento borrar las revisiones con la lista vacia
		dao.borrarRevisiones();

		// verifico que ya no hay revisiones
		if (dao.largo() != 0) {
			System.out.println("ERROR:No se eliminaron todas las revisiones");
			error = true;
		}

		// inserto varias revisiones
		for (int i = 0; i < 15; i++) {
			// inserto una revision
			revision = new Revision(i, "revision " + i);
			dao.insback(revision);
		}

		// borro las revisiones
		dao.borrarRevisiones();

		// verifico que ya no hay revisiones
		if (dao.largo() != 0) {
			System.out.println("ERROR:No se eliminaron todas las revisiones");
			error = true;
		}

		daoFolios.delete(folio.getCodigo());

		if (!error)
			System.out.println("Test DAORevisiones.borrarRevisiones OK");
	}
}
