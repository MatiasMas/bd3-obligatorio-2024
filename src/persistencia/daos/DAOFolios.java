import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import persistencia.consultas.Consultas;

public class DAOFolios {
	
	
	
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
	
	public RevisionVO find (int numero) {
		
	}
	public List<RevisionVO> listarFolios (){
		
	}
	
	
	
}
