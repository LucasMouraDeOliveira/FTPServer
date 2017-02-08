package command;

import server.FTPLauncher;
import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

/**
 * Impl�mentation de la commande EPSV
 * Passe la connexion en mode passif �tendu : le serveur choisit un port de connexion pour la socket de donn�es
 * et informe le client du num�ro de port sur lequel se connecter.
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class EpsvCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState) {
		String port = ""+FTPLauncher.FTP_PORT_FILE;
		userState.setDataPort(Integer.valueOf(port));
		userState.setActive(false);
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_229_MODE_PASSIF_ETENDU_ACTIVE, 
				"Passage en mode passif �tendu (|||"+userState.getDataPort()+"|)");
	}

}
