package command;

import server.FtpLongReply;
import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class FeatCommand extends LoggedCommand{

	@Override
	public FtpReply executeLogged(String data, UserState userState) {
		FtpLongReply reply = FtpStatusCodes.buildLongReply(FtpStatusCodes.CODE_211_FEATURES,"Features");
		reply.addLine("MLST type*;size*;modify*;");
		reply.addLine(FtpStatusCodes.buildReply(FtpStatusCodes.CODE_211_FEATURES,"End").toString());
		return reply;
	}


}
