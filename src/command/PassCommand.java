package command;

import server.FtpReply;
import server.FtpServer;
import utilitary.FtpStatusCodes;
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
	public FtpReply execute(String data, UserState userState, FtpServer server) {
		if(userState.getUser() == null){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_503_MAUVAIS_ORDRE_DE_COMMANDES, 
					"Commande USER � envoyer avant");
		}
		if(server.getUserHandler().isPasswordCorrect(userState.getUser(), data)){
			userState.setLogged(true);
			userState.setRepository(server.getUserHandler().getRoot(userState.getUser()));
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_230_UTILISATEUR_CONNECTE, 
					"Utilisateur connect�");
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_530_PAS_CONNECTE,
				"Mauvais mot de passe");
	}

}
