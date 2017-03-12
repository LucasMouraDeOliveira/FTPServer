package command;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import server.FtpReply;
import server.FtpServer;
import user.UserState;
import utility.FtpStatusCodes;

/**
 * Implémentation de la commande RNTO
 * Renomme un fichier dans le dossier courant de l'utilisateur sur le serveur. 
 * Prend en paramètre le nouveau nom du fichier, et intervient après la commande RNFR qui envoie le nom d'origine du fichier
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class RntoCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState, FtpServer server) {
		if(data == null){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_501_ERREUR_DE_SYNTAXE, 
					"Le nom de fichier est manquant");
		}
		File from = userState.getRenameFile();
		Path p  = Paths.get(userState.getRepository());
		Path p2 = p.resolve(data);
		File to = p2.toFile();
		if(to.exists()){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
					"Le fichier existe déjà");
		}else if(!server.getUserHandler().userHaveRight(userState.getUser(), from)){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE,
					"Le fichier n'est pas accessible");
		}
		
		if(from.renameTo(to)){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES, 
					"Le fichier a bien été renommé");
		} else {
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
					"Le fichier n'a pas été renommé");
		}
	}
}
