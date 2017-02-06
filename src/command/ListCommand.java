package command;

import java.io.File;
import java.net.Socket;

import server.DataCommand;
import utilitary.Connexion;
import utilitary.UserState;

public class ListCommand extends LoggedCommand implements DataCommandExecutor{

	@Override
	public String executeLogged(String data, UserState etat) {
		new DataCommand(data, etat, this).start();
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
		Connexion.write(dataSocket, retour);
	}

}
