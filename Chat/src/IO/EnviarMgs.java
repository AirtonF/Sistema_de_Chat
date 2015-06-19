package IO;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class EnviarMgs extends Thread {
	private PrintStream printStream;
	private Scanner scanner;
	private String msg;
	
	public EnviarMgs(OutputStream output) {
		printStream = new PrintStream(output);
	}
 
 @Override
public void run() {
	 scanner = new Scanner(System.in);
	 
	 while (true){
		 msg = scanner.nextLine();	
		 printStream.println(msg);
	 }
 }
 
}
