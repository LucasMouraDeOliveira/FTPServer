package command;

import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class OptsCommand extends Command {

	@Override
	public String execute(String data, UserState etat) {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_202_COMMANDE_NON_PRISE_EN_CHARGE, 
				"Commande non nécessaire");
	}

}
