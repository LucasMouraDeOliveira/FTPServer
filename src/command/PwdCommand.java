package command;

import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class PwdCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		String path = etat.getRepository();
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_257_CHEMIN_D_ACCES_RETOURNE, 
				"\""+path+"\"");
	}

}
