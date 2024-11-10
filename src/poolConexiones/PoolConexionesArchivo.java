package poolConexiones;

import java.io.Serializable;

import logica.Monitor;
import logica.excepciones.PersistenciaException;

public class PoolConexionesArchivo implements IPoolConexiones, Serializable {

	private static final long serialVersionUID = 1L;
	private Monitor m;

	public PoolConexionesArchivo() {
		m = new Monitor();
	}

	public IConexion obtenerConexion(boolean escribe) throws PersistenciaException {
		if (escribe) {
			m.comienzoEscritura();
		} else {
			m.comenzarLectura();
		}
		return new ConexionArchivo(escribe);
	}

	public void liberarConexion(IConexion conex, boolean mod) throws PersistenciaException {
		//utilizo la clase ConexionArchivo para saber si era escritura en obtener conexion.
		//no puedo utilizar mod por compatibilidad, en la fachada se utiliza para saber si es rollback en BD
		
		
		if (((ConexionArchivo)conex).isEscritura()) {
			m.terminoEscritura();
		} else {
			m.terminarLectura();
		}
	}

}
