package logica.excepciones;

public class RevisionYaExisteException extends RuntimeException{
	
	public RevisionYaExisteException() {
		super();
	}

	public RevisionYaExisteException(String message) {
		super(message);
	}
	

}
