package command;

import java.net.UnknownHostException;

import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class PortCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		String[] datas = data.split(",");
		try {
			etat.setDataAddress("1", datas[0]+"."+datas[1]+"."+datas[2]+"."+datas[3]);
			etat.setDataPort(Integer.valueOf(datas[4])*256+Integer.valueOf(datas[5]));
			etat.setActive(true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES, 
				"Connecté sur le port spécifié");
				
	}

}
