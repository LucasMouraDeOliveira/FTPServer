package command;

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
			if(p2.toFile().exists()){
				try {
					newpath = p2.toFile().getCanonicalPath();
				} catch (IOException e) {
					e.printStackTrace();
					return "500";
				}
				
				if(newpath.startsWith(UserHandler.getRoot(etat.getUser()))){
					etat.setRepository(newpath);
				}else{
					return "403";
				}
			}else{
				return "403";
			}
		}
		return "200";
	}

}
