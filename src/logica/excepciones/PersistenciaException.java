package logica.excepciones;

public class PersistenciaException extends Exception {

	private static final long serialVersionUID = 1L;

	public PersistenciaException() {
		super();
	}

	public PersistenciaException(String message) {
		super(message);
	}
	
}
