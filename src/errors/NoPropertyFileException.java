package errors;

/**
 * Erreur qui est d�clench�e quand le fichier de configuration du serveur est introuvable
 * 
 * @author Lucas Moura de Oliveira
 */
public class NoPropertyFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2791834471681124790L;
	
	public NoPropertyFileException(String filePath) {
		super("Le fichier de configuration n'a pas �t� trouv� � l'emplacement : " + filePath);
	}

}
