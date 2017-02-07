package command;

import java.io.File;
import java.net.Socket;

import server.DataCommand;
import utilitary.Connexion;
import utilitary.UserState;

public class MlstCommand extends LoggedCommand implements DataCommandExecutor{

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
			retour+="type="+ getType(file)+"; size="+file.getTotalSpace()+"; modify="+file.lastModified()+"; "+file.getName()+"\n";
		}
		Connexion.write(dataSocket, retour);
	}

	private String getType(File file) {
		if(file.isDirectory()){
			return "dir";
		}
		return file.getName().split(".")[1];
	}

}
