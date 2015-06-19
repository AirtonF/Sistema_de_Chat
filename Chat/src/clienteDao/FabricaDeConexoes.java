package clienteDao;

import java.sql.Connection;
import java.sql.DriverManager;

public class FabricaDeConexoes {

	public Connection getConexaoJDBC(){
		
		String url = "jdbc:mysql://localhost/chat_redes";
		Connection con = null;
		
		try {
		//	Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, "root", "macilio");
			System.out.println("Conectado");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return con;
	}
}
