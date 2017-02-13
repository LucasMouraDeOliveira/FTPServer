package command;

import java.net.UnknownHostException;

import server.FtpReply;
import server.FtpServer;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

/**
 * Implémentation de la commande PORT
 * Permet à l'utilisateur de demander au serveur de se connecter sur le port et l'adresse passés en paramètre 
 * pour l'envoi de données via la data socket.
 * Le format de la commande est : PORT h1,h2,h3,h4,p1,p2 avec h1..4 l'adresse au format IPv4 et p1,p2 le port tel que
 * p1*256+p2 = le numéro du port
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class PortCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState, FtpServer server) {
		String[] datas = data.split(",");
		try {
			userState.setDataAddress("1", datas[0]+"."+datas[1]+"."+datas[2]+"."+datas[3]);
			userState.setDataPort(Integer.valueOf(datas[4])*256+Integer.valueOf(datas[5]));
			userState.setActive(true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES, 
				"Connecté sur le port spécifié");
				
	}

}
