package command;

import server.FtpLongReply;
import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

/**
 * Impl�mentation de la commande FEAT
 * Renvoie au client la liste des commandes non d�finies dans la RFC 959 qui sont support�es par le serveur
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class FeatCommand extends LoggedCommand{

	@Override
	public FtpReply executeLogged(String data, UserState userState) {
		FtpLongReply reply = FtpStatusCodes.buildLongReply(FtpStatusCodes.CODE_211_FEATURES,"Features");
		reply.addLine("MLST type*;size*;modify*;");
		reply.addLine(FtpStatusCodes.buildReply(FtpStatusCodes.CODE_211_FEATURES,"End").toString());
		return reply;
	}


}
