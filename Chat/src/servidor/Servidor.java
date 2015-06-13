package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread{
Socket cliente;
private ServerSocket serverSocket;


public Servidor(ServerSocket serverSocket, Socket cSocket) {
	this.serverSocket = serverSocket;
	this.cliente= cSocket;
}

//"CORAÇÃO DO SISTEMA' 
public void Iniciar_conexao(){
	while (true){ 
		try { //VEREFIQUE PARA SEMPRE SE ALGUEM ESTA DE CONECTANDO NO SERVIDOR
			cliente=serverSocket.accept();
			if(cliente.isConnected()){
				System.out.println("CLIENTE CONECTADO "+cliente.getInetAddress().getHostName());
				//SE ALGUEM SE CONECTAR O TRATE EM UMA THREAD
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
		// TODO Auto-generated method stub
		super.run();
		}
	}

//O QUE SERA TRATADO NA THREAD

