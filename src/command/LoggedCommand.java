package command;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

/**
 * Modèle pour les commandes connectées : ces commandes ne sont exécutées que si l'utilisateur est authentifié
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public abstract class LoggedCommand implements Command {

	@Override
	public FtpReply execute(String data, UserState userState) {
		if(userState.isLogged()){
			return executeLogged(data, userState);
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_530_PAS_CONNECTE,
				"Vous n'êtes pas connecté");
	}
	
	/**
	 * Exécute la commande à condition que l'utilisateur soit authentifié
	 * 
	 * @param data les paramètres de la commande
	 * @param userState les informations de la session utilisateur
	 * 
	 * @return la réponse renvoyée à l'utilisateur à l'issue du traitement de la commande
	 */
	public abstract FtpReply executeLogged(String data, UserState userState);

}
