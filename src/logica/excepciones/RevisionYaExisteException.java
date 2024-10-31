package logica.excepciones;

public class RevisionYaExisteException extends Exception{
	
	public RevisionYaExisteException() {
		super();
	}

	public RevisionYaExisteException(String message) {
		super(message);
	}
}