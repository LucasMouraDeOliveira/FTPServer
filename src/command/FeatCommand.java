package command;

import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class FeatCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_502_COMMANDE_NON_IMPLEMENTEE, 
				"Commande non implémentée");
	}

}
