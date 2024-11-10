package logica.excepciones;

public class NoExistenFoliosException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public NoExistenFoliosException() {
		super();
	}

	public NoExistenFoliosException(String message) {
		super(message);
	}
}