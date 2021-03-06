package command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import server.FtpReply;
import server.FtpServer;
import user.UserState;
import utility.FtpStatusCodes;

/**
 * <p>Impl�mentation de la commande CWD et de son alias XCWD.
 * Met � jour le dossier courant de l'utilisateur et retourne un code 250 en cas de succ�s.</p> 
 * <p>Les erreurs suivantes peuvent �tre d�clench�es :</p> 
 * <ul>
 * <li>500 : erreur interne </li>
 * <li>550 : Action non r�alis�e (l'utilisateur n'a pas les droit d'acc�s au dossier)</li>
 * </ul>
 * 
 * @author Lucas Moura de Oliveira
 */
public class CwdCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String parameters, UserState userState, FtpServer server) {
		if(parameters == null){
			userState.setRepository(server.getUserHandler().getRoot(userState.getUser()));
		}else{
			String newpath;
			Path p  = Paths.get(userState.getRepository());
			Path p2 = p.resolve(parameters);
			File f = p2.toFile();
			if(f.exists()){
				try {
					newpath = f.getCanonicalPath().replace('\\', '/');
				} catch (IOException e) {
					return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_500_ERREUR_INTERNE, 
							"Erreur lors de la r�solution du chemin de fichier");
				}
				if(!newpath.equals(server.getUserHandler().getRoot(userState.getUser())) && !server.getUserHandler().userHaveRight(userState.getUser(), f)){
					return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
							"Vous n'avez pas les droits requis pour vous d�placer dans ce dossier");
				}
				userState.setRepository(newpath);
			}else{
				return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
						"Le dossier n'existe pas");
			}
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_250_ACTION_SUR_LE_FICHIER_REALISEE_AVEC_SUCCES, 
				"Dossier mis � jour avec succ�s");
	}

}
