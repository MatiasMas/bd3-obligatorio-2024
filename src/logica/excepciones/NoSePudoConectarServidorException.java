package logica.excepciones;

public class NoSePudoConectarServidorException extends RuntimeException{
	
	public NoSePudoConectarServidorException() {
		super();
	}

	public NoSePudoConectarServidorException(String message) {
		super(message);
	}


}
