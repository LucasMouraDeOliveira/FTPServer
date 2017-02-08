package command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserHandler;
import utilitary.UserState;

/**
 * Implémentation de la commande CWD et de son alias XCWD.
 * Met à jour le dossier courant de l'utilisateur. 
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class CwdCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState) {
		if(data == null){
			userState.setRepository(UserHandler.getRoot(userState.getUser()));
		}else{
			String newpath;
			Path p  = Paths.get(userState.getRepository());
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
				if(!UserHandler.userHaveRight(userState.getUser(), f)){
					return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
							"Vous n'avez pas les droits requis pour vous déplacer dans ce dossier");
				}
				userState.setRepository(newpath);
			}else{
				return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
						"Le dossier n'existe pas");
			}
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_250_ACTION_SUR_LE_FICHIER_REALISEE_AVEC_SUCCES, 
				"Dossier mis à jour avec succès");
	}

}
