package command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import utilitary.UserHandler;
import utilitary.UserState;

public class RntoCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		if(data == null){
			return "400 - pas de nom de fichier";
		}
		File from = etat.getRenameFile();
		Path p  = Paths.get(etat.getRepository());
		Path p2 = p.resolve(data);
		File to = p2.toFile();
		if(to.exists()){
			return "400 - ce fichier exist deja";
		}else if(UserHandler.userHaveRight(etat.getUser(), to)){
			return "403 - vous ne pouvez pas aller au dela de votre dossier";
		}
		return from.renameTo(to) ? "200 - successful" : "400 - il y a eu un problème";
	}
}
