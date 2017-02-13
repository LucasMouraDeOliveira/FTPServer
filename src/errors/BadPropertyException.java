package errors;

/**
 * Mauvaise propriété lors du chargement du serveur
 */

public class BadPropertyException extends Exception {

	private static final long serialVersionUID = -418489720247123150L;
	
	public BadPropertyException(String property){
		super("La propriété " + property + " est mal renseignée");
	}

}
