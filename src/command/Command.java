package command;

import utilitary.UserState;

public abstract class Command {
	
	public abstract String execute(String data, UserState etat);

}
