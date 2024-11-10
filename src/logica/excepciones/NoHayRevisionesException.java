package logica.excepciones;

public class NoHayRevisionesException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public NoHayRevisionesException() {
		super();
	}

	public NoHayRevisionesException(String message) {
		super(message);
	}


}
