package command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import utilitary.FtpStatusCodes;
import utilitary.UserHandler;
import utilitary.UserState;

public class CwdCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		// data = null  go -> root
		if(data == null){
			etat.setRepository(UserHandler.getRoot(etat.getUser()));
		}else{
			String newpath;
			Path p  = Paths.get(etat.getRepository());
			Path p2 = p.resolve(data);
			File f = p2.toFile();
			if(f.exists()){
				try {
					newpath = f.getCanonicalPath().replace('\\', '/');
				} catch (IOException e) {
					e.printStackTrace();
					return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_500_ERREUR_INTERNE, 
							"Erreur lors de la résolution du chemin de fichier");
				}
				if(!UserHandler.userHaveRight(etat.getUser(), f)){
					return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
							"Vous n'avez pas les droits requis pour vous déplacer dans ce dossier");
				}
				etat.setRepository(newpath);
			}else{
				return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
						"Le dossier n'existe pas");
			}
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_250_ACTION_SUR_LE_FICHIER_REALISEE_AVEC_SUCCES, 
				"Dossier mis à jour avec succès");
	}

}
