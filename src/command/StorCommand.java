package command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

import server.FtpReply;
import server.FtpServer;
import server.ThreadData;
import utility.Connexion;
import utility.FtpStatusCodes;
import utility.UserState;

/**
 * Implémentation de la commande STOR
 * Upload un fichier dans le dossier courant de l'utilisateur sur le serveur
 * Le fichier est uploadé sous la forme d'un flux binaire via la data socket
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class StorCommand extends LoggedCommand implements DataCommandExecutor{
	
	protected File file;

	@Override
	public FtpReply executeLogged(String data, UserState userState, FtpServer server) {
		Path p  = Paths.get(userState.getRepository());
		Path p2 = p.resolve(data);
		file = p2.toFile();
		if(file.exists()){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
					"Le fichier existe déjà");
		} 
		try {
			if(!server.getUserHandler().userHaveRight(userState.getUser(), file)){
				return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE,
						"Vous n'avez pas les droits pour uploader ce fichier ici");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_500_ERREUR_INTERNE,
					"Erreur lors de la récupération des droits utilisateurs");
		}
		new ThreadData(data, userState, this).start();
		return new FtpReply();
	}

	@Override
	public void executeThread(String data, UserState userState, Socket socket) {
		/*BufferedReader reader = null;
		PrintWriter writer = null;
		try {
			String line = "";
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(new FileWriter(file));
			while((line = Connexion.read(reader)) != null){
				writer.write(line+"\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		DataInputStream reader = null;
		DataOutputStream writer = null;
		try {
			reader = new DataInputStream(socket.getInputStream());
			writer = new DataOutputStream(new FileOutputStream(file));
			byte[] bytes;
			while((bytes = Connexion.readBinary(reader)) != null){
				writer.write(bytes);
			}
			writer.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public FtpReply getStartCode() {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_150_STATUS_FICHIER_OK_OUVERTURE_EN_COURS, 
				"Ouverture du flux de données");
	}

	@Override
	public FtpReply getEndCode() {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_226_FERMETURE_CONNEXION_DONNEES,
				"Fermeture de la connexion");
	}

}
