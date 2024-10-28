package logica.excepciones;

public class FolioNoExisteException extends RuntimeException {
	public FolioNoExisteException() {
		super();
	}

	public FolioNoExisteException(String message) {
		super(message);
	}
}