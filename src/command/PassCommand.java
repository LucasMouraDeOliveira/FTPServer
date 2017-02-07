package command;

import utilitary.UserState;
import utilitary.FtpStatusCodes;
import utilitary.UserHandler;

public class PassCommand extends Command {

	@Override
	public String execute(String data, UserState etat) {
		if(UserHandler.isPasswordCorrect(etat.getUser(), data)){
			etat.setLogged(true);
			etat.setRepository(UserHandler.getRoot(etat.getUser()));
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_230_UTILISATEUR_CONNECTE, 
					"Utilisateur connecté");
		}
		return "430 - Mauvais mot de passe";
	}

}
