package logica.excepciones;

public class NoSePudoConectarServidorException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public NoSePudoConectarServidorException() {
		super();
	}

	public NoSePudoConectarServidorException(String message) {
		super(message);
	}


}
