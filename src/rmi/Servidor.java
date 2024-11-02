package rmi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.sql.SQLException;

import logica.Fachada;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import utilidades.Configuracion;

public class Servidor {

	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, InstantiationException,
			IllegalAccessException, IOException, SQLException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {

		try {
			// Obtengo la configuracion
			Configuracion fileConfig = Configuracion.getInstancia();

			// Armo la ruta al objeto remoto
			String ubicacionServidor = "//" + fileConfig.getIpServidor() + ":" + fileConfig.getPuertoServidor()
					+ "/fachada";

			// Creo la instancia del objeto remoto
			// Fachada fachada = new Fachada();
			IFachada fachada;
			fachada = Fachada.getInstancia();

			// Publico el objeto remoto
			LocateRegistry.createRegistry(fileConfig.getPuertoServidor());
			Naming.rebind(ubicacionServidor, (Remote) fachada);

			System.out.println("Fachada publicada correctamente");

		} catch (RemoteException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (MalformedURLException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (PersistenciaException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

}
