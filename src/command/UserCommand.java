package command;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

/**
 * Implémentation de la commande USER
 * Conserve le login de l'utilisateur et renvoie un code 331 qui demande l'envoi du mot de passe
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class UserCommand implements Command {

	@Override
	public FtpReply execute(String data, UserState userState) {
		userState.setUser(data);
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_331_USER_OK_NEED_PASSWORD, 
				"Mot de passe attendu");
	}

}
