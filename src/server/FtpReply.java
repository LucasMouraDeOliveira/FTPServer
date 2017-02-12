package server;

/**
 * Classe de gestion des messages de retour.
 * Une r�ponse FTP simple est compos�e d'un code de retour, d'un espace puis d'un message (optionnel) 
 * 
 * @author Lucas Moura de Oliveira
 */
public class FtpReply {
	
	protected String code;
	
	protected String message;
	
	/**
	 * @return le code de retour apr�s l'ex�cution d'une commande
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * D�finit le code de retour apr�s l'ex�cution d'une commande
	 * 
	 * @param code le code de retour
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * @return le message de retour apr�s l'ex�cution d'une commande
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * D�finit le message de retour apr�s l'ex�cution d'une commande
	 * 
	 * @param message le message de retour
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @return vrai si la r�ponse est vide (dans certains cas on n'envoie pas de r�ponse)
	 */
	public boolean isEmpty(){
		return this.code == null || this.code.isEmpty();
	}
	
	@Override
	public String toString(){
		return this.code + " " + this.message;
	}

}
