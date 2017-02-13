package command;

import server.FtpReply;
import server.FtpServer;
import utilitary.UserState;

/**
 * Interface des commandes FTP.
 * Les commandes doivent implémenter une méthode execute qui réalise les traitements liés à une commande en fonction
 * des paramètres passés et de l'état actuel de la session utilisateur.
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public interface Command {
	
	/**
	 * Exécute une commande et retourne un code FTP informant de la bonne exécution (ou non) de la commande.
	 * 
	 * @param parameter les paramètres de la commande, peuvent être null
	 * @param userState la session utilisateur
	 * @param server le serveur
	 * 
	 * @return un code de retour au format texte
	 */
	public abstract FtpReply execute(String parameter, UserState userState, FtpServer server);

}
