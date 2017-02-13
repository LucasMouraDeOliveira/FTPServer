package command;

import server.FtpReply;
import server.FtpServer;
import utility.UserState;

/**
 * Interface des commandes FTP.
 * Les commandes doivent impl�menter une m�thode execute qui r�alise les traitements li�s � une commande en fonction
 * des param�tres pass�s et de l'�tat actuel de la session utilisateur.
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public interface Command {
	
	/**
	 * Ex�cute une commande et retourne un code FTP informant de la bonne ex�cution (ou non) de la commande.
	 * 
	 * @param parameter les param�tres de la commande, peuvent �tre null
	 * @param userState la session utilisateur
	 * @param server le serveur
	 * 
	 * @return un code de retour sous la forme d'une {@link FtpReply}.
	 */
	public abstract FtpReply execute(String parameter, UserState userState, FtpServer server);

}
