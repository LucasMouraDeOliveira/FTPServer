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
 * G�re les �changes de messages entre le serveur et un client sur la socket de donn�es.
 * Contrairement � la socket de commande, la socket de donn�es est ouverte temporairement,
 * apr�s la r�c�ption d'une commande du client n�cessitant de transmettre un flux de donn�es (envoi de fichier par exemple).
 * La socket est ferm�e apr�s l'envoi des donn�es.
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
	 * @param data les param�tres de la commande
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
		this.connectSocket();
		if(this.dataSocket.isConnected() && !this.dataSocket.isClosed()){
			dataCommandExecutor.executeThread(this.data, this.userState, this.dataSocket);
		}
		this.closeSocket();
	}
	
	/**
	 * Ouvre la socket de donn�es en mode actif : le serveur se connecte � un port sp�cifi� par l'utilisateur
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
	 * Ouvre la socket de donn�es en mode passif : le serveur attend la connexion du client sur le port choisi par le serveur
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
	 * D�marre la connexion (soit en mode passif soit en mode actif en fonction de ce qui a �t� choisi par le client)
	 * et envoi un message � l'utilisateur attestant que la connexion est active.
	 */
	public void connectSocket(){
		if(userState.isActive()){
			this.openActiveSocket();
		}else{
			this.openPassiveSocket();
		}
		Connexion.write(userState.getWriter(), dataCommandExecutor.getStartCode().toString());
	}
	
	/**
	 * Envoi un message � l'utilisateur l'informant que le traitement s'est bien d�roul� et ferme la socket de donn�es
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
