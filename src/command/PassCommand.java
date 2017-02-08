package command;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserHandler;
import utilitary.UserState;

/**
 * Impl�mentation de la commande PASS
 * V�rifie la validit� du mot de passe envoy� par un utilisateur.
 * La commande PASS est normalement envoy�e apr�s USER et permet � l'utilisateur de s'authentifier
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class PassCommand implements Command {

	@Override
	public FtpReply execute(String data, UserState userState) {
		if(userState.getUser() == null){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_503_MAUVAIS_ORDRE_DE_COMMANDES, 
					"Commande USER � envoyer avant");
		}
		if(UserHandler.isPasswordCorrect(userState.getUser(), data)){
			userState.setLogged(true);
			userState.setRepository(UserHandler.getRoot(userState.getUser()));
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_230_UTILISATEUR_CONNECTE, 
					"Utilisateur connect�");
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_530_PAS_CONNECTE,
				"Mauvais mot de passe");
	}

}
