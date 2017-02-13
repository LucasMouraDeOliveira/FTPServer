package command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import server.FtpReply;
import server.FtpServer;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

/**
 * Implémentation de la commande MKD et de son alias XMKD
 * Crée un dossier dans le dossier courant de l'utilisateur
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class MkdCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState, FtpServer server) {
		if(data == null){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_501_ERREUR_DE_SYNTAXE, 
					"Le nom de fichier est manquant");
		}
		Path p  = Paths.get(userState.getRepository());
		Path p2 = p.resolve(data);
		File f = p2.toFile();
		if(f.exists()){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
					"Le dossier existe");
		} else {
			try {
				if(!server.getUserHandler().userHaveRight(userState.getUser(), f)){
					return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE,
							"Le dossier n'est pas accessible");
				}
			} catch (IOException e) {
				e.printStackTrace();
				return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_500_ERREUR_INTERNE, 
						"Erreur lors de la récupération des droits utilisateurs");
			}
		}
		if(f.mkdirs()){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES, 
					"Le dossier a été crée");
		}else{
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
					"La création de dossier a échoué");
		}
	}
}
