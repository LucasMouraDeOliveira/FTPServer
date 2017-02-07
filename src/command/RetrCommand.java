package command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

import server.DataCommand;
import utilitary.Connexion;
import utilitary.FtpStatusCodes;
import utilitary.UserHandler;
import utilitary.UserState;

public class RetrCommand extends LoggedCommand implements DataCommandExecutor{

	@Override
	public String executeLogged(String data, UserState etat) {
		new DataCommand(data, etat, this).start();
		return null;
	}

	@Override
	public void executeThread(String data, UserState userState, Socket socket) {
		Path p  = Paths.get(userState.getRepository());
		Path p2 = p.resolve(data);
		File f = p2.toFile();
		if(f.exists()){
			if(UserHandler.userHaveRight(userState.getUser(), f)){
				try {
					BufferedReader br = new BufferedReader(new FileReader(f));
					String line = "";
					while((line = br.readLine()) != null){
						Connexion.write(socket, line);
					}
					br.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				//TODO erreur
			}
		}else{
			//TODO envoyer message erreur
		}
	}
	
	@Override
	public String getStartCode() {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_125_CONNEXION_ETABLIE_TRANSFERT_DEMARRE, 
				"Connexion établie");
	}

	@Override
	public String getEndCode() {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_226_FERMETURE_CONNEXION_DONNEES,
				"Fermeture de la connexion");
	}

}
