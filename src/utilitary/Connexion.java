package utilitary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Connexion {
	
	public static void write(PrintWriter writer, String message){
		if(message == null)
			return;
		writer.write(message+"\r\n");
		writer.flush();
		System.out.println("Message envoy� : " + message);
	}
	
	public static String read(BufferedReader reader){
		try {
			String message = reader.readLine();
			System.out.println("Message re�u : " + message);
			return message;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
	}

}
