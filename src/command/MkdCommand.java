package command;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserHandler;
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
	public FtpReply executeLogged(String data, UserState userState) {
		if(data == null){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_501_ERREUR_DE_SYNTAXE, 
					"Le nom de fichier est manquant");
		}
		Path p  = Paths.get(userState.getRepository());
		Path p2 = p.resolve(data);
		File f = p2.toFile();
		if(f.exists()){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
					"Le fichier n'existe pas");
		}else if(!UserHandler.userHaveRight(userState.getUser(), f)){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE,
					"Le fichier n'est pas accessible");
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
