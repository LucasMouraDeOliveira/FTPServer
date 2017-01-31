package command;

import utilitary.UserState;

public class PwdCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		String path = etat.getRepository();
		return "257 \""+path+"\"";
	}

}
