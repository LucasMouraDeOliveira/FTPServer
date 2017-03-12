package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import command.DataCommandExecutor;
import user.UserState;
import utility.Connexion;

/**
 * Gère les échanges de messages entre le serveur et un client sur la socket de données.
 * Contrairement à la socket de commande, la socket de données est ouverte temporairement,
 * après la récéption d'une commande du client nécessitant de transmettre un flux de données (envoi de fichier par exemple).
 * La socket est fermée après l'envoi des données.
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class ThreadData extends Thread{
	
	protected String data;
	
	protected UserState userState;
	
	protected Socket dataSocket;
	
	protected ServerSocket serverSocket;

	private DataCommandExecutor dataCommandExecutor;
	
	/**
	 * Initialise le thread
	 * 
	 * @param data les paramètres de la commande
	 * @param userState les informations de la session utilisateur
	 * @param dataCommandExecutor la commande 
	 */
	public ThreadData(String data, UserState userState, DataCommandExecutor dataCommandExecutor){
		this.data = data;
		this.userState = userState;
		this.dataCommandExecutor  = dataCommandExecutor;
	}
	
	@Override
	public void run(){
		System.out.println("Starting to connect");
		this.connectSocket();
		System.out.println("Connected");
		if(this.dataSocket.isConnected() && !this.dataSocket.isClosed()){
			dataCommandExecutor.executeThread(this.data, this.userState, this.dataSocket);
		}
		this.closeSocket();
	}
	
	/**
	 * Ouvre la socket de données en mode actif : le serveur se connecte à un port spécifié par l'utilisateur
	 */
	public void openActiveSocket(){
		this.dataSocket = new Socket();
		try {
			this.dataSocket.connect(new InetSocketAddress(userState.getDataAddress(), userState.getDataPort()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ouvre la socket de données en mode passif : le serveur attend la connexion du client sur le port choisi par le serveur
	 */
	public void openPassiveSocket() {
		try {
			this.serverSocket = new ServerSocket(userState.getDataPort());
			this.dataSocket = serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Envoie un message à l'utilisateur attestant que la connexion est active et
	 * démarre la connexion (soit en mode passif soit en mode actif en fonction de ce qui a été choisi par le client)
	 */
	public void connectSocket(){
		Connexion.write(userState.getWriter(), dataCommandExecutor.getStartCode().toString());
		if(userState.isActive()){
			this.openActiveSocket();
		}else{
			this.openPassiveSocket();
		}
	}
	
	/**
	 * Envoi un message à l'utilisateur l'informant que le traitement s'est bien déroulé et ferme la socket de données
	 */
	public void closeSocket() {
		Connexion.write(userState.getWriter(), dataCommandExecutor.getEndCode().toString());
		if(this.serverSocket != null){
			try {
				this.serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(this.dataSocket != null){
			try {
				this.dataSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
