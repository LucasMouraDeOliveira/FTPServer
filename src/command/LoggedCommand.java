package command;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

public abstract class LoggedCommand implements Command {

	@Override
	public FtpReply execute(String data, UserState userState) {
		if(userState.isLogged()){
			return executeLogged(data, userState);
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_530_PAS_CONNECTE,
				"Vous n'�tes pas connect�");
	}
	
	public abstract FtpReply executeLogged(String data, UserState userState);

}
