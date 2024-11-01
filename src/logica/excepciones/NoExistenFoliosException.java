package logica.excepciones;

public class NoExistenFoliosException extends Exception {
	public NoExistenFoliosException() {
		super();
	}

	public NoExistenFoliosException(String message) {
		super(message);
	}
}