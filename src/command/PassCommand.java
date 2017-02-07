package command;

import utilitary.UserState;
import utilitary.FtpStatusCodes;
import utilitary.UserHandler;

public class PassCommand extends Command {

	@Override
	public String execute(String data, UserState etat) {
		if(etat.getUser() == null){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_503_MAUVAIS_ORDRE_DE_COMMANDES, 
					"Commande USER à envoyer avant");
		}
		if(UserHandler.isPasswordCorrect(etat.getUser(), data)){
			etat.setLogged(true);
			etat.setRepository(UserHandler.getRoot(etat.getUser()));
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_230_UTILISATEUR_CONNECTE, 
					"Utilisateur connecté");
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_530_PAS_CONNECTE,
				"Mauvais mot de passe");
	}

}
