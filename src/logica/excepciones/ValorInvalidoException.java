package logica.excepciones;

public class ValorInvalidoException extends Exception {

	private static final long serialVersionUID = 1L;

	public ValorInvalidoException() {
		super();
	}

	public ValorInvalidoException(String message) {
		super(message);
	}
}