package command;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class UserCommand implements Command {

	@Override
	public FtpReply execute(String data, UserState userState) {
		userState.setUser(data);
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_331_USER_OK_NEED_PASSWORD, 
				"Mot de passe attendu");
	}

}
