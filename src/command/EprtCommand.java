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
		Integer ancienPort = userState.getDataPort();
		try {
			Integer port = Integer.valueOf(datas[3]);
			if(port > 1024 && port <= 65536){
				userState.setDataPort(Integer.valueOf(datas[3]));
			} else {
				return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_501_ERREUR_DE_SYNTAXE, 
						"Le num�ro de port est invalide (doit �tre entre 1024 et 65536)");
			}
			userState.setDataAddress(datas[1], datas[2]);
			userState.setActive(true);
		} catch (UnknownHostException e) {
			userState.setDataPort(ancienPort);
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_500_ERREUR_INTERNE, 
					"Erreur lors de la r�solution de l'adresse h�te");
		} catch (NumberFormatException e){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_501_ERREUR_DE_SYNTAXE,
					"Erreur lors de la r�solution du port de l'h�te (format non num�rique)");
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES, 
				"Port mis � jour avec succ�s");
	}

}
