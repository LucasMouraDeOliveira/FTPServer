package command;

import utilitary.UserState;

public class ListCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		etat.connectUser();
		return "150 - data connection ouverte";
	}

}
