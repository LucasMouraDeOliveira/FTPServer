package command;

import java.net.Inet6Address;
import java.net.InetAddress;

import server.FTPLauncher;
import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class PasvCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState) {
		Integer dataPort = FTPLauncher.FTP_PORT_FILE;
		Integer p1 = dataPort/256;
		Integer p2 = dataPort%256;
		
		InetAddress address = userState.getDataAddress();
		String addressToString = "";
		if(address instanceof Inet6Address){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_500_ERREUR_INTERNE, 
					"Address is IPv6, you should use EPSV command instead");
		}else{
			addressToString = address.getHostAddress().replaceAll("\\.", ",");
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_227_MODE_PASSIF_ACTIVE, 
				"="+addressToString+","+p1+","+p2);
	}

}
