package logica.excepciones;

public class NoExistenFoliosException extends RuntimeException {
	public NoExistenFoliosException() {
		super();
	}

	public NoExistenFoliosException(String message) {
		super(message);
	}
}