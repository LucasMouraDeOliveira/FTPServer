package utilitary;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserHandler {
	
	public static Map<String,String> users;
	public static String root = "C:/Users/brico/Documents/GitHub/FTPServer/root/";
	//return "/home/m1/bricout/Bureau/FTPServer/root/"+login;

	
	static {
		users = new HashMap<String,String>();
		users.put("lucas", "l");
		users.put("eliott", "e");
		users.put("admin", "admin");
	}
	
	public static boolean userExists(String login){
		return users.containsKey(login);
	}
	
	public static boolean isPasswordCorrect(String login, String password){
		return userExists(login) && users.get(login).equals(password);
	}
	
	public static String getRoot(String login){
		return root+login;
	}
	
	public static boolean userHaveRight(String login, File f){
		try {
			return !f.getCanonicalPath().replace('\\', '/').startsWith(getRoot(login));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
