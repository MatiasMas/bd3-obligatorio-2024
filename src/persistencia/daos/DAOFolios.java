import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import persistencia.consultas.Consultas;

public class DAOFolios {
	
	private String url;
	private String usr;
	private String pwd;
	
	public DAOFolios(String url, String usr, String pwd) {
        this.url = url;
        this.usr = usr;
        this.pwd = pwd;
    }
	
	public boolean member(String codigo) {
	    boolean existeFolio = false;
	    
	    try {
	        Connection con = DriverManager.getConnection(url, usr, pwd);
	        Consultas consultas = new Consultas();
	        String query = consultas.existeFolio();
	        
	        PreparedStatement pstmt1 = con.prepareStatement(query);
	        pstmt1.setString(1, codigo);
	        
	        ResultSet rs = pstmt1.executeQuery();
	        if (rs.next()) {
	            existeFolio = true;
	        }
	        
	        rs.close();
	        pstmt1.close();
	        con.close();
	    } catch (SQLException e) {
	        throw new PersistenciaException();
	    }
	    
	    return existeFolio;
	}


	public void insert(FolioVO folio) {
	    try {
	        Connection con = DriverManager.getConnection(url, usr, pwd);

	        Consultas consultas = new Consultas();
	        String query = consultas.agregarFolio();
	        PreparedStatement pstmt = con.prepareStatement(query);

	        pstmt.setString(1, folio.getCodigo());
	        pstmt.setString(2, folio.getCaratula());
	        pstmt.setString(3, folio.getPaginas());

	        pstmt.executeUpdate();

	        pstmt.close();
	        con.close();
	    } catch (SQLException e) {
	        throw new PersistenciaException();
	    }
	}
	
	
	   public FolioVO find(String codigo) {
	        FolioVO folio = null;

	        try {
	            Connection con = DriverManager.getConnection(url, usr, pwd);

	            Consultas consultas = new Consultas();
	            String query = consultas.buscarFolio(); // Método que debe devolver la consulta SQL para buscar un folio por su código
	            PreparedStatement pstmt = con.prepareStatement(query);

	            pstmt.setString(1, codigo);

	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	                folio = new FolioVO(
	                    rs.getString("codigo"),
	                    rs.getString("caratula"),
	                    rs.getString("paginas")
	                );
	            }

	            rs.close();
	            pstmt.close();
	            con.close();
	        } catch (SQLException e) {
	            throw new PersistenciaException();
	        }

	        return folio;
	    }
	   
	   
	   public List<FolioVO> listarFolios() {
	        List<FolioVO> folios = new ArrayList<>();

	        try {
	            Connection con = DriverManager.getConnection(url, usr, pwd);

	            Consultas consultas = new Consultas();
	            String query = consultas.listarFolios(); // Método que debe devolver la consulta SQL para listar todos los folios
	            PreparedStatement pstmt = con.prepareStatement(query);

	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                FolioVO folio = new FolioVO(
	                    rs.getString("codigo"),
	                    rs.getString("caratula"),
	                    rs.getString("paginas")
	                );

	                folios.add(folio);
	            }

	            rs.close();
	            pstmt.close();
	            con.close();
	        } catch (SQLException e) {
	            throw new PersistenciaException();
	        }

	        return folios;
	    }
	
	
	
}
