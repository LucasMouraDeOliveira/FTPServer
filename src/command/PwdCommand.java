package command;

import server.FtpReply;
import server.FtpServer;
import utility.FtpStatusCodes;
import utility.UserState;

/**
 * Impl�mentation de la commande PWD et de son alias XPWD
 * Retourne le dossier courant de l'utilisateur sur le serveur
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class PwdCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState, FtpServer server) {
		String path = userState.getRepository();
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_257_CHEMIN_D_ACCES_RETOURNE, 
				"\""+path+"\"");
	}

}
