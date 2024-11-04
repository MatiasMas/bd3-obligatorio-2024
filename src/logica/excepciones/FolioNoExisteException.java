package logica.excepciones;

public class FolioNoExisteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FolioNoExisteException() {
		super();
	}

	public FolioNoExisteException(String message) {
		super(message);
	}
}