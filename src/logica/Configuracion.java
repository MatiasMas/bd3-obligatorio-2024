package logica;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
//import logicaPersistencia.excepciones.PersistenciaException;

public class Configuracion {
	private static Configuracion instancia;
	private String ipServidor;
	private int puertoServidor;
	private String rutaRespaldo;

	private Configuracion() {
		Properties p = new Properties();
		String fileConfig = "src/config.properties";
		try {

			p.load(new FileInputStream(fileConfig));
		} catch (IOException e) {
//			throw new PersistenciaException(e.getMessage());
		}
		ipServidor = p.getProperty("ipServidor");
		puertoServidor = Integer.parseInt(p.getProperty("puertoServidor"));
		// rutaRespaldo = p.getProperty("rutaRespaldo");
	}

	public static Configuracion getInstancia() {
		if (instancia == null)
			instancia = new Configuracion();
		return instancia;
	}

	public String getIpServidor() {
		return ipServidor;
	}

	public int getPuertoServidor() {
		return puertoServidor;
	}

	public String getRutaRespaldo() {
		return rutaRespaldo;
	}
}
