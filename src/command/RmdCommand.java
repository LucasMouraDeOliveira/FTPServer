package command;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import utilitary.UserHandler;
import utilitary.UserState;

public class RmdCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		if(data == null){
			return "400 - pas de nom de fichier";
		}
		Path p  = Paths.get(etat.getRepository());
		Path p2 = p.resolve(data);
		File f = p2.toFile();
		if(!f.exists()){
			return "400 - le fichier n'existe pas";
		}else if(!UserHandler.userHaveRight(etat.getUser(), f)){
			return "403 - vous ne pouvez supprimer ce fichier";
		}
		if(f.isDirectory()){
			return recussifdelete(f) ? "200": "400";

		}
		return f.delete() ? "200": "400";
	}

	private boolean recussifdelete(File parent) {
		for(File f : parent.listFiles()){
			if(f.isDirectory()){
				recussifdelete(f);
			}else{
				f.delete();
			}
		}
		return parent.delete();
	}

}
	