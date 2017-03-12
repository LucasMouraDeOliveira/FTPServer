package command;

import java.net.UnknownHostException;

import server.FtpReply;
import server.FtpServer;
import user.UserState;
import utility.FtpStatusCodes;

/**
 * Implémentation de la commande EPRT.
 * Met à jour l'adresse et le port de connexion de la socket de données.
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
						"Le numéro de port est invalide (doit être entre 1024 et 65536)");
			}
			userState.setDataAddress(datas[1], datas[2]);
			userState.setActive(true);
		} catch (UnknownHostException e) {
			userState.setDataPort(ancienPort);
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_500_ERREUR_INTERNE, 
					"Erreur lors de la résolution de l'adresse hôte");
		} catch (NumberFormatException e){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_501_ERREUR_DE_SYNTAXE,
					"Erreur lors de la résolution du port de l'hôte (format non numérique)");
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES, 
				"Port mis à jour avec succès");
	}

}
