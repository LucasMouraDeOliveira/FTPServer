package user;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilitaire qui gère la connexion des utilisateurs
 * 
 * @author Lucas Moura de Oliveira
 */
public class UserHandler {
	
	public Map<String,String> users;
	
	protected String dossierRoot;
	
	public UserHandler(String dossierRoot) throws IOException{
		File file = new File(dossierRoot);
		if(!file.exists()){
			file.mkdir(); // crée le dossier root si il n'existe pas
		}	
		this.dossierRoot = file.getCanonicalPath().replace('\\', '/');
		this.initUsers();
	}

	private void initUsers() throws IOException{
		this.users = new HashMap<String, String>();
		this.addUser("lucas", "l");
		this.addUser("eliott", "e");
		this.addUser("admin", "admin");
	}
	
	private void addUser(String login, String password) throws IOException{
		if(this.users.containsKey(login)){
			return;
		}
		this.users.put(login, password);
		String root = getRoot(login);
		Path pathRoot = Paths.get(root);
		if(!Files.exists(pathRoot)){
			Files.createDirectories(pathRoot);
		}
	}
	
	/**
	 * Retourne vrai si l'utilisateur est connu par le serveur, faux sinon
	 * 
	 * @param login le login de l'utilisateur
	 * 
	 * @return vrai si l'utilisateur existe
	 */
	public boolean userExists(String login){
		return this.users.containsKey(login);
	}
	
	/**
	 * Retourne vrai si le mot de passe d'un utilisateur est correct
	 * 
	 * @param login le login de l'utilisateur
	 * @param password le mot de passe de l'utilisateur
	 * 
	 * @return vrai si le mot de passe de l'utilisateur est correct
	 */
	public boolean isPasswordCorrect(String login, String password){
		return this.userExists(login) && this.users.get(login).equals(password);
	}
	
	/**
	 * Retourne le dossier root d'un utilisateur sur le serveur FTP
	 * 
	 * @param login le login de l'utilisateur
	 * 
	 * @return le dossier root de l'utilisateur
	 */
	public String getRoot(String login){
		return dossierRoot+"/"+login;
	}
	
	/**
	 * Retourne vrai si un utilisateur a le droit d'accéder à un fichier/dossier
	 * 
	 * @param login le login de l'utilisateur
	 * @param f le fichier/dossier
	 * 
	 * @return vrai si l'utilisateur à le droit d'accéder au fichier (s'il se trouve dans son dossier root)
	 */
	public boolean userHaveRight(String login, File f){
		try {
			return f.getCanonicalPath().replace('\\', '/').startsWith(getRoot(login)+"/");
		} catch (IOException e) {
			/* L'erreur ne devrait jamais arriver, mais dans le cas ou ça arrive on affiche le message d'erreur */
			e.printStackTrace();
			return false;
		}
	}
}
