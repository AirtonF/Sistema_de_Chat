package cliente;

public class Usuario {

	private Long id_usuario;
	private String login;
	private String senha;
	private String nome;
	private String email;
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	

	public Usuario(String login, String nome, String senha, String email) {
	//	super();
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.email = email;
	}



	public Long getId_usuario() {
		return id_usuario;
	}
	
	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
	    this.senha = senha;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@Override
	public String toString() {
		return "Usuario [id_usuario=" + id_usuario + ", login=" + login
				+ ", nome=" + nome + ", senha=" + senha + ", email=" + email
				+ "]";
	}
	
	
	
}
