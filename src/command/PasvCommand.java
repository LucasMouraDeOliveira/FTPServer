package command;

import java.net.Inet6Address;
import java.net.InetAddress;

import server.FtpServer;
import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

/**
 * Implémentation de la commande PASV
 * Passe la connexion en mode passif : le serveur décide du port et de l'adresse de connexion et renvoie un message
 * à l'utilisateur au format 227 =h1,h2,h3,h4,p1,p2 avec h1..4 l'adresse au format IPv4 et p1,p2 le port tel que
 * p1*256+p2 = le numéro du port 
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class PasvCommand extends LoggedCommand {

	@Override
	public FtpReply executeLogged(String data, UserState userState, FtpServer server) {
		Integer dataPort = server.getDataPort();
		Integer p1 = dataPort/256;
		Integer p2 = dataPort%256;
		
		InetAddress address = userState.getDataAddress();
		String addressToString = "";
		if(address instanceof Inet6Address){
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_500_ERREUR_INTERNE, 
					"Address is IPv6, you should use EPSV command instead");
		}else{
			addressToString = address.getHostAddress().replaceAll("\\.", ",");
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_227_MODE_PASSIF_ACTIVE, 
				"="+addressToString+","+p1+","+p2);
	}

}
