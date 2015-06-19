package cliente;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import IO.EnviarMgs;
import IO.ReceberMgs;


public class Cliente extends Thread{
	
	public static Socket Conexaocliente ;
	
		

	public static void main(String[] args) {
	try {
		
		Conexaocliente = new Socket("localhost",4444);
		System.out.println("CLIENTE:conectado ao servidor");
		
		new EnviarMgs(Conexaocliente.getOutputStream()).start();
		new ReceberMgs(Conexaocliente.getInputStream()).start();
		
		// Realizando Login
		
		
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
	
@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}



}


