package utilitary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connexion {
	
	public static void write(Socket socket, String message){
		if(message == null)
			return;
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(socket.getOutputStream());
			writer.write(message+"\r\n");
			writer.flush();
			System.out.println("Message envoyé : " + message);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static String read(Socket socket){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message = reader.readLine();
			System.out.println("Message reçu : " + message);
			return message;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
	}

}
