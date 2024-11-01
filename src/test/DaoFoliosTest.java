package test;

import java.util.List;

import logica.entidades.Folio;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import persistencia.daos.DAOFolios;

public class DaoFoliosTest {

	public static void main(String[] args) {

		try {
		
			//para limpiar datos del main principal
			/*
			DAOFolios dao = new DAOFolios();
			// elimino los folios que podrian haber quedado
			List<VOFolio> folios = dao.listarFolios();
			for (VOFolio voFolio : folios) {
				dao.delete(voFolio.getCodigo());
			}
			 */
		
			
			pruebaInsert();
			pruebaDelete();
			pruebaMember();
			pruebaFind();
			pruebaLitarFolios();
			pruebaEsVacio();
			pruebaFolioMasRevisado();
			
			//TODO: cuando da persitance exception, como insertar dos veces el mismo folio
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// metodo que prueba el metodo insert de DAOFolios
	private static void pruebaInsert() throws PersistenciaException {
		DAOFolios dao = new DAOFolios();
		boolean existe;
		boolean error = false;

		// inserto un folio
		Folio folio = new Folio("Folio_1", "caratula del folio 1", 10);
		dao.insert(folio);

		// busco el folio que inserte
		existe = dao.member("Folio_1");
		if (!existe) {
			System.out.println("ERROR:No se inserto el folio");
			error = true;
		}

		// intento insertarlo nuevamente
		try {
			dao.insert(folio);
			System.out.println("ERROR: pude insertar dos veces el mismo folio");
			error = true;
		} catch (Exception e) {
			// no muestro nada, es correcto que de excepcion
		}

		// elimino el folio de prueba
		dao.delete("Folio_1");

		if(!error)
			System.out.println("Test DAOFolios.insert OK");
	}

	// metodo que prueba el metodo delete de DAOFolios
	private static void pruebaDelete() throws PersistenciaException {
		DAOFolios dao = new DAOFolios();
		boolean existe;
		boolean error = false;

		// inserto un folio
		Folio folio = new Folio("Folio_1", "caratula del folio 1", 10);
		dao.insert(folio);

		// lo elimino
		dao.delete("Folio_1");

		// busco el folio que borre
		existe = dao.member("Folio_1");
		if (existe) {
			System.out.println("ERROR:No se elimino el folio");
			error = true;
		}

		// intento eliminar uno que no existe
		try {
			dao.delete("Folio_fruta");
		} catch (Exception e) {
			// el delete si no existe no debe dar excepcion
			System.out.println("ERROR: excepcion intentando borrar folio que no existe");
			error = true;
		}

		// intento eliminar vacio
		try {
			dao.delete("");
		} catch (Exception e) {
			// el delete no debe dar excepcion si se pasa vacio
			System.out.println("ERROR: excepcion intentando borrar folio que no existe");
			error = true;
		}

		if(!error)
			System.out.println("Test DAOFolios.delete OK");
	}

	// metodo que prueba el metodo member de DAOFolios
	private static void pruebaMember() throws PersistenciaException {
		DAOFolios dao = new DAOFolios();
		boolean existe;
		boolean error = false;

		// inserto un folio
		Folio folio = new Folio("Folio_1", "caratula del folio 1", 10);
		dao.insert(folio);

		// busco el folio que inserte
		existe = dao.member("Folio_1");
		if (!existe) {
			System.out.println("ERROR:member no encontro el folio");
			error = true;
		}

		// busco un folio que no existe
		existe = dao.member("Folio_fruta");
		if (existe) {
			System.out.println("ERROR:member encontro un folio que no existe");
			error = true;
		}

		// busco por vacio
		existe = dao.member("");
		if (existe) {
			System.out.println("ERROR:member encontro un folio sin codigo");
			error = true;
		}

		// elimino el folio de prueba
		dao.delete("Folio_1");

		if(!error)
			System.out.println("Test DAOFolios.memeber OK");
	}

	// metodo que prueba el metodo find de DAOFolios
	private static void pruebaFind() throws PersistenciaException {
		DAOFolios dao = new DAOFolios();
		Folio folioResult;
		boolean error = false;

		// inserto un folio
		Folio folio = new Folio("Folio_1", "caratula del folio 1", 10);
		dao.insert(folio);

		// obtengo el folio
		folioResult = dao.find("Folio_1");
		if (!folioResult.getCodigo().equals("Folio_1")) {
			System.out.println("ERROR:No se obtuvo correctamente el codigo del folio, codigo obtenido : " + folioResult.getCodigo());
			error = true;
		} else if (!folioResult.getCaratula().equals("caratula del folio 1")) {
			System.out.println("ERROR:No se obtuvo correctamente la caratula del folio, caratula obtenida : " + folioResult.getCaratula());
			error = true;
		}else if (folioResult.getPaginas() != 10) {
			System.out.println("ERROR:No se obtuvo correctamente las paginas del folio, paginas obtenidas : " + folioResult.getPaginas());
			error = true;
		}

		//trato de obtener un folio que no existe
		folioResult = dao.find("Folio_fruta");
		if(folioResult != null) {
			System.out.println("ERROR:Folio que no existe vino con datos");
			error = true;
		}
		
		// elimino el folio de prueba
		dao.delete("Folio_1");

		//TODO:Arreglar en clase DAOFolios
		System.out.println("ERROR:Cuando se obtiene un folio no se estan obteniendo sus revisiones");
		error = true;
		
		if(!error)
			System.out.println("Test DAOFolios.find OK");
	}
	
	// metodo que prueba el metodo listarFolios de DAOFolios
	private static void pruebaLitarFolios() throws PersistenciaException {
		DAOFolios dao = new DAOFolios();
		List<VOFolio> listaFolios;
		boolean error = false;

		// inserto 3  folios
		Folio folio1 = new Folio("Folio_1", "caratula del folio 1", 10);
		Folio folio2 = new Folio("Folio_2", "caratula del folio 2", 20);
		Folio folio3 = new Folio("Folio_3", "caratula del folio 3", 30);
		dao.insert(folio1);
		dao.insert(folio2);
		dao.insert(folio3);

		// obtengo la lista de folios 
		listaFolios = dao.listarFolios();
		if (listaFolios.size() != 3) {
			System.out.println("ERROR:No se obtuvo la cantidad de folios correcta, cantidad obtenida: " + listaFolios.size());
			error = true;
		// valido sus codigos
		} else if (!listaFolios.get(0).getCodigo().equals("Folio_1")) {
			System.out.println("ERROR:No se obtuvo correctamente el codigo del folio 1 , codigo obtenida : " + listaFolios.get(0).getCodigo());
			error = true;
		} else if (!listaFolios.get(1).getCodigo().equals("Folio_2")) {
			System.out.println("ERROR:No se obtuvo correctamente el codigo del folio 2 , codigo obtenida : " + listaFolios.get(1).getCodigo());
			error = true;
		} else if (!listaFolios.get(2).getCodigo().equals("Folio_3")) {
			System.out.println("ERROR:No se obtuvo correctamente el codigo del folio 3 , codigo obtenida : " + listaFolios.get(2).getCodigo());
			error = true;
		}
		// valido sus caratulas
		else if (!listaFolios.get(0).getCaratula().equals("caratula del folio 1")) {
			System.out.println("ERROR:No se obtuvo correctamente la caratula del folio 1 , caratula obtenida : " + listaFolios.get(0).getCaratula());
			error = true;
		} else if (!listaFolios.get(1).getCaratula().equals("caratula del folio 2")) {
			System.out.println("ERROR:No se obtuvo correctamente la caratula del folio 2 , caratula obtenida : " + listaFolios.get(1).getCaratula());
			error = true;
		} else if (!listaFolios.get(2).getCaratula().equals("caratula del folio 3")) {
			System.out.println("ERROR:No se obtuvo correctamente la caratula del folio 3 , caratula obtenida : " + listaFolios.get(2).getCaratula());
			error = true;
		}
		// valido sus paginas
		else if (listaFolios.get(0).getPaginas() != 10) {
			System.out.println("ERROR:No se obtuvo correctamente las paginas del folio 1 , paginas obtenidas : " + listaFolios.get(0).getPaginas());
			error = true;
		} else if (listaFolios.get(1).getPaginas() != 20) {
			System.out.println("ERROR:No se obtuvo correctamente las paginas del folio 2 , paginas obtenidas : " + listaFolios.get(1).getPaginas());
			error = true;
		} else if (listaFolios.get(2).getPaginas() != 30) {
			System.out.println("ERROR:No se obtuvo correctamente las paginas del folio 3 , paginas obtenidas : " + listaFolios.get(2).getPaginas());
			error = true;
		}

		// elimino los folios
		dao.delete("Folio_1");
		dao.delete("Folio_2");
		dao.delete("Folio_3");

		if(!error)
			System.out.println("Test DAOFolios.listarFolios OK");
	}

	// metodo que prueba el metodo esvacio de DAOFolios
	private static void pruebaEsVacio() throws PersistenciaException {
		DAOFolios dao = new DAOFolios();
		boolean vacio;
		boolean error = false;

		// elimino los folios que podrian haber quedado
		List<VOFolio> folios = dao.listarFolios();
		for (VOFolio voFolio : folios) {
			dao.delete(voFolio.getCodigo());
		}

		vacio = dao.esVacio();

		// verifico si devuelve true cuando esta vacio
		if (!vacio) {
			System.out.println("ERROR: esVacio devolvio que no es vacio luego de borrar los folios");
			error = true;
		}

		// inserto un folio
		Folio folio = new Folio("Folio_1", "caratula del folio 1", 10);
		dao.insert(folio);

		vacio = dao.esVacio();
		// verifico si devuelve false cuando no esta vacio
		if (vacio) {
			System.out.println("ERROR: esVacio devolvio vacio luego de insertar un folio");
			error = true;
		}

		// elimino el folio de prueba
		dao.delete("Folio_1");

		if (!error)
			System.out.println("Test DAOFolios.esVacio OK");
	}

	// metodo que prueba el metodo folioMasRevisado de DAOFolios
	private static void pruebaFolioMasRevisado() throws PersistenciaException {
		DAOFolios dao = new DAOFolios();
		VOFolioMaxRev folioResult;
		boolean error = false;

		// inserto un folio
		Folio folio = new Folio("Folio_1", "caratula del folio 1", 10);
		dao.insert(folio);

		// obtengo el folio
		folioResult = dao.folioMasRevisado();

		// verifico que obtuvo el unico folio aunque no tenga revisiones
		if (folioResult == null) {
			System.out.println("ERROR: no se obtuvo el folio mas revisado cuando solo hay uno sin revisiones");
			error = true;
		}

		// inserto un segundo folio
		Folio folio2 = new Folio("Folio_2", "caratula del folio 2", 20);
		dao.insert(folio2);

		// obtengo el folio
		folioResult = dao.folioMasRevisado();

		// verifico que obtuvo un folio aunque no ninguno tenga revisiones
		if (folioResult == null) {
			System.out.println("ERROR: no se obtuvo el folio mas revisado cuando los folios estan sin revisiones");
			error = true;
		}

		/**
		 * TODO:continuar cuando este DAOrevisiones
		 * ***No puedo continuar el test, no hay forma de insertar una revision desde DAOFolios
		 * 
		 * 
		 */
		
		// elimino el folio de prueba
		dao.delete("Folio_1");
		dao.delete("Folio_2");

		if (!error)
			System.out.println("Test DAOFolios.folioMasRevisado OK");
	}

}
