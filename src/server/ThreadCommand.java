package server;

import java.io.IOException;
import java.net.Socket;

import command.CommandPool;
import user.UserState;
import utility.Connexion;

/**
 * G�re les �changes de messages entre le serveur et un client sur le port de commande
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class ThreadCommand extends Thread {
	
	protected CommandPool commandPool;
	
	protected FtpServer server;
	
	protected Socket socket;
	
	protected UserState state;
	
	protected boolean socketOpen;
	
	/**
	 * Lance un thread s�par� pour recevoir et envoyer des messages � un client par le biais de la socket de commande
	 * 
	 * @param server le serveur
	 * @param socket la socket client, connect�e sur le port de commande
	 * @param commandPool la pool de commandes reconnues par l'application
	 */
	public ThreadCommand(FtpServer server, Socket socket, CommandPool commandPool) {
		this.server = server;
		this.socket = socket;
		this.commandPool = commandPool;
		this.state = new UserState(socket);
		this.socketOpen = true;
	}
	
	@Override
	public void run() {
		Connexion.write(state.getWriter(), "220 - bienvenue sur le serveur FTP");
		while(isSocketOpen()){
			String message = receiveCommand();
			//La socket a surement �t� ferm�e par le client, on arr�te le thread (proprement)
			if(message == null){
				this.socketOpen = false;
				continue;
			}
			String[] data = message.split(" ", 2);
			FtpReply reply;
			if(data[0].equals("QUIT")){
				this.socketOpen = false;
				continue;
			}
			if(data.length == 1){
				reply = interpreteCommand(data[0], null);
			} else {
				reply = interpreteCommand(data[0], data[1]);
			}
			if(!reply.isEmpty()){
				Connexion.write(state.getWriter(), reply.toString());
			}
		}
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Lit une ligne depuis la socket client
	 * 
	 * @return une ligne (commande) envoy�e par la socket client
	 */
	public String receiveCommand(){
		return Connexion.read(state.getReader());
	}
	
	/**
	 * Interpr�te une commande envoy�e par l'utilisateur et retourne une r�ponse FTP
	 * 
	 * @param command la commande utilisateur
	 * @param data les param�tres de la commande
	 * 
	 * @return la r�ponse � la commande
 	 */
	public FtpReply interpreteCommand(String command, String data){
		return commandPool.handle(command, data, this.state, this.server);
	}
	
	/**
	 * 
	 * @return vrai si la socket client est ouverte
	 */
	public boolean isSocketOpen() {
		return socketOpen;
	}

}
