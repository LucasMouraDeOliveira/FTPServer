package command;

import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class UserCommand extends Command {

	@Override
	public String execute(String data, UserState etat) {
		etat.setUser(data);
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_331_USER_OK_NEED_PASSWORD, 
				"Mot de passe attendu");
	}

}
