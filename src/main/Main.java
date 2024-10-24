
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String driver, url, user, pwd;
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/";
        user = "root";
        pwd = "root";

        Class.forName(driver);
        Connection con = DriverManager.getConnection(url, user, pwd);

        String createDB = "CREATE DATABASE EstudioNotarial";
        Statement stm1 = con.createStatement();
        stm1.executeUpdate(createDB);
        stm1.close();

        String createFolios = "CREATE TABLE EstudioNotarial.Folios( codigo VARCHAR(60) PRIMARY KEY,"
                + " caratula VARCHAR(60), paginas INT NOT NULL )";
        Statement stm2 = con.createStatement();
        stm2.executeUpdate(createFolios);
        stm2.close();

        String createRevisiones = "CREATE TABLE EstudioNotarial.Revisiones ("
                + "numero INT, "
                + "codFolio VARCHAR(60), "
                + "descripcion VARCHAR(60), "
                + "PRIMARY KEY (numero, codFolio), "
                + "CONSTRAINT fk_revisiones FOREIGN KEY (codFolio) REFERENCES Folios(codigo))";

        Statement stm3 = con.createStatement();
        stm3.executeUpdate(createRevisiones);
        stm3.close();

        String insert1 = "INSERT INTO EstudioNotarial.Folios (codigo, caratula, paginas) VALUES ('FGH-0015', 'La comuna contra la señora que tiene 38 gatos', 5)";
        String insert2 = "INSERT INTO EstudioNotarial.Folios (codigo, caratula, paginas) VALUES ('BBD-1278', 'Adolescentes descontrolados hasta las 5 AM', 2)";
        String insert3 = "INSERT INTO EstudioNotarial.Folios (codigo, caratula, paginas) VALUES ('JJ-202', 'Vecinos reclaman por heces de perro en el hall', 9)";
        String insert4 = "INSERT INTO EstudioNotarial.Folios (codigo, caratula, paginas) VALUES ('CEFJ-63', 'Vecinas rivales se arrojan macetas con frecuencia', 463)";

        Statement stm = con.createStatement();
        stm.executeUpdate(insert1);
        stm.executeUpdate(insert2);
        stm.executeUpdate(insert3);
        stm.executeUpdate(insert4);
        stm.close();
        con.close();
    }

}
