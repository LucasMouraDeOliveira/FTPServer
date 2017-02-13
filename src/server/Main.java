package server;

import java.io.IOException;

import errors.BadPropertyException;
import errors.NoPropertyFileException;
import errors.NullPropertyException;

/**
 * Launcher de l'application. Le main prend trois arguments :
 * <ul>
 * 	<li>Le numéro de port de commande</li>
 * 	<li>Le numéro de port de données par défaut</li>
 * 	<li>Le dossier root du serveur, dans lequel seront crées les dossiers des utilisateurs</li>
 * </ul>
 * 
 * Les paramètres sont optionnels, s'ils en manque au moins un, le fichier de configuration par défaut de l'application sera utilisé
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
