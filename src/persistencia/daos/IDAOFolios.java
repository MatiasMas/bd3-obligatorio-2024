package persistencia.daos;

import java.util.List;

import logica.entidades.Folio;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import poolConexiones.IConexion;

public interface IDAOFolios {

	public boolean member(IConexion icon, String codigo) throws PersistenciaException;

	public void insert(IConexion icon, Folio fol) throws PersistenciaException;

	public Folio find(IConexion icon, String cod) throws PersistenciaException;

	public void delete(IConexion icon, String cod) throws PersistenciaException;

	public List<VOFolio> listarFolios(IConexion icon) throws PersistenciaException;

	public boolean esVacio(IConexion icon) throws PersistenciaException;

	public VOFolioMaxRev folioMasRevisado(IConexion icon) throws PersistenciaException;
}
