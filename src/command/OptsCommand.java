package command;

import utilitary.UserState;

public class OptsCommand extends Command {

	@Override
	public String execute(String data, UserState etat) {
		return "202 - mais on s'en ballec en fait de l'UTF-8";
	}

}
