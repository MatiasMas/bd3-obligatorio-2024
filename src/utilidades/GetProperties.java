package utilidades;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties {
	private static GetProperties instancia;
	private static Properties prop;

	private GetProperties() {
		prop = new Properties();

		InputStream in = getClass().getResourceAsStream("src/config/config.properties");

		try {
			prop.load(in);
		} catch (FileNotFoundException e) {
			System.out.print("El archivo de propiedades no fue encontrado.");
		} catch (IOException e) {
			System.out.print("No se pudo leer el archivo de propiedades.");
		}
	}

	public static GetProperties getInstancia() {
		if (instancia == null) {
			instancia = new GetProperties();
		}

		return instancia;
	}

	public String getString(String propiedad) {
		return prop.getProperty(propiedad);
	}
}