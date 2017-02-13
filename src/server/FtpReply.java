package server;

/**
 * Classe de gestion des messages de retour.
 * Une réponse FTP simple est composée d'un code de retour, d'un espace puis d'un message (optionnel).
 * 
 * @author Lucas Moura de Oliveira
 */
public class FtpReply {
	
	protected String code;
	
	protected String message;
	
	/**
	 * @return le code de retour après l'exécution d'une commande
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Définit le code de retour après l'exécution d'une commande
	 * 
	 * @param code le code de retour
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @return le message de retour après l'exécution d'une commande
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Définit le message de retour après l'exécution d'une commande
	 * 
	 * @param message le message de retour
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @return vrai si la réponse est vide (dans certains cas on n'envoie pas de réponse)
	 */
	public boolean isEmpty(){
		return this.code == null || this.code.isEmpty();
	}
	
	@Override
	public String toString(){
		return this.code + " " + this.message;
	}

}
