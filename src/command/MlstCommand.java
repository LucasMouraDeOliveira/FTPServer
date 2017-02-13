package command;

import java.net.Socket;

import server.FtpReply;
import server.FtpServer;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

/**
 * Impl�mentation de la commande MLST (non impl�ment�e)
 * 
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class MlstCommand extends LoggedCommand implements DataCommandExecutor{

	@Override
	public FtpReply executeLogged(String data, UserState userState, FtpServer server) {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_202_COMMANDE_NON_PRISE_EN_CHARGE, 
				"Commande non impl�ment�e");
	}

	@Override
	public void executeThread(String data, UserState userState, Socket dataSocket) {
//		File folder = new File(userState.getRepository());
//		File[] files = folder.listFiles();
//		String retour = "";
//		for(File file : files){
//			retour+="type="+ getType(file)+"; size="+file.getTotalSpace()+"; modify="+file.lastModified()+"; "+file.getName()+"\n";
//		}
//		try {
//			PrintWriter writer = new PrintWriter(dataSocket.getOutputStream());
//			Connexion.write(writer, retour);
//			writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return;
	}

	@Override
	public FtpReply getStartCode() {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_125_CONNEXION_ETABLIE_TRANSFERT_DEMARRE, 
				"Connexion �tablie");
	}

	@Override
	public FtpReply getEndCode() {
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_226_FERMETURE_CONNEXION_DONNEES,
				"Fermeture de la connexion");
	}

}
