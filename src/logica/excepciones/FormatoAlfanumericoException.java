package logica.excepciones;

public class FormatoAlfanumericoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FormatoAlfanumericoException() {
		super();
	}

	public FormatoAlfanumericoException(String message) {
		super(message);
	}
}