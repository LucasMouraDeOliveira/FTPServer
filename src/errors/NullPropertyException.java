package errors;

public class NullPropertyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4311456789140284659L;
	
	public NullPropertyException(String property) {
		super("La propri�t� " + property + " est mal renseign�e");
	}

}
