package grafica.controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import grafica.ventanas.FolioMasRevisado;
import logica.IFachada;
import logica.excepciones.NoExistenFoliosException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolioMaxRev;

public class ControladorFolioMasVisitado {
	
	private FolioMasRevisado fmr;

	public ControladorFolioMasVisitado(FolioMasRevisado ventana) {
		this.fmr = ventana;
	}

	public VOFolioMaxRev getFolioMasRev()  throws Exception {
		
		Properties p = new Properties();
		String nomArch = "config/cliente.properties";
		try {
			p.load(new FileInputStream(nomArch));
		} catch (IOException e1) {
			throw new PersistenciaException("Error en leer archivo de configuracion");
		}

		String path = p.getProperty("fachada");
		
		
		try {
			IFachada fachada = (IFachada) Naming.lookup(path);
			return fachada.folioMasRevisado();
			 
		} catch (Exception e) {
			throw new PersistenciaException("Error en fachada:" + e);
		}
		
	} 
	
	
}
