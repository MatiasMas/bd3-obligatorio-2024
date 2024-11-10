package logica.excepciones;

public class RevisionYaExisteException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public RevisionYaExisteException() {
		super();
	}

	public RevisionYaExisteException(String message) {
		super(message);
	}
}