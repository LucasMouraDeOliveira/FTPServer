package command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import server.FtpReply;
import server.FtpServer;
import utility.FtpStatusCodes;
import utility.UserState;

/**
 * Implémentation de la commande RNFR
 * Renomme un fichier dans le dossier courant de l'utilisateur sur le serveur. 
 * La commande RNFR définit le nom du fichier source, et est complétée par la commande
 * RNTO qui définit le nouveau nom du fichier
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class RnfrCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState, FtpServer server) {
		if(data == null){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_501_ERREUR_DE_SYNTAXE, 
					"Le nom de fichier est manquant");
		}
		Path p  = Paths.get(userState.getRepository());
		Path p2 = p.resolve(data);
		File f = p2.toFile();
		if(!f.exists()){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
					"Le fichier n'existe pas");
		}else {
			try {
				if(!server.getUserHandler().userHaveRight(userState.getUser(), f)){
					return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE,
							"Le fichier n'est pas accessible");
				}
			} catch (IOException e) {
				e.printStackTrace();
				return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_500_ERREUR_INTERNE,
						"Erreur lors de la récupération des droits utilisateurs");
			}
		}
		userState.setRenameFile(f);
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_350_EN_ATTENTE_D_INFORMATION_SUPPLEMENTAIRE, 
				"Besoin d'informations supplémentaires");
	}

	

}
