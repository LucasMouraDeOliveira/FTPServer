package command;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import server.FtpReply;
import server.FtpServer;
import server.ThreadData;
import utilitary.Connexion;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

/**
 * Implémentation de la commande RETR
 * Télécharge un fichier depuis le serveur en envoyant un flux de byte par la data socket
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class RetrCommand extends LoggedCommand implements DataCommandExecutor{
	
	protected Path path;

	@Override
	public FtpReply executeLogged(String data, UserState userState, FtpServer server) {
		Path p  = Paths.get(userState.getRepository());
		path = p.resolve(data);
		File file = path.toFile();
		if(!file.exists()){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
					"Le fichier n'existe pas");
		} 
		try {
			if(!server.getUserHandler().userHaveRight(userState.getUser(), file)){
				return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE,
						"Le fichier n'est pas accessible");
			}
		} catch (IOException e) {
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_500_ERREUR_INTERNE,
					"Erreur lors de la récupération des droits utilisateurs");
		}
				
		new ThreadData(data, userState, this).start();
		return new FtpReply();
	}
	
	@Override
	public void executeThread(String data, UserState userState, Socket dataSocket) {
		DataOutputStream writer = null;
		try {
			writer = new DataOutputStream(dataSocket.getOutputStream());
			byte[] binaires = Files.readAllBytes(path);
			Connexion.writeBinary(writer, binaires);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*@Override
	public void executeThread(String data, UserState userState, Socket dataSocket) {
		BufferedReader reader = null;
		PrintWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			writer = new PrintWriter(dataSocket.getOutputStream());
			String line = "";
			while((line = reader.readLine()) != null){
				Connexion.write(writer, line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			writer.close();
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
	
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
