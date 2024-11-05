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

import logica.entidades.Revision;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VORevision;
import utilidades.Configuracion;

public class DAORevisionesArchivo {

    private String codFolio;

    public DAORevisionesArchivo() {
    }

    public DAORevisionesArchivo(String codF) {
        this.codFolio = codF;
    }

    public void insback(Revision rev) throws PersistenciaException {
        String rutaRevision = crearNombreArchivoRevision(rev.getNumero());
        try {ObjectOutputStream oOutput = new ObjectOutputStream(new FileOutputStream(rutaRevision));
            oOutput.writeObject(rev);
            oOutput.close();
        } catch (IOException e) {
        	e.printStackTrace();
            throw new PersistenciaException("Error insertando revisión.");
        }
    }

    public int largo() throws PersistenciaException {
        int largo = 0;
        File dir = new File(Configuracion.getInstancia().getRutaRespaldo());
        File[] files = dir.listFiles((d, name) -> name.startsWith("revision-") && name.endsWith(".txt"));

        if (files != null) {
            largo = files.length; // Contamos el número de archivos de revisiones
        }
        return largo;
    }

    public Revision kesimo(int numero) throws PersistenciaException {
        String rutaRevision = crearNombreArchivoRevision(numero);
        try (ObjectInputStream oInput = new ObjectInputStream(new FileInputStream(rutaRevision))) {
            return (Revision) oInput.readObject();
        } catch (FileNotFoundException e) {
            return null; // Si no se encuentra la revisión, retornamos null
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenciaException("Error al leer la revisión.");
        }
    }

    public List<VORevision> listarRevisiones() throws PersistenciaException {
        List<VORevision> revisiones = new ArrayList<>();
        File dir = new File(Configuracion.getInstancia().getRutaRespaldo());
        File[] files = dir.listFiles((d, name) -> name.startsWith("revision-") && name.endsWith(".txt"));

        if (files != null) {
            for (File file : files) {
                try (ObjectInputStream oInput = new ObjectInputStream(new FileInputStream(file))) {
                    Revision revision = (Revision) oInput.readObject();
                    VORevision voRevision = new VORevision();
                    voRevision.setNumero(revision.getNumero());
                    voRevision.setDescripcion(revision.getDescripcion());
                    voRevision.setCodFolio(this.codFolio);
                    revisiones.add(voRevision);
                } catch (IOException | ClassNotFoundException e) {
                    throw new PersistenciaException("Error al leer el archivo de revisiones.");
                }
            }
        }
        return revisiones;
    }

    public void borrarRevisiones() throws PersistenciaException {
        File dir = new File(Configuracion.getInstancia().getRutaRespaldo());
        File[] files = dir.listFiles((d, name) -> name.startsWith("revision-") && name.endsWith(".txt"));

        if (files != null) {
            for (File file : files) {
                if (!file.delete()) {
                    throw new PersistenciaException("Error al eliminar el archivo de revisión: " + file.getName());
                }
            }
        }
    }

    private String crearNombreArchivoRevision(int numero) {
        return Configuracion.getInstancia().getRutaRespaldo() + "/revision-" + this.codFolio + "-" + numero + ".txt";
    }
}
