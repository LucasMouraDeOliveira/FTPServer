package command;

import java.net.Socket;

import server.FtpReply;
import utilitary.UserState;

/**
 * Interface impl�ment�e par les commandes qui ont besoin d'envoyer des informations par la socket de donn�es
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public interface DataCommandExecutor {
	
	/**
	 * Ex�cute le traitement de la commande dans un thread s�par� 
	 * (ce qui permet par exemple d'envoyer des donn�es par la socket de donn�es tout en continuant de recevoir des commandes)
	 
	 * @param data les param�tres de la commande
	 * @param userState les informations de l'utilisateur
	 * @param socket la socket de donn�es
	 */
	public void executeThread(String data, UserState userState, Socket socket);
	
	/**
	 * @return le code envoy� par la socket de donn�es au d�but de la transmission 
	 */
	public FtpReply getStartCode();
	
	/**
	 * @return le code envoy� par la socket de donn�es � la fin de la transmission
	 */
	public FtpReply getEndCode();
	
}
