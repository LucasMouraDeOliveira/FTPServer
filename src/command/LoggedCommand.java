package command;

import server.FtpReply;
import server.FtpServer;
import utility.FtpStatusCodes;
import utility.UserState;

/**
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public abstract class LoggedCommand implements Command {

	@Override
	public FtpReply execute(String parameters, UserState userState, FtpServer server) {
		if(userState.isLogged()){
			return executeLogged(parameters, userState, server);
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_530_PAS_CONNECTE,
				"Vous n'êtes pas connecté");
	}
	
	/**
	 * Exécute la commande à condition que l'utilisateur soit authentifié
	 * 
	 * @param data les paramètres de la commande
	 * @param userState les informations de la session utilisateur
	 * @param server le serveur
	 * 
	 * @return la réponse renvoyée à l'utilisateur à l'issue du traitement de la commande
	 */
	public abstract FtpReply executeLogged(String data, UserState userState, FtpServer server);

}
