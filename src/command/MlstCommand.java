package command;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import server.DataCommand;
import server.FtpReply;
import utilitary.Connexion;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

/**
 * Implémentation de la commande MLST
 * Je ne sais pas ce qu'elle fait ...
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class MlstCommand extends LoggedCommand implements DataCommandExecutor{

	@Override
	public FtpReply executeLogged(String data, UserState userState) {
		new DataCommand(data, userState, this).start();
		return null;
	}

	@Override
	public void executeThread(String data, UserState userState, Socket dataSocket) {
		File folder = new File(userState.getRepository());
		File[] files = folder.listFiles();
		String retour = "";
		for(File file : files){
			retour+="type="+ getType(file)+"; size="+file.getTotalSpace()+"; modify="+file.lastModified()+"; "+file.getName()+"\n";
		}
		try {
			PrintWriter writer = new PrintWriter(dataSocket.getOutputStream());
			Connexion.write(writer, retour);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retourne le type d'un fichier.
	 * Si le fichier est un dossier, retourne dir, sinon retourne l'extension du fichier
	 * 
	 * @param file le fichier
	 * 
	 * @return le type du fichier
	 */
	private String getType(File file) {
		if(file.isDirectory()){
			return "dir";
		}
		return file.getName().split(".")[1];
	}
	
	@Override
	public FtpReply getStartCode() {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_125_CONNEXION_ETABLIE_TRANSFERT_DEMARRE, 
				"Connexion établie");
	}

	@Override
	public FtpReply getEndCode() {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_226_FERMETURE_CONNEXION_DONNEES,
				"Fermeture de la connexion");
	}

}
