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

	// Inicializo el DAO de revisiones con el código de folio especificado
	public DAORevisionesArchivo(String codF) {
		this.codFolio = codF;
	}

	// Inserto una nueva revisión al final de la lista de revisiones en el archivo
	public void insback(IConexion icon, Revision rev) throws PersistenciaException {
		String rutaRevision = crearNombreArchivoRevision();
		//obtengo la lista de clases revisiones del archivo
		List<Revision> revisiones = leerRevisionesDesdeArchivo(rutaRevision);

		// Añado la nueva revisión a la lista existente
		revisiones.add(rev);

		ObjectOutputStream oOutput = null;
		try {
			oOutput = new ObjectOutputStream(new FileOutputStream(rutaRevision));
			// Guardo la lista completa de revisiones en el archivo
			oOutput.writeObject(revisiones);
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

	// Devuelvo el número total de revisiones en el archivo
	public int largo(IConexion icon) throws PersistenciaException {
		String rutaRevision = crearNombreArchivoRevision();
		List<Revision> revisiones = leerRevisionesDesdeArchivo(rutaRevision);
		return revisiones.size();
	}

	// Busco y retorno la revisión en la posición especificada
	public Revision kesimo(IConexion icon, int numero) throws PersistenciaException {
		String rutaRevision = crearNombreArchivoRevision();
		List<Revision> revisiones = leerRevisionesDesdeArchivo(rutaRevision);

		// Recorro la lista de revisiones buscando la revisión con el número especificado
		for (Revision rev : revisiones) {
			if (rev.getNumero() == numero) {
				return rev;
			}
		}
		return null; // Si no encuentro la revisión, retorno null
	}

	// Listo todas las revisiones del archivo y las retorno como una lista de VORevision
	public List<VORevision> listarRevisiones(IConexion icon) throws PersistenciaException {
		List<VORevision> listaVORevisiones = new ArrayList<>();
		String rutaRevision = crearNombreArchivoRevision();
		List<Revision> revisiones = leerRevisionesDesdeArchivo(rutaRevision);

		// Convierto cada revisión en un objeto VORevision y lo añado a la lista
		for (Revision rev : revisiones) {
			VORevision voRevision = new VORevision();
			voRevision.setNumero(rev.getNumero());
			voRevision.setDescripcion(rev.getDescripcion());
			voRevision.setCodFolio(this.codFolio);
			listaVORevisiones.add(voRevision);
		}
		return listaVORevisiones;
	}

	// Elimino el archivo que contiene las revisiones de este folio
	public void borrarRevisiones(IConexion icon) throws PersistenciaException {
		String rutaRevision = crearNombreArchivoRevision();
		File archivoRevisiones = new File(rutaRevision);
		if (!archivoRevisiones.delete()) {
			throw new PersistenciaException("Error al eliminar el archivo de revisiones.");
		}
	}

	// Creo el nombre del archivo para las revisiones de este folio
	private String crearNombreArchivoRevision() {
		return Configuracion.getInstancia().getRutaRespaldo() + "/revision-" + this.codFolio + ".txt";
	}

	// Leo las revisiones desde el archivo y retorno una lista de ellas
	@SuppressWarnings("unchecked")
	private List<Revision> leerRevisionesDesdeArchivo(String rutaRevision) throws PersistenciaException {
		File archivo = new File(rutaRevision);
		List<Revision> revisiones = new ArrayList<Revision>();
		if (!archivo.exists()) {
			// Si el archivo no existe, retorno una lista vacía
			return revisiones;
		}

		ObjectInputStream oInput = null;
		try {
			oInput = new ObjectInputStream(new FileInputStream(rutaRevision));
			revisiones = (List<Revision>) oInput.readObject();
			oInput.close();
			return revisiones;
		} catch (IOException | ClassNotFoundException e) {
			throw new PersistenciaException("Error al leer el archivo de revisiones.");
		} 
	}
}
