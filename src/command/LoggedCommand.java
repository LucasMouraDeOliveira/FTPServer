package command;

import utilitary.UserState;

public abstract class LoggedCommand extends Command {

	@Override
	public String execute(String data, UserState etat) {
		if(etat.isLogged()){
			return executeLogged(data, etat);
		}
		return "403 - vous devez etre connecte";
	}
	
	public abstract String executeLogged(String data, UserState etat);

}
