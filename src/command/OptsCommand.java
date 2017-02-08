package command;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

/**
 * Impl�mentation de la commande OPTS
 * La commande n'est pas prise en charge et retourne le code 202
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class OptsCommand implements Command {

	@Override
	public FtpReply execute(String data, UserState userState) {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_202_COMMANDE_NON_PRISE_EN_CHARGE, 
				"Commande non n�cessaire");
	}

}
