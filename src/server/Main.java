package server;

import java.io.IOException;

import errors.BadPropertyException;
import errors.NoPropertyFileException;
import errors.NullPropertyException;

/**
 * Launcher de l'application. Le main prend trois arguments :
 * <ul>
 * 	<li>Le num�ro de port de commande</li>
 * 	<li>Le num�ro de port de donn�es par d�faut</li>
 * 	<li>Le dossier root du serveur, dans lequel seront cr�es les dossiers des utilisateurs</li>
 * </ul>
 * 
 * Les param�tres sont optionnels, s'ils en manque au moins un, le fichier de configuration par d�faut de l'application sera utilis�
 * 
 * @author Lucas Moura de Oliveira
 */
public class Main {
	
	public static void main(String[] args) throws NoPropertyFileException, NullPropertyException, BadPropertyException, IOException {
		//Si le nombre d'arguments est bon, on charge la configuration de l'utilisateur
		if(args.length == 3){
			new FtpServer(args[0], args[1], args[2]).startServer();
		} else { //Sinon on charge la configuration par d�faut
			new FtpServer().startServer();
		}
	}

}
