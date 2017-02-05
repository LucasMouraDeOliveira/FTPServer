package command;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import utilitary.UserHandler;
import utilitary.UserState;

public class CwdCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		// data = null  go -> root
		if(data == null){
			etat.setRepository(UserHandler.getRoot(etat.getUser()));
		}else{
			String newpath;
			Path p  = Paths.get(etat.getRepository());
			Path p2 = p.resolve(data);
			File f = p2.toFile();
			if(f.exists()){
				try {
					newpath = f.getCanonicalPath().replace('\\', '/');
				} catch (IOException e) {
					e.printStackTrace();
					return "500";
				}
				if(UserHandler.userHaveRight(etat.getUser(), f)){
					return "403 - vous ne pouvez pas acceder a ce fichier";
				}
				etat.setRepository(newpath);
			}else{
				return "403 - file don't exists";
			}
		}
		return "200";
	}

}
