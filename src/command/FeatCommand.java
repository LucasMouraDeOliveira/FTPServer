package command;

import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class FeatCommand extends LoggedCommand{

	@Override
	public String executeLogged(String data, UserState etat) {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_211_FEATURES,"Features")+ "\n"
				+ "MLST type*;size*;modify*;\n" 
				+ FtpStatusCodes.buildReply(FtpStatusCodes.CODE_211_FEATURES,"End");
		
	}


}
