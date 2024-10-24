package logicaPersistencia.poolConexiones;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import logicaPersistencia.excepciones.PersistenciaException;

public class PoolConexiones extends UnicastRemoteObject implements IPoolConexiones {

	private static final long serialVersionUID = 1L;

	private String driver, url, user, password;
	private int nivelTransaccional;
	private IConexion conexiones[];
	private int tamanio, creadas, tope;

	public PoolConexiones() throws RemoteException, PersistenciaException, ClassNotFoundException {

		try {
			Properties p = new Properties();
			String fileConfig = "src/config/config.properties";

			p.load(new FileInputStream(fileConfig));
			driver = p.getProperty("driver");
			url = p.getProperty("url");
			user = p.getProperty("usuario");
			password = "";
			nivelTransaccional = Integer.parseInt(p.getProperty("nivelTransaccional"));
			tamanio = Integer.parseInt(p.getProperty("tamanioPool"));
			creadas = 0;
			tope = 0;

			Class.forName(driver);
			conexiones = new IConexion[tamanio];

		} catch (FileNotFoundException e) {
			System.out.println("Error, El archivo no existe!");
		} catch (IOException e) {
			System.out.println("Error, No se puede leer el archivo!");
		}
	}

	public IConexion obtenerConexion(boolean mod) throws PersistenciaException {
		IConexion conex = null;
		while (conex == null) {
			if (tope > 0) {
				conex = conexiones[tope - 1];
				tope--;
			} else if (creadas < tamanio) {
				try {
					Connection conect = DriverManager.getConnection(url, user, password);
					conect.setTransactionIsolation(nivelTransaccional);
					if (mod)
						conect.setAutoCommit(false);
					conex = new Conexion(conect);
					creadas++;
				} catch (SQLException e) {
					throw new PersistenciaException("Error de conexion de obtenerConexion");
				}
			} else {
				try {
					wait();
				} catch (InterruptedException e) {
					throw new PersistenciaException("Error de sincronizacion");
				}
			}
		}
		return conex;
	}

	public synchronized void liberarConexion(IConexion conex, boolean res) throws PersistenciaException {
		if (conex != null) {

			Connection con;
			con = ((Conexion) conex).getCon();
			try {
				if (res)
					con.commit();
				else
					con.rollback();
			} catch (SQLException e) {
				throw new PersistenciaException("Error al cerrar transaccion");
			}

			tope++;
			conexiones[tope - 1] = conex;
			notify();
		} else
			throw new PersistenciaException("Conexion nulla");
	}

}
