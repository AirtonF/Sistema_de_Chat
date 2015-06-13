package cliente;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import IO.EnviarMgs;
import IO.ReceberMgs;


public class cliente {
private static Socket cliente;
public static void main(String[] args) {
	try {
		
		cliente = new Socket("127.0.0.1",4444);
		System.out.println("CLIENTE:conectado ao servidor");
		
		ReceberMgs rm = new ReceberMgs(cliente.getInputStream());
		rm.run();
		 
		 EnviarMgs em = new EnviarMgs(cliente.getOutputStream());
			em.run();
			
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
	   
}


