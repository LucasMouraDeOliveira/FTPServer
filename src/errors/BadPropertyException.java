package errors;

public class BadPropertyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -418489720247123150L;
	
	public BadPropertyException(String property){
		super("La propri�t� " + property + " est mal renseign�e");
	}

}
