package persistencia.daos;

import java.io.File;
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

	public DAOFoliosArchivo() {
	}

	public boolean member(String codigo) throws PersistenciaException {
		String rutaFolio = crearNombreArchivoFolio(codigo);
		Folio folioMember = null;
		try {
			ObjectInputStream oInput = new ObjectInputStream(new FileInputStream(rutaFolio));
			folioMember = (Folio) oInput.readObject();
			oInput.close();
			return folioMember != null;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException | ClassNotFoundException e) {
			throw new PersistenciaException("Error al leer el archivo.");
		}
	}

	public void insert(Folio fol) throws PersistenciaException {
		String rutaFolio = crearNombreArchivoFolio(fol.getCodigo());
		try {
			ObjectOutputStream oOutput = new ObjectOutputStream(new FileOutputStream(rutaFolio));
			oOutput.writeObject(fol);
			oOutput.close();
		} catch (IOException e) {
			throw new PersistenciaException("Error insertando folio.");
		}
	}

	public Folio find(String cod) throws PersistenciaException {
		String rutaFolio = crearNombreArchivoFolio(cod);
		try {
			ObjectInputStream oInput = new ObjectInputStream(new FileInputStream(rutaFolio));
			Folio folio = (Folio) oInput.readObject();
			oInput.close();
			return folio;
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException | ClassNotFoundException e) {
			throw new PersistenciaException("Error al leer el archivo.");
		}
	}

	public void delete(String cod) throws PersistenciaException {
		// Leer el folio existente
		Folio folio = find(cod);
		if (folio != null) {
			// TODO: esta bien que esto se haga aca o va en logica
			// Borrar el archivo de revisiones asociado al folio
			deleteRevisionsFile(cod);

			// Eliminar el archivo del folio
			File folioFile = new File(crearNombreArchivoFolio(cod));
			if (folioFile.exists()) {
				folioFile.delete();
			}
		} else {
			throw new PersistenciaException("El folio con código " + cod + " no existe.");
		}
	}

	private void deleteRevisionsFile(String cod) throws PersistenciaException {
		File revisionesFile = new File(crearNombreArchivoRevision(cod));
		if (revisionesFile.exists()) {
			if (!revisionesFile.delete()) {
				throw new PersistenciaException("Error al eliminar el archivo de revisiones para el folio " + cod);
			}
		}
	}

	public List<VOFolio> listarFolios() throws PersistenciaException {
		List<VOFolio> folios = new ArrayList<>();
		String rutaBase = Configuracion.getInstancia().getRutaRespaldo();
		File dir = new File(rutaBase);
		// TODO: consultar si esto es valido
		// lambda que encontre en google, no vimos hasta el momento funciones lambdas,
		// no se si esta bien usar esto pero no hemos visto nada en la carrera para
		// levantar todos los archivos de un directorio
		File[] files = dir.listFiles((d, name) -> name.startsWith("folio-") && name.endsWith(".txt"));

		if (files != null) {
			for (File file : files) {
				try {
					ObjectInputStream oInput = new ObjectInputStream(new FileInputStream(file));
					Folio folio = (Folio) oInput.readObject();
					oInput.close();
					folios.add(new VOFolio(folio.getCodigo(), folio.getCaratula(), folio.getPaginas()));
				} catch (IOException | ClassNotFoundException e) {
					throw new PersistenciaException("Error al leer el archivo de folios.");
				}
			}
		}

		return folios;
	}

	public boolean esVacio() throws PersistenciaException {
		String rutaBase = Configuracion.getInstancia().getRutaRespaldo();
		File dir = new File(rutaBase);
		// TODO: misma duda que en listar folios con esta linea
		File[] files = dir.listFiles((d, name) -> name.startsWith("folio-") && name.endsWith(".txt"));
		return files == null || files.length == 0;
	}

	public VOFolioMaxRev folioMasRevisado(IConexion icon) throws PersistenciaException {
		VOFolioMaxRev voFolio = null;
		int maxRevisiones = -1;

		String rutaBase = Configuracion.getInstancia().getRutaRespaldo();
		File dir = new File(rutaBase);
		// TODO: misma duda que en listar folios con esta linea
		File[] files = dir.listFiles((d, name) -> name.startsWith("folio-") && name.endsWith(".txt"));

		if (files != null) {
			for (File file : files) {
				try {
					ObjectInputStream oInput = new ObjectInputStream(new FileInputStream(file));
					Folio folio = (Folio) oInput.readObject();
					oInput.close();
					int cantidadRevisiones = folio.cantidadRevisiones(icon);

					if (cantidadRevisiones > maxRevisiones) {
						maxRevisiones = cantidadRevisiones;
						voFolio = new VOFolioMaxRev(folio.getCodigo(), folio.getCaratula(), folio.getPaginas(),
								cantidadRevisiones);
					}
				} catch (IOException | ClassNotFoundException e) {
					throw new PersistenciaException("Error al leer el archivo.");
				}
			}
		}

		return voFolio;
	}

	private String crearNombreArchivoFolio(String codigo) {
		return Configuracion.getInstancia().getRutaRespaldo() + "/folio-" + codigo + ".txt";
	}

	private String crearNombreArchivoRevision(String codigo) {
		return Configuracion.getInstancia().getRutaRespaldo() + "/revisiones-" + codigo + ".txt";
	}
}
