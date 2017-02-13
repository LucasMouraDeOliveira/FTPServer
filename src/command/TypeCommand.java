package command;

import server.FtpReply;
import server.FtpServer;
import user.UserState;
import utility.FtpStatusCodes;

/**
 * Implémentation de la commande TYPE
 * Cette commande n'est pas prise en charge et retourne un code 200
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class TypeCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState, FtpServer server) {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES, 
				"Commande non implémentée");
	}

}
