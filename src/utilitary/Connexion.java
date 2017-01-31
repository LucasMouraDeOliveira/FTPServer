package utilitary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connexion {
	
	public static void write(Socket socket, String message){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(socket.getOutputStream());
			writer.write(message+"\r\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static String read(Socket socket){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message = reader.readLine();
			return message;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
	}

}
