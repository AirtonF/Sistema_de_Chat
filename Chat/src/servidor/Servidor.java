package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cliente.Usuario;
import clienteDao.UsuarioDao;

public class Servidor extends Thread{
	Socket cliente;
	private ServerSocket serverSocket;
	private static Map<String, Socket> ClientesOnline =  new HashMap<String, Socket>(); 
	private static Map<String, List<String>> SalaBatePapo = new HashMap<String, List<String>>();
	private PrintStream enviar;
	private BufferedReader receber;
	private String mensagem;
	private String[] separador;
	private UsuarioDao usuarioDao;
	private Usuario usuario;
	private boolean logado;

	public Servidor(ServerSocket serverSocket, Socket cSocket) {
		this.serverSocket = serverSocket;
		this.cliente= cSocket;
		this.usuarioDao = new UsuarioDao();
	}

 
	public void Iniciar_conexao(){
		while (true){ 
			try { //VEREFIQUE PARA SEMPRE SE ALGUEM ESTA DE CONECTANDO NO SERVIDOR
				cliente=serverSocket.accept();
				if(cliente.isConnected()){
					System.out.println("CLIENTE CONECTADO "+cliente.getInetAddress().getHostName());
					Servidor novocliente = new Servidor(serverSocket, this.cliente);
					novocliente.start();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
		public void run() {
		try {
		
			enviar = new PrintStream (cliente.getOutputStream()); 
			receber = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			Socket Clienedestino;
			PrintStream enviarToCliente;
		
		
			while(true){
		
			
				mensagem = receber.readLine();
				separador = mensagem.split("-");		
		
			switch (separador[0]) {
			case "login":
				
				/*
				 * Enviar Mensagem: do Cliente --> Servidor Separador[0] =
				 * Comando Separador[1] = Cliente de Destino Separador[2] =
				 * Cliente que envia a mensagem Separador[3] = mensagem em si.
				 * 
				 * Enviar Mensagem: do Servidor --> Cliente Separador[0] =
				 * Comando Separador[1] = Cliente de que enviou a mensagem antes
				 * Separador[2] = mensagem em si.
				 */	
				
				this.usuario = usuarioDao.listarUsuario(separador[1]);
				
				   if (usuario != null && usuario.getSenha().equals(separador[2])){ // se usuario != null ele existe no bando, verificica se senha é iqual

					  ClientesOnline.put(usuario.getLogin(), cliente);
					  System.out.println("Tamanho: " + ClientesOnline.size() + " Inserido: " + usuario.getLogin());
					  enviar.println("LoginRealizado");
					  this.logado = true;
					
				   }else{
					  enviar.println("ErroLogin");				
				   }
				  
				   break;

			case "cadastro":
				
				/* Realizar Cadastro: do Cliente --> Servidor
				 * Separador[0] = Comando
				 * Separador[1] = Login
				 * Separador[2] = Senha
				 * Separador[3] = Nome
				 * Separador[4] = E-mail
				 * 
				 * Realizar Cadastro: do Servidor --> Cliente
				 * Retorno = Comando
				 *  */
				
				usuario = usuarioDao.listarUsuario(separador[1]);
				
			     // se não tem ninguém com esse login o cadastro pode ser realizado
			       if(usuario == null){ 
			    	   
			    	  usuario = new Usuario();
				      usuario.setLogin(separador[1]);
				      usuario.setSenha(separador[2]);
				      usuario.setNome(separador[3]);
			 	      usuario.setEmail(separador[4]);
				
			 	      //Inserir usuario no banco;
			 	      usuarioDao.inserirUsuario(usuario);
			
				      ClientesOnline.put(usuario.getLogin(), cliente);
				      enviar.println("CadastroRealizado");
				   
			
			       }else{
				      enviar.println("ErroCadastro");				
			       }
				     break;
				     
			case "enviar":
				
				/*
				 * Enviar Mensagem: do Cliente --> Servidor 
				 * Separador[0] = Comando 
				 * Separador[1] = Cliente de Destino 
				 * Separador[2] = Cliente que envia a mensagem 
				 * Separador[3] = mensagem em si.
				 * 
				 * Enviar Mensagem: do Servidor --> Cliente Separador[0] =
				 * Comando Separador[1] = Cliente de que enviou a mensagem antes
				 * Separador[2] = mensagem em si.
				 */	
				
				if(logado){
					
					if (ClientesOnline.containsKey(separador[1]) == false){
						enviar.println("ClienteOffLine");
					
					}else {
					
						Clienedestino = ClientesOnline.get(separador[1]);  
						enviarToCliente = new PrintStream(Clienedestino.getOutputStream());
						enviarToCliente.println("Recebida-"+separador[2]+"-"+separador[3]);
						enviar.println("Mensagem Enviada!!");
					}
				}else{					   
						  enviar.println("RealizeLogin");	  
				}
						  
				break;
				
				/*Criar Nova Sala Cliente --> Servidor
				 * separador[0] = comando
				 * separador[1] = nome da sala
				 * separador[2] = Nome do Cliente
				 * */
				
				
			case "novaSala":
				if (ClientesOnline.containsKey(separador[2]) == false){
					enviar.println("RealizeLogin");
				
				}else{
					
					 if(SalaBatePapo.containsKey(separador[1]))
						 enviar.println("EssaSalaJaExiste");
				
					 else{
					
						 List<String> clientes = new ArrayList<String>();
						 clientes.add(separador[2]);
						 SalaBatePapo.put(separador[1], clientes);
						 enviar.println("SalaCriada");
					 }
				}
				
				break;
				
				/*Adiciar Usario a Sala CLiente --> Servidor
				 *separador[0] = comando
				 *separador[1] = nome da sala
				 *separador[2] = nome de quem mandou 
				 *separador[3] = nome do usuario a adicionar
				 * */
				
				case "adicionarUsuario":
				
					if (ClientesOnline.containsKey(separador[2]) == false){
						enviar.println("RealizeLogin");
						
					}else{
						
						if(!SalaBatePapo.containsKey(separador[1])){
							 enviar.println("EssaSalaNAOExiste");
					}else {
					
						if (ClientesOnline.containsKey(separador[3]) == false){
							enviar.println("UsuarioOffline");
						}else{
							
							List<String> clientes = SalaBatePapo.get(separador[1]);
							
							if (clientes.contains(separador[3])){
								enviar.println("UsuarioJaEstaNaSala");
								break;
							}
						
								clientes.add(separador[3]);
								SalaBatePapo.remove(separador[1]);
								SalaBatePapo.put(separador[1], clientes);
								enviar.println("UsuarioInserido");
						    }
						}
					
					}
						break;
				
				case "enviarParaSala":
					
					/*
					 * Enviar ParaSala: do Cliente --> Servidor 
					 * Separador[0] = Comando 
					 * Separador[1] = Nome da Sala 
					 * Separador[2] = Nome do cliente que envia Mensagem
					 * Separador[3] = Mensagem 
                    */
					
					if (SalaBatePapo.containsKey(separador[1])) {
						
						List<String> clientes = SalaBatePapo.get(separador[1]);
						
						if(clientes.contains(separador[2]) && logado){
							
							for (String n : clientes) {
								
								Clienedestino = ClientesOnline.get(n);
								enviarToCliente = new PrintStream(Clienedestino.getOutputStream());
								enviarToCliente.println("Recebida-"+separador[1]+"-"+separador[3]);
								
							}

							enviar.println("Mensagem Enviada!!");
							

						}else{
							enviar.println("UsuarioNaoEncontrado");
						}
						
					}else{
						enviar.println("SalaNaoEncontrada");
					}
					
					break;
					
				case "alterarSala":
					
					/* alterarSala  Sala CLiente --> Servidor
					 *separador[0] = comando
					 *separador[1] = nome da sala
					 *separador[2] = nome do admin da sala 
					 *separador[3] = nome da sala
					 * */
					
					if (SalaBatePapo.containsKey(separador[1])) {

						List<String> clientes = SalaBatePapo.get(separador[1]);
						
						if (clientes.contains(separador[2])) {
							
							SalaBatePapo.remove(separador[1]);
							SalaBatePapo.put(separador[3], clientes);
							enviar.println("NomeDeSalaAlterada");
						
						}else{
							enviar.println("UsuarioNaoEadmin");
						}
					}else{
						enviar.println("SalaNaoExiste");
					}
					
					break;
				
				case "deletarUsuario":
					
					/*Deletar Usario a Sala CLiente --> Servidor
					 *separador[0] = comando
					 *separador[1] = nome da sala
					 *separador[2] = nome do admin da sala 
					 *separador[3] = nome do usuario a ser deletado
					 * */
					
					if (SalaBatePapo.containsKey(separador[1]) ) {

						List<String> clientes = SalaBatePapo.get(separador[1]);
						
						if(clientes.contains(separador[2])){
												
						   if (clientes.contains(separador[3])) {
													     
							  clientes.remove(separador[3]);
						      SalaBatePapo.remove(separador[1]);
						      SalaBatePapo.put(separador[1], clientes);
						      enviar.println("UsuarioDeletado");
						    
						   }else{
							  enviar.println("UsuarioNaoExisteNaSala");
					 	   } 
						}else{
							enviar.println("UsuarioNaoEAdiministrador");
						}
						
					}else{
						enviar.println("SalaNaoExiste");
					}
					
					break;
						
			default: 
				enviar.println("ComandoDesconhecido");
				break;
			}
		
         }
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	super.run();
	}
		
	
}


