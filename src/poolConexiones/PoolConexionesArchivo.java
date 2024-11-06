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

	public IConexion obtenerConexion(boolean mod) throws PersistenciaException {
		if (mod) {
			m.comienzoEscritura();
		} else {
			m.comenzarLectura();
		}
		return null;
	}

	public void liberarConexion(IConexion conex, boolean escribe) throws PersistenciaException {
		if (escribe) {
			m.terminoEscritura();
		} else {
			m.terminarLectura();
		}
	}

}
