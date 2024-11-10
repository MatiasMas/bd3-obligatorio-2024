package logica.excepciones;

public class RevisionNoExisteException extends Exception {
	private static final long serialVersionUID = 1L;

	public RevisionNoExisteException() {
		super();
	}

	public RevisionNoExisteException(String message) {
		super(message);
	}
}