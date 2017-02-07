package command;

import server.FTPLauncher;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class EpsvCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		String port = ""+FTPLauncher.FTP_PORT_FILE;
		etat.setDataPort(Integer.valueOf(port));
		etat.setActive(false);
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_229_MODE_PASSIF_ETENDU_ACTIVE, 
				"Passage en mode passif étendu (|||"+etat.getDataPort()+"|)");
	}

}
