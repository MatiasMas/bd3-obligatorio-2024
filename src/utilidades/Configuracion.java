package utilidades;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
//import logicaPersistencia.excepciones.PersistenciaException;

public class Configuracion {
	private static Configuracion instancia;
	private String ipServidor;
	private int puertoServidor;
	private String rutaRespaldo;
	private String url;
	private String user;
	private String pwd;

	private Configuracion() {
		Properties p = new Properties();
		String fileConfig = "config/config.properties";
		
		try {

			p.load(new FileInputStream(fileConfig));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		ipServidor = p.getProperty("ipServidor");
		puertoServidor = Integer.parseInt(p.getProperty("puertoServidor"));
		url = p.getProperty("url");
		user = p.getProperty("user");
		pwd = p.getProperty("password");
		
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
	
	public String getUrl() {
		return url;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPassword() {
		return pwd;
	}
}
