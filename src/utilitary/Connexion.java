package utilitary;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Connexion {
	
	public static void write(PrintWriter writer, String message){
		if(message == null)
			return;
		writer.write(message+"\r\n");
		writer.flush();
		System.out.println("Message envoyé : " + message);
	}
	
	public static String read(BufferedReader reader){
		try {
			String message = reader.readLine();
			System.out.println("Message reçu : " + message);
			return message;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
	}

	public static void writeBinary(DataOutputStream writer, byte[] binary) {
		if(binary == null)
			return;
		try {
			writer.write(binary);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		System.out.println("Message envoyé : " + binary.length +" octet");
	}
	
	public static byte[] readBinary(DataInputStream reader) {
		byte[] retour = new byte[4096];
		try {
			reader.read(retour);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retour;
	}

}
