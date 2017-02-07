package command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

import server.DataCommand;
import server.FtpReply;
import utilitary.Connexion;
import utilitary.FtpStatusCodes;
import utilitary.UserHandler;
import utilitary.UserState;

public class RetrCommand extends LoggedCommand implements DataCommandExecutor{
	
	protected File file;

	@Override
	public FtpReply executeLogged(String data, UserState userState) {
		Path p  = Paths.get(userState.getRepository());
		Path p2 = p.resolve(data);
		file = p2.toFile();
		if(!file.exists()){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE, 
					"Le fichier n'existe pas");
		} 
		if(!UserHandler.userHaveRight(userState.getUser(), file)){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_550_ACTION_NON_REALISEE,
					"Le fichier n'est pas accessible");
		}
				
		new DataCommand(data, userState, this).start();
		return new FtpReply();
	}

	@Override
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
