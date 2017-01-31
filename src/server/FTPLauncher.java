package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import utilitary.Connexion;

public class FTPLauncher {
	
	public final static int FTP_PORT_FILE = 2020;
	public final static int FTP_PORT_COMMAND = 10000;
	
	protected ServerSocket serverSocket;
	
	public FTPLauncher() {
		try {
			this.serverSocket = new ServerSocket(FTP_PORT_COMMAND);
			System.out.println("Serveur ouvert sur le port " + FTP_PORT_COMMAND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startServer() {
		Socket socket;
		while(true){
			try {
				System.out.println("En attente de connexion");
				socket = serverSocket.accept();
				Connexion.write(socket, "220 - bienvenue sur le serveur FTP");
				System.out.println("Utilisateur connecté sur le port :" + socket.getPort());
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
