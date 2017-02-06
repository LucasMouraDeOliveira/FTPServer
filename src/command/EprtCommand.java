package command;

import java.net.UnknownHostException;

import utilitary.UserState;

public class EprtCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		String[] datas = data.split("\\|");
		try {
			etat.setDataAddress(datas[1], datas[2]);
			etat.setDataPort(Integer.valueOf(datas[3]));
			etat.setActive(true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "200 Port command successful";
	}

}
