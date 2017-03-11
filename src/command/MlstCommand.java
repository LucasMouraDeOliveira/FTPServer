package command;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import server.FtpReply;
import server.FtpServer;
import server.ThreadData;
import user.UserState;
import utility.Connexion;
import utility.FtpStatusCodes;

/**
 *
 * @author Lucas Moura de Oliveira
 *
 */
public class MlstCommand extends LoggedCommand implements DataCommandExecutor{

	@Override
	public FtpReply executeLogged(String data, UserState userState, FtpServer server) {
		new ThreadData(data, userState, this).start();
		return new FtpReply();
	}

	@Override
	public void executeThread(String data, UserState userState, Socket dataSocket) {
		File folder = new File(userState.getRepository());
		File[] files = folder.listFiles();
		String retour = "";
		for(File file : files){
			retour+="type="+ getType(file)+"; size="+file.getTotalSpace()+"; modify="+file.lastModified()+"; "+file.getName()+"\n";
		}
		if(retour != ""){
			retour = retour.substring(0, retour.length()-1);
			try {
				PrintWriter writer = new PrintWriter(dataSocket.getOutputStream());
				Connexion.write(writer, retour);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getType(File file) {
		return file.isDirectory() ? "directory" : "file";
	}

	@Override
	public FtpReply getStartCode() {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_125_CONNEXION_ETABLIE_TRANSFERT_DEMARRE, 
				"Connexion �tablie");
	}

	@Override
	public FtpReply getEndCode() {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_226_FERMETURE_CONNEXION_DONNEES,
				"Fermeture de la connexion");
	}

}
