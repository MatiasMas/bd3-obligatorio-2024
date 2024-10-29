package test;

import java.util.List;

import logica.entidades.Folio;
import logica.entidades.Revision;
import logica.valueObjects.VOFolio;
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
		
		//intento insertar otra revision con el mismo numero	
		try {
			revision = new Revision(1, "revision 2");
			dao.insback(revision);
			System.out.println("ERROR:pude insertar dos revisiones con el mismo codigo");
			error = true;
		}catch (Exception e) {
			// si da una excepcion es correcto, no debo poder insertar dos veces una revision con el mismo codigo
		}
		
		//inserto una segunda revision
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
}
