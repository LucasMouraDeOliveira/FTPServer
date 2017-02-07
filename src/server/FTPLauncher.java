package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import utilitary.Connexion;
import utilitary.UserHandler;

public class FTPLauncher {
	
	public static int FTP_PORT_FILE = 2020;
	public static int FTP_PORT_COMMAND = 2021;
	
	protected ServerSocket serverSocket;
	
	public FTPLauncher() {
		ReadParam();
		try {
			this.serverSocket = new ServerSocket(FTP_PORT_COMMAND);
			System.out.println("Serveur ouvert sur le port " + FTP_PORT_COMMAND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void ReadParam(){
		Properties prop = new Properties();
		try {
			InputStream input = new FileInputStream("parametre");
			prop.load(input);
		} catch (IOException e) {
			System.out.println("pas de fichier parametre");
			return;
		}
		FTP_PORT_COMMAND = Integer.parseInt(prop.getProperty("FTP_PORT_COMMAND"));
		FTP_PORT_FILE = Integer.parseInt(prop.getProperty("FTP_PORT_FILE"));
		UserHandler.root = prop.getProperty("ROOT");
		System.out.println("fichier parametre charge");
	}

	public void startServer() {
		Socket socket;
		while(true){
			try {
				System.out.println("En attente de connexion");
				socket = serverSocket.accept();
				new ThreadCommand(socket).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}
	
	public static void main(String[] args) {
		new FTPLauncher().startServer();
	}

}
