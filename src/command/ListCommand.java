package command;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import server.FtpReply;
import server.FtpServer;
import server.ThreadData;
import utilitary.Connexion;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

/**
 * Implémentation de la commande LIST
 * Renvoie le contenu du dossier courant de l'utilisateur
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class ListCommand extends LoggedCommand implements DataCommandExecutor{

	@Override
	public FtpReply executeLogged(String data, UserState userState, FtpServer ftpServer) {
		new ThreadData(data, userState, this).start();
		return null;
	}

	@Override
	public void executeThread(String data, UserState userState, Socket dataSocket) {
		File folder = new File(userState.getRepository());
		File[] files = folder.listFiles();
		String retour = "";
		for(File file : files){
			retour+=file.getName()+"\n";
		}
		try {
			PrintWriter writer = new PrintWriter(dataSocket.getOutputStream());
			Connexion.write(writer, retour);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
