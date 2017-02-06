package command;

import java.net.UnknownHostException;

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
		return "200 Connected to specified port";
	}

}
