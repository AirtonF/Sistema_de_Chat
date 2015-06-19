package clienteDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cliente.Usuario;



public class UsuarioDao {

	private FabricaDeConexoes fc;
	
	public UsuarioDao() {
		// TODO Auto-generated constructor stub
  	   fc = new FabricaDeConexoes();
	}
	
	public void inserirUsuario(Usuario usuario){
		
		String sql = "insert into usuario (login, senha, nome, email) values(?,?,?,?)";
		
		Connection con = fc.getConexaoJDBC();
		
		try {
			
             PreparedStatement stm = con.prepareStatement(sql);
             stm.setString(1, usuario.getLogin());
             stm.setString(2, usuario.getSenha());
             stm.setString(3, usuario.getNome());
             stm.setString(4, usuario.getEmail());
             stm.execute();
             
             stm.close();
             con.close();
             
             System.out.println("Usuário inserido!!!");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Usuario listarUsuario(String login){
		
		String sql = "select id, login, senha, nome, email from usuario where login = ? ";
		
		Connection con = fc.getConexaoJDBC();
		Usuario user = null;
		
		try {
			
			PreparedStatement stm = con.prepareStatement(sql);
		    stm.setString(1, login);
		    ResultSet rs = stm.executeQuery();
		    
		    while(rs.next()){
		    	
		    	user = new Usuario();
		    	user.setId_usuario(rs.getLong(1));
		    	user.setLogin(rs.getString(2));
		    	user.setSenha(rs.getString(3));
		    	user.setNome(rs.getString(4));
		    	user.setEmail(rs.getString(5));
		    }
		    
		   rs.close();
		   stm.close();
		   con.close();
		   
		 /*  if (user != null) {

			   System.out.println("Usuário encontrado com Sucesso!!");
		}*/
		   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
}
