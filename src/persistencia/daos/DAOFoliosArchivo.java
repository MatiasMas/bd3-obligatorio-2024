package persistencia.daos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import logica.entidades.Folio;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import poolConexiones.IConexion;
import utilidades.Configuracion;

public class DAOFoliosArchivo {
	private String url = Configuracion.getInstancia().getUrl();
	private String usr = Configuracion.getInstancia().getUser();
	private String pwd = Configuracion.getInstancia().getPassword();

	public DAOFoliosArchivo() {

	}

	public boolean member(String codigo) throws PersistenciaException {

		boolean existeFolio = false;
		String rutaArchivo = Configuracion.getInstancia().getRutaRespaldo();
		List<Folio> folios = new ArrayList<>();

		try {
			FileInputStream fInput = new FileInputStream(rutaArchivo);
			ObjectInputStream oInput = new ObjectInputStream(fInput);
			folios = (List<Folio>) oInput.readObject();
			oInput.close();
		} catch (FileNotFoundException e) {
			// Si el archivo no existe, significa que no hay folios aún, no es un error
			return false;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new PersistenciaException("Error al leer el archivo.");
		}

		// Verificar si algún folio tiene el código buscado
		for (Folio folio : folios) {
			if (folio.getCodigo().equals(codigo)) {
				existeFolio = true;
				break;
			}
		}

		return existeFolio;
	}

	public void insert(Folio fol) throws PersistenciaException {

		String rutaArchivo = Configuracion.getInstancia().getRutaRespaldo();
		List<Folio> folios = new ArrayList<>();

		try {
			FileInputStream fInput = new FileInputStream(rutaArchivo);
			ObjectInputStream oInput = new ObjectInputStream(fInput);
			folios = (List<Folio>) oInput.readObject();
			oInput.close();
		} catch (FileNotFoundException e) {
			// Si el archivo no existe, es la primera inserción, no es un error
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new PersistenciaException("Error al leer el archivo de folios.");
		}

		folios.add(fol);

		try {
			FileOutputStream fOutput = new FileOutputStream(rutaArchivo);
			ObjectOutputStream oOutput = new ObjectOutputStream(fOutput);
			oOutput.writeObject(folios);
			oOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new PersistenciaException("Error insertando folio.");
		}

	}

	public Folio find(String cod) throws PersistenciaException {
		String rutaArchivo = Configuracion.getInstancia().getRutaRespaldo();
		List<Folio> folios = new ArrayList<>();

		try {
			FileInputStream fInput = new FileInputStream(rutaArchivo);
			ObjectInputStream oInput = new ObjectInputStream(fInput);
			folios = (List<Folio>) oInput.readObject();
			oInput.close();
		} catch (FileNotFoundException e) {
			// TODO: agregar excepcion de archivo no existe
			return null;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new PersistenciaException("Error al leer el archivo.");
		}

		// Verificar si algún folio tiene el código buscado
		for (Folio folio : folios) {
			if (folio.getCodigo().equals(cod)) {
				return folio;
			}
		}
		return null;
	}

	public void delete(IConexion icon, String cod) throws PersistenciaException {

		String rutaArchivo = Configuracion.getInstancia().getRutaRespaldo();
		List<Folio> folios = new ArrayList<>();

		try {
			FileInputStream fInput = new FileInputStream(rutaArchivo);
			ObjectInputStream oInput = new ObjectInputStream(fInput);
			folios = (List<Folio>) oInput.readObject();
			oInput.close();
		} catch (FileNotFoundException e) {
			// TODO: agregar excepcion de archivo no existe
			return;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new PersistenciaException("Error al leer el archivo.");
		}

		for (Folio folio : folios) {
			if (folio.getCodigo().equals(cod)) {
				folio.borrarRevisiones(icon);
				folios.remove(folio);
				break;
			}
		}

		try {
			FileOutputStream fOutput = new FileOutputStream(rutaArchivo);
			ObjectOutputStream oOutput = new ObjectOutputStream(fOutput);
			oOutput.writeObject(folios);
			oOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new PersistenciaException("Error al escribir en el archivo.");
		}
	}

	public List<VOFolio> listarFolios() throws PersistenciaException {
		List<VOFolio> folios = new ArrayList<>();
		String rutaArchivo = Configuracion.getInstancia().getRutaRespaldo();

		try {
			FileInputStream fInput = new FileInputStream(rutaArchivo);
			ObjectInputStream oInput = new ObjectInputStream(fInput);
			List<Folio> listaFolios = (List<Folio>) oInput.readObject();
			oInput.close();
			for (Folio folio : listaFolios) {
				VOFolio voFolio = new VOFolio(folio.getCodigo(), folio.getCaratula(), folio.getPaginas());
				folios.add(voFolio);
			}
		} catch (FileNotFoundException e) {
			return folios;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new PersistenciaException("Error al leer el archivo.");
		}

		return folios;
	}

	public boolean esVacio() throws PersistenciaException {
		boolean existenFolios = false;
		String rutaArchivo = Configuracion.getInstancia().getRutaRespaldo();

		try {
			FileInputStream fInput = new FileInputStream(rutaArchivo);
			ObjectInputStream oInput = new ObjectInputStream(fInput);
			List<Folio> listaFolios = (List<Folio>) oInput.readObject();
			oInput.close();

			if (!listaFolios.isEmpty()) {
				existenFolios = true;
			}
		} catch (FileNotFoundException e) {
			return true;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new PersistenciaException("Error al leer el archivo.");
		}

		return !existenFolios;
	}

	public VOFolioMaxRev folioMasRevisado(IConexion icon) throws PersistenciaException {
		VOFolioMaxRev voFolio = null;
		int maxRevisiones = -1;
		String rutaArchivo = Configuracion.getInstancia().getRutaRespaldo();

		// Leer la lista de folios desde el archivo
		try {
			FileInputStream fInput = new FileInputStream(rutaArchivo);
			ObjectInputStream oInput = new ObjectInputStream(fInput);
			List<Folio> listaFolios = (List<Folio>) oInput.readObject();
			oInput.close();

			for (Folio folio : listaFolios) {
				int cantidadRevisiones = folio.cantidadRevisiones(icon);

				if (cantidadRevisiones > maxRevisiones) {
					maxRevisiones = cantidadRevisiones;
					voFolio = new VOFolioMaxRev(folio.getCodigo(), folio.getCaratula(), folio.getPaginas(),
							cantidadRevisiones);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO: agregar excepcion de archivo no existe
			return null;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new PersistenciaException("Error al leer el archivo.");
		}

		return voFolio;
	}
}