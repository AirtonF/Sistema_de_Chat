package IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ReceberMgs extends Thread{
	private BufferedReader entrada;
	private  String msg ;
	public ReceberMgs(InputStream input) {
		entrada = new BufferedReader(new InputStreamReader(input));
	}

	@Override
	public void run() {
	while(true){
	
		try {
			msg = entrada.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(msg != null)
			System.out.println(msg);
		
	}
	
	}
}
