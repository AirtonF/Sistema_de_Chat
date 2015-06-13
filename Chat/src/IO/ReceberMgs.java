package IO;

import java.io.InputStream;
import java.util.Scanner;

public class ReceberMgs extends Thread {

	private InputStream input;
	private Scanner scanner;


public ReceberMgs(InputStream input) {
	this.input = input;
	
}
 
 @Override
public void run() {
	 System.out.println("EXECUTANDO RECEBEDOR");
	 scanner = new Scanner(input);
	 while(scanner.hasNextLine()){
		 
		 System.out.println(scanner.nextLine());;
		 
	 }
 }
 
}
