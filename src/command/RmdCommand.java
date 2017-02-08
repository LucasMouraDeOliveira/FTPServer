package command;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserHandler;
import utilitary.UserState;

/**
 * Implémentation de la commande RMD
 * Supprime un dossier et tout son contenu du serveur
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class RmdCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState) {
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
		}else if(!UserHandler.userHaveRight(userState.getUser(), f)){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE,
					"Le fichier n'est pas accessible");
		}
		if(f.isDirectory()){
			if(recursifdelete(f)){
				return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES, 
						"Le fichier a bien été supprimé");
			} else {
				return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
						"Le fichier n'a pas été supprimé");
			}

		}
		if(f.delete()){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES, 
					"Le fichier a bien été supprimé");
		} else {
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
					"Le fichier n'a pas été supprimé");
		}
	}
	
	private boolean recursifdelete(File parent) {
		for(File f : parent.listFiles()){
			if(f.isDirectory()){
				recursifdelete(f);
			}else{
				f.delete();
			}
		}
		return parent.delete();
	}

}
	