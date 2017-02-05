package command;

import utilitary.UserState;
import utilitary.UserHandler;

public class UserCommand extends Command {

	@Override
	public String execute(String data, UserState etat) {
		if(UserHandler.userExists(data)){
			etat.setUser(data);
			System.out.println("Utilisateur " + etat.getUser() + " tente de se connecter");
			return "331 - Vas y continue tu m'interesses";
		}
		return "430 - Je te connais pas mec ...";
	}

}
