package command;

import utilitary.UserState;

public class FeatCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		return "211 - aucune commande supplementaire autorisee";
	}

}
