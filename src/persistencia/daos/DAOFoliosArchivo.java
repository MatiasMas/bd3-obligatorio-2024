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

public class DAOFoliosArchivo implements IDAOFolios {

    public DAOFoliosArchivo() {
    }

    // Verifico si existe un folio con el código especificado
    public boolean member(IConexion icon, String codigo) throws PersistenciaException {
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

    // Inserto un nuevo folio en un archivo
    public void insert(IConexion icon, Folio fol) throws PersistenciaException {
        String rutaFolio = crearNombreArchivoFolio(fol.getCodigo());
        try {
            ObjectOutputStream oOutput = new ObjectOutputStream(new FileOutputStream(rutaFolio));
            oOutput.writeObject(fol);
            oOutput.close();
        } catch (IOException e) {
            throw new PersistenciaException("Error insertando folio.");
        }
    }

    // Busco y retorno el folio con el código especificado
    public Folio find(IConexion icon, String cod) throws PersistenciaException {
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

    // Elimino el archivo del folio y sus revisiones asociadas
    public void delete(IConexion icon, String cod) throws PersistenciaException {
        deleteRevisionsFile(icon, cod); // Elimino el archivo de revisiones asociado a este folio
        File folioFile = new File(crearNombreArchivoFolio(cod));
        folioFile.delete(); // Elimino el archivo del folio
    }

    // Elimino el archivo de revisiones de un folio
    private void deleteRevisionsFile(IConexion icon, String cod) throws PersistenciaException {
        File revisionesFile = new File(crearNombreArchivoRevision(cod));
        if (revisionesFile.exists()) {
            revisionesFile.delete();
        } // Si no tenía revisiones asociadas no hago nada
    }

    // Listo todos los folios guardados en archivos
    public List<VOFolio> listarFolios(IConexion icon) throws PersistenciaException {
        List<VOFolio> folios = new ArrayList<>();
        String rutaBase = Configuracion.getInstancia().getRutaRespaldo();
        File dir = new File(rutaBase);
        // Obtengo todos los archivos que empiecen por "folio-" y terminen en ".txt"
        File[] files = dir.listFiles((d, name) -> name.startsWith("folio-") && name.endsWith(".txt"));

        // Convierto cada archivo en una clase folio y la cargo en la lista que voy a retornar
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

    // Verifico si no existen archivos de folios
    public boolean esVacio(IConexion icon) throws PersistenciaException {
        String rutaBase = Configuracion.getInstancia().getRutaRespaldo();
        File dir = new File(rutaBase);
        // Obtengo todos los archivos que empiecen por "folio-" y terminen en ".txt"
        File[] files = dir.listFiles((d, name) -> name.startsWith("folio-") && name.endsWith(".txt"));
        return files == null || files.length == 0;
    }

    // Retorno el folio con mayor cantidad de revisiones
    public VOFolioMaxRev folioMasRevisado(IConexion icon) throws PersistenciaException {
        VOFolioMaxRev voFolio = null;
        // Inicializo en -1 para asegurarme de que el primer folio tenga más revisiones que el valor por defecto de maxRevisiones
        int maxRevisiones = -1;

        String rutaBase = Configuracion.getInstancia().getRutaRespaldo();
        File dir = new File(rutaBase);
        // Obtengo todos los archivos que empiecen por "folio-" y terminen en ".txt"
        File[] files = dir.listFiles((d, name) -> name.startsWith("folio-") && name.endsWith(".txt"));

        // Itero sobre los archivos encontrados
        if (files != null) {
            for (File file : files) {
                try {
                    ObjectInputStream oInput = new ObjectInputStream(new FileInputStream(file));
                    Folio folio = (Folio) oInput.readObject();
                    oInput.close();
                    int cantidadRevisiones = folio.cantidadRevisiones(icon);

                    // Me voy quedando con el que tenga más revisiones
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

    // Creo el nombre de archivo para un folio
    private String crearNombreArchivoFolio(String codigo) {
        return Configuracion.getInstancia().getRutaRespaldo() + "folio-" + codigo + ".txt";
    }

    // Creo el nombre de archivo para las revisiones de un folio
    private String crearNombreArchivoRevision(String codigo) {
        return Configuracion.getInstancia().getRutaRespaldo() + "revisiones-" + codigo + ".txt";
    }
}
