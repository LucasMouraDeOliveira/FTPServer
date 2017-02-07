package command;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class PwdCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState) {
		String path = userState.getRepository();
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_257_CHEMIN_D_ACCES_RETOURNE, 
				"\""+path+"\"");
	}

}
