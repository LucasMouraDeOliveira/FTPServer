package command;

import utilitary.UserState;

public class TypeCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		return "200 Type set to " + data;
	}

}
