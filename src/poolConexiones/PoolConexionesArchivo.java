package poolConexiones;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import logica.excepciones.PersistenciaException;

public class PoolConexionesArchivo implements IPoolConexiones {

	private int cantLectores;
	private boolean escribiendo;

	public PoolConexionesArchivo() {

		
			//Properties p = new Properties();
			//String fileConfig = "config/config.properties";

			//p.load(new FileInputStream(fileConfig));
			

			
	
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
