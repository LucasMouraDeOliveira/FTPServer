package command;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class TypeCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState) {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_502_COMMANDE_NON_IMPLEMENTEE, 
				"Commande non implémentée");
	}

}
