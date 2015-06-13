package IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class EnviarMgs extends Thread{
	private BufferedReader entrada;
	
	public EnviarMgs(InputStreamReader input) {
		entrada = new BufferedReader(input);
	}

	@Override
	public void run() {
	while(true)
		try {
			System.out.println(entrada.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
