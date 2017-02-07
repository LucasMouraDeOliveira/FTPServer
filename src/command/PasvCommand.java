package command;

import java.net.Inet6Address;
import java.net.InetAddress;

import server.FTPLauncher;
import utilitary.UserState;

public class PasvCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		Integer dataPort = FTPLauncher.FTP_PORT_FILE;
		Integer p1 = dataPort/256;
		Integer p2 = dataPort%256;
		
		InetAddress address = etat.getDataAddress();
		String addressToString = "";
		if(address instanceof Inet6Address){
			return "500 Address is IPv6, you should use EPSV command instead";
		}else{
			addressToString = address.getHostAddress().replaceAll("\\.", ",");
		}
		return "227 ="+addressToString+","+p1+","+p2;
	}

}
