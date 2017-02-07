package command;

import java.io.File;
import java.net.Socket;

import server.DataCommand;
import utilitary.Connexion;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class NlstCommand extends LoggedCommand implements DataCommandExecutor {

	@Override
	public String executeLogged(String data, UserState etat) {
		new DataCommand(data, etat, this).start();
		//TODO modifier pour permettre � la commande de ne rien retourner
		return null;
	}

	@Override
	public void executeThread(String data, UserState userState, Socket dataSocket) {
		File folder = new File(userState.getRepository());
		File[] files = folder.listFiles();
		String retour = "";
		if(files != null){
			for(File file : files){
				retour+=file.getName()+"\n";
			}
		}
		Connexion.write(dataSocket, retour);
	}
	
	@Override
	public String getStartCode() {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_125_CONNEXION_ETABLIE_TRANSFERT_DEMARRE, 
				"Connexion �tablie");
	}

	@Override
	public String getEndCode() {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_226_FERMETURE_CONNEXION_DONNEES,
				"Fermeture de la connexion");
	}
	 

}
