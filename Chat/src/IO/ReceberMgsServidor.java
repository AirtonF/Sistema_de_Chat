package IO;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ReceberMgsServidor extends Thread{


	private BufferedReader entrada;
	private  String msg ;
	public ReceberMgsServidor(InputStream input) {
		entrada = new BufferedReader(new InputStreamReader(input));
	}

	@Override
	public void run() {
	String[] refat;
		while(true){
	
		try {
			msg = entrada.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		refat = msg.split("-");		
		
		}
	
	}

	
	
}
