package logica.excepciones;

public class NoHayRevisionesException extends RuntimeException{
	
	public NoHayRevisionesException() {
		super();
	}

	public NoHayRevisionesException(String message) {
		super(message);
	}


}
