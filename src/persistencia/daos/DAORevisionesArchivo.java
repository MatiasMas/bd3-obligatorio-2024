package persistencia.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import logica.entidades.Revision;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VORevision;
import poolConexiones.IConexion;
import utilidades.Configuracion;

public class DAORevisionesArchivo implements IDAORevisiones, Serializable {
	

	private static final long serialVersionUID = -4359856492938099682L;
	private String codFolio;

	public DAORevisionesArchivo() {
		super();
	}

	public DAORevisionesArchivo(String codF) {
		this.codFolio = codF;
	}

	public void insback(IConexion icon, Revision rev) throws PersistenciaException {
		String rutaRevision = crearNombreArchivoRevision();
		List<Revision> revisiones = leerRevisionesDesdeArchivo(rutaRevision);

		revisiones.add(rev); // Añadir la nueva revisión a la lista existente

		ObjectOutputStream oOutput = null;
		try {
			oOutput = new ObjectOutputStream(new FileOutputStream(rutaRevision));
			oOutput.writeObject(revisiones); // Guardar la lista completa en el archivo
		} catch (IOException e) {
			throw new PersistenciaException("Error insertando revisión.");
		} finally {
			if (oOutput != null) {
				try {
					oOutput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public int largo(IConexion icon) throws PersistenciaException {
		String rutaRevision = crearNombreArchivoRevision();
		List<Revision> revisiones = leerRevisionesDesdeArchivo(rutaRevision);
		return revisiones.size();
	}

	public Revision kesimo(IConexion icon, int numero) throws PersistenciaException {
		String rutaRevision = crearNombreArchivoRevision();
		List<Revision> revisiones = leerRevisionesDesdeArchivo(rutaRevision);

		for (Revision rev : revisiones) {
			if (rev.getNumero() == numero) {
				return rev;
			}
		}
		return null; // Si no se encuentra la revisión, retornamos null
	}

	public List<VORevision> listarRevisiones(IConexion icon) throws PersistenciaException {
		List<VORevision> listaVORevisiones = new ArrayList<>();
		String rutaRevision = crearNombreArchivoRevision();
		List<Revision> revisiones = leerRevisionesDesdeArchivo(rutaRevision);

		for (Revision rev : revisiones) {
			VORevision voRevision = new VORevision();
			voRevision.setNumero(rev.getNumero());
			voRevision.setDescripcion(rev.getDescripcion());
			voRevision.setCodFolio(this.codFolio);
			listaVORevisiones.add(voRevision);
		}
		return listaVORevisiones;
	}

	public void borrarRevisiones(IConexion icon) throws PersistenciaException {
		String rutaRevision = crearNombreArchivoRevision();
		File archivoRevisiones = new File(rutaRevision);
		if (!archivoRevisiones.delete()) {
			throw new PersistenciaException("Error al eliminar el archivo de revisiones.");
		}
	}

	private String crearNombreArchivoRevision() {
		return Configuracion.getInstancia().getRutaRespaldo() + "/revision-" + this.codFolio + ".txt";
	}

	@SuppressWarnings("unchecked")
	private List<Revision> leerRevisionesDesdeArchivo(String rutaRevision) throws PersistenciaException {
		File archivo = new File(rutaRevision);
		if (!archivo.exists()) {
			return new ArrayList<>(); // Si el archivo no existe, retornamos una lista vacía
		}

		ObjectInputStream oInput = null;
		try {
			oInput = new ObjectInputStream(new FileInputStream(rutaRevision));
			return (List<Revision>) oInput.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new PersistenciaException("Error al leer el archivo de revisiones.");
		} finally {
			if (oInput != null) {
				try {
					oInput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
