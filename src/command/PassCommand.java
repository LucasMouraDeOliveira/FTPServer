package command;

import utilitary.UserState;
import utilitary.UserHandler;

public class PassCommand extends Command {

	@Override
	public String execute(String data, UserState etat) {
		if(UserHandler.isPasswordCorrect(etat.getUser(), data)){
			etat.setLogged(true);
			System.out.println("Utilisateur " + etat.getUser() +" est connecte");
			etat.setRepository(UserHandler.getRoot(etat.getUser()));
			return "230 - Utilisateur connecte";
		}
		return "430 - Mauvais mdp fdp";
	}

}
