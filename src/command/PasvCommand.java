package command;

import server.FTPLauncher;
import utilitary.UserState;

public class PasvCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		Integer dataPort = FTPLauncher.FTP_PORT_FILE;
		Integer p1 = dataPort/256;
		Integer p2 = dataPort%256;
		//TODO find real IPv4 address
		return "227 =127,0,0,1,"+p1+","+p2;
	}

}
