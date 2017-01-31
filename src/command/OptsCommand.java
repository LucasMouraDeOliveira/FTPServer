package command;

import utilitary.UserState;

public class OptsCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		return "202 - mais on s'en ballec en fait de l'UTF-8";
	}

}
