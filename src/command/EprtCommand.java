package command;

import utilitary.UserState;

public class EprtCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		data = data.substring(0, data.length()-1);
		data = data.substring(data.lastIndexOf("|")+1, data.length());
		etat.setDataPort(Integer.valueOf(data));
		return "200 - Connexion sur le port " + data + " (pas) effectu�e";
	}

}
