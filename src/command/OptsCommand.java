package command;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class OptsCommand implements Command {

	@Override
	public FtpReply execute(String data, UserState userState) {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_202_COMMANDE_NON_PRISE_EN_CHARGE, 
				"Commande non nécessaire");
	}

}
