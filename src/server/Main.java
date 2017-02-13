package server;

import java.io.IOException;

import errors.BadPropertyException;
import errors.NoPropertyFileException;
import errors.NullPropertyException;

/**
 * Launcher de l'application
 * 
 * @author Lucas Moura de Oliveira
 */
public class Main {
	
	public static void main(String[] args) throws NoPropertyFileException, NullPropertyException, BadPropertyException, IOException {
		//Si le nombre d'arguments est bon, on charge la configuration de l'utilisateur
		if(args.length == 3){
			new FtpServer(args[0], args[1], args[2]).startServer();
		} else { //Sinon on charge la configuration par défaut
			new FtpServer().startServer();
		}
	}

}
