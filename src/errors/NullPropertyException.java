package errors;

/**
 * @author bricout
 *
 *  propriété null lors du chargement du serveur
 */
public class NullPropertyException extends Exception {

	private static final long serialVersionUID = 4311456789140284659L;
	
	public NullPropertyException(String property) {
		super("La propriété " + property + " est mal renseignée");
	}

}
