package command;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserHandler;
import utilitary.UserState;

public class RntoCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState) {
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
		}else if(!UserHandler.userHaveRight(userState.getUser(), to)){
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
