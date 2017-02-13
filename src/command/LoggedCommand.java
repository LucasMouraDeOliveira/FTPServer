package command;

import server.FtpReply;
import server.FtpServer;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

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
	public abstract FtpReply executeLogged(String data, UserState userState, FtpServer server);

}
