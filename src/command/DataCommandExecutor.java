package command;

import java.net.Socket;

import server.FtpReply;
import utilitary.UserState;

/**
 * Interface implémentée par les commandes qui ont besoin d'envoyer des informations par la socket de données
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public interface DataCommandExecutor {
	
	/**
	 * Exécute le traitement de la commande dans un thread séparé 
	 * (ce qui permet par exemple d'envoyer des données par la socket de données tout en continuant de recevoir des commandes)
	 
	 * @param data les paramètres de la commande
	 * @param userState les informations de l'utilisateur
	 * @param socket la socket de données
	 */
	public void executeThread(String data, UserState userState, Socket socket);
	
	/**
	 * @return le code envoyé par la socket de données au début de la transmission 
	 */
	public FtpReply getStartCode();
	
	/**
	 * @return le code envoyé par la socket de données à la fin de la transmission
	 */
	public FtpReply getEndCode();
	
}
