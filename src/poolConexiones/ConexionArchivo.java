package poolConexiones;

public class ConexionArchivo implements IConexion {
	
	// parametro que utilizo para saber si era una escritura, al momento de liberar
	private boolean escritura;

	public ConexionArchivo(boolean escritura) {
		this.escritura = escritura;
	}

	public boolean isEscritura() {
		return escritura;
	}

	public void setEscritura(boolean escritura) {
		this.escritura = escritura;
	}

}
