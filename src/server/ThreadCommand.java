package server;

import java.io.IOException;
import java.net.Socket;

import command.CommandPool;
import utilitary.Connexion;
import utilitary.UserState;

/**
 * G�re les �changes de messages entre le serveur et un client sur le port de commande
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class ThreadCommand extends Thread {
	
	protected Socket socket;
	protected UserState state;
	
	protected boolean socketOpen;
	
	/**
	 * Initialise les informations du clients
	 * 
	 * @param socket la socket client
	 */
	public ThreadCommand(Socket socket) {
		this.socket = socket;
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
		return CommandPool.getInstance().handle(command, data, this.state);
	}
	
	/**
	 * 
	 * @return vrai si la socket client est ouverte
	 */
	public boolean isSocketOpen() {
		return socketOpen;
	}

}
