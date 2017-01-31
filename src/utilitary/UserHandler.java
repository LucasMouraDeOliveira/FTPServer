package utilitary;

import java.util.HashMap;
import java.util.Map;

public class UserHandler {
	
	public static Map<String,String> users;
	
	static {
		users = new HashMap<String,String>();
		users.put("lucas", "l");
		users.put("eliotto", "e");
		users.put("admin", "admin");
	}
	
	public static boolean userExists(String login){
		return users.containsKey(login);
	}
	
	public static boolean isPasswordCorrect(String login, String password){
		return userExists(login) && users.get(login).equals(password);
	}
	
	public static String getRoot(String login){
		return "/home/m1/bricout/Bureau/FTPServer/root/"+login;
	}
}
