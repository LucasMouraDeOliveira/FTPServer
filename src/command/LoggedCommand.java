package command;

import utilitary.FtpStatusCodes;
import utilitary.UserState;

public abstract class LoggedCommand extends Command {

	@Override
	public String execute(String data, UserState etat) {
		if(etat.isLogged()){
			return executeLogged(data, etat);
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_530_PAS_CONNECTE,
				"Vous n'êtes pas connecté");
	}
	
	public abstract String executeLogged(String data, UserState etat);

}
