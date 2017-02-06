package command;

import server.FTPLauncher;
import utilitary.UserState;

public class EpsvCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		String port = ""+FTPLauncher.FTP_PORT_FILE;
		etat.setDataPort(Integer.valueOf(port));
		etat.setActive(false);
		return "229 Entering Extended Passive Mode (|||"+etat.getDataPort()+"|)";
	}

}
