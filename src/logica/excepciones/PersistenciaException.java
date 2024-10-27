package logica.excepciones;

public class PersistenciaException extends RuntimeException{

	public PersistenciaException() {
		super();
	}

	public PersistenciaException(String message) {
		super(message);
	}
	
}
