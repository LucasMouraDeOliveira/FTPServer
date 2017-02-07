package command;

import java.net.UnknownHostException;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class PortCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState) {
		String[] datas = data.split(",");
		try {
			userState.setDataAddress("1", datas[0]+"."+datas[1]+"."+datas[2]+"."+datas[3]);
			userState.setDataPort(Integer.valueOf(datas[4])*256+Integer.valueOf(datas[5]));
			userState.setActive(true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES, 
				"Connecté sur le port spécifié");
				
	}

}
