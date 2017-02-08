package command;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

/**
 * Implémentation de la commande TYPE
 * Cette commande n'est pas prise en charge et retourne un code 502
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class TypeCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState) {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_502_COMMANDE_NON_IMPLEMENTEE, 
				"Commande non implémentée");
	}

}
