package command;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

/**
 * Mod�le pour les commandes connect�es : ces commandes ne sont ex�cut�es que si l'utilisateur est authentifi�
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
				"Vous n'�tes pas connect�");
	}
	
	/**
	 * Ex�cute la commande � condition que l'utilisateur soit authentifi�
	 * 
	 * @param data les param�tres de la commande
	 * @param userState les informations de la session utilisateur
	 * 
	 * @return la r�ponse renvoy�e � l'utilisateur � l'issue du traitement de la commande
	 */
	public abstract FtpReply executeLogged(String data, UserState userState);

}
