package utilidades;

public class Validador {
	
	public static boolean esAlfaNumerico(String valor) {
		// expresion regular para validar que el String solo tiene letras mayusculas,
		// minusculas, numeros o -
		return valor.matches("^[a-zA-Z0-9-]+$");
	}

	public static boolean esNumerico(String valor) {
		// valida si es solo numeros
		return valor.matches("\\d+");
	}

	public static boolean esPositivo(String valor) {
		// valida que sea numero y que no sea 0
		return valor.matches("\\d+") && !valor.equals("0");
	}

	public static boolean esAlfabetico(String valor) {
	    // Expresión regular para validar que el String solo tiene letras mayúsculas o minúsculas
	    return valor.matches("^[a-zA-Z]+$");
	}
}
