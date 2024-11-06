package utilidades;

public class Validador {
	
	public static boolean validarAlfaNumerico(String valor) {
		// expresion regular para validar que el String solo tiene letras mayusculas,
		// minusculas o numeros
		return valor.matches("^[a-zA-Z0-9]+$");
	}

	public static boolean validarNumerico(String valor) {
		// valida si es solo numeros
		return valor.matches("\\d+");
	}

	public static boolean validarPositivo(String valor) {
		// valida que sea numero y que no sea 0
		return valor.matches("\\d+") && !valor.equals("0");
	}
	
}
	
