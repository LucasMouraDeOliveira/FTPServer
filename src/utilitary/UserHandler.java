package utilitary;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilitaire qui gère la connexion des utilisateurs
 * 
 * @author Lucas Moura de Oliveira
 */
public class UserHandler {
	
	public static Map<String,String> users;
	
	public static String root;

	static {
		users = new HashMap<String,String>();
		users.put("lucas", "l");
		users.put("eliott", "e");
		users.put("admin", "admin");
	}
	
	/**
	 * Retourne vrai si l'utilisateur est connu par le serveur, faux sinon
	 * 
	 * @param login le login de l'utilisateur
	 * 
	 * @return vrai si l'utilisateur existe
	 */
	public static boolean userExists(String login){
		return users.containsKey(login);
	}
	
	/**
	 * Retourne vrai si le mot de passe d'un utilisateur est correct
	 * 
	 * @param login le login de l'utilisateur
	 * @param password le mot de passe de l'utilisateur
	 * 
	 * @return vrai si le mot de passe de l'utilisateur est correct
	 */
	public static boolean isPasswordCorrect(String login, String password){
		return userExists(login) && users.get(login).equals(password);
	}
	
	/**
	 * Retourne le dossier root d'un utilisateur sur le serveur FTP
	 * 
	 * @param login le login de l'utilisateur
	 * 
	 * @return le dossier root de l'utilisateur
	 */
	public static String getRoot(String login){
		return root+login;
	}
	
	/**
	 * Retourne vrai si un utilisateur a le droit d'accéder à un fichier/dossier
	 * 
	 * @param login le login de l'utilisateur
	 * @param f le fichier/dossier
	 * 
	 * @return vrai si l'utilisateur à le droit d'accéder au fichier (s'il se trouve dans son dossier root)
	 */
	public static boolean userHaveRight(String login, File f){
		try {
			return f.getCanonicalPath().replace('\\', '/').startsWith(getRoot(login));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
