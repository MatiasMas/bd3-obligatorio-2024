package persistencia.scripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {

	static String driver;
	static String url;
	static String user;
	static String password;

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection con = null;
		try {

			// obtengo driver, url, user, password de un archivo de configuracion
			cargarProperties();

			/* cargo el driver */
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			// borro la BD por las dudas
			borrarDB(con);
			// creo la DB
			crearDB(con);

			// creo tabla folios
			crearTablaFolios(con);

			// creo tabla revisiones
			crearTablaRevisiones(con);

			// inserto datos de prueba
			insertDatos(con);

			// cierro la conexion
			con.close();

			System.out.println("Main finalizo sin errores");

		} catch (Exception e) {
			System.out.print(e.getMessage());
			if (con != null)
				con.close();
		}
	}

	// metodo que carga las properties del archivo de configs
	private static void cargarProperties() {

		try {
			Properties p = new Properties();
			p.load(new FileInputStream("config/config.properties"));
			driver = p.getProperty("driver");
			url = p.getProperty("urlCreacionBD");
			user = p.getProperty("user");
			password = p.getProperty("password");

		} catch (IOException e) {
			System.out.println("ERROR: no se pudieron cargar las properties");
			e.printStackTrace();
		}

	}

	// metodo que elimina la base de datos si existe
	private static void borrarDB(Connection con) {

		try {
			String borrarDB = "DROP DATABASE IF EXISTS EstudioNotarial";
			Statement stm1 = con.createStatement();
			stm1.executeUpdate(borrarDB);
			stm1.close();

		} catch (Exception e) {
			System.out.println("ERROR: no se pudo borrar la base de datos");
			e.printStackTrace();
		}

	}

	// metodo que crea la base de datos si no existe
	private static void crearDB(Connection con) {

		try {
			String createDB = "CREATE DATABASE IF NOT EXISTS EstudioNotarial";
			Statement stm1 = con.createStatement();
			stm1.executeUpdate(createDB);
			stm1.close();

		} catch (Exception e) {
			System.out.println("ERROR: no se pudo crear la base de datos");
			e.printStackTrace();
		}

	}

	// metodo que crea la tabla folios si no existe
	private static void crearTablaFolios(Connection con) {

		try {
			String createFolios = "CREATE TABLE IF NOT EXISTS EstudioNotarial.Folios( codigo VARCHAR(60) PRIMARY KEY, caratula VARCHAR(60), paginas INT NOT NULL )";
			Statement stm2 = con.createStatement();
			stm2.executeUpdate(createFolios);
			stm2.close();

		} catch (Exception e) {
			System.out.println("ERROR: no se pudo crear la tabla folios");
			e.printStackTrace();
		}

	}

	// metodo que crea la tabla revisiones si no existe
	private static void crearTablaRevisiones(Connection con) {

		try {
			String createRevisiones = "CREATE TABLE IF NOT EXISTS EstudioNotarial.Revisiones (" + "numero INT, "
					+ "codFolio VARCHAR(60), " + "descripcion VARCHAR(60), " + "PRIMARY KEY (numero, codFolio), "
					+ "CONSTRAINT fk_revisiones FOREIGN KEY (codFolio) REFERENCES Folios(codigo))";

			Statement stm3 = con.createStatement();
			stm3.executeUpdate(createRevisiones);
			stm3.close();

		} catch (Exception e) {
			System.out.println("ERROR: no se pudo crear la tabla revisiones");
			e.printStackTrace();
		}

	}

	// metodo que inserta datos de prueba iniciales
	private static void insertDatos(Connection con) {

		try {

			// agrego el IGNORE a los insert para que no de error de duplicados, si ya
			// existe el folio, no lo inserta
			String insert1 = "INSERT IGNORE  INTO EstudioNotarial.Folios (codigo, caratula, paginas) VALUES ('FGH-0015', 'La comuna contra la señora que tiene 38 gatos', 5)";
			String insert2 = "INSERT IGNORE  INTO EstudioNotarial.Folios (codigo, caratula, paginas) VALUES ('JJ-202', 'Vecinos reclaman por heces de perro en el hall', 9)";
			String insert3 = "INSERT IGNORE  INTO EstudioNotarial.Folios (codigo, caratula, paginas) VALUES ('CEFJ-63', 'Vecinas rivales se arrojan macetas con frecuencia', 463)";

			Statement stm = con.createStatement();
			stm.executeUpdate(insert1);
			stm.executeUpdate(insert2);
			stm.executeUpdate(insert3);

			// agrego revisiones
			String insert4 = "INSERT IGNORE  INTO EstudioNotarial.Revisiones (numero, codFolio, descripcion) VALUES (1, 'FGH-0015', 'Sigue sumando gatos')";
			String insert5 = "INSERT IGNORE  INTO EstudioNotarial.Revisiones (numero, codFolio, descripcion) VALUES (2, 'FGH-0015', 'Es la gatera de los Simpsons')";
			String insert6 = "INSERT IGNORE  INTO EstudioNotarial.Revisiones (numero, codFolio, descripcion) VALUES (1, 'JJ-202', 'No eran de perro')";

			stm.executeUpdate(insert4);
			stm.executeUpdate(insert5);
			stm.executeUpdate(insert6);

			stm.close();

		} catch (Exception e) {
			System.out.println("ERROR: no se pudo insertar los datos");
			e.printStackTrace();
		}

	}

}
