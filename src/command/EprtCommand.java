package command;

import java.net.UnknownHostException;

import server.FtpReply;
import server.FtpServer;
import user.UserState;
import utility.FtpStatusCodes;

/**
 * Impl�mentation de la commande EPRT.
 * Met � jour l'adresse et le port de connexion de la socket de donn�es.
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class EprtCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState, FtpServer server) {
		String[] datas = data.split("\\|");
		try {
			userState.setDataAddress(datas[1], datas[2]);
			userState.setDataPort(Integer.valueOf(datas[3]));
			userState.setActive(true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_500_ERREUR_INTERNE, 
					"Erreur lors de la r�solution de l'adresse h�te");
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES, 
				"Port mis � jour avec succ�s");
	}

}
