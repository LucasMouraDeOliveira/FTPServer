package command;

import java.net.UnknownHostException;

import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class EprtCommand extends LoggedCommand {

	@Override
	public String executeLogged(String data, UserState etat) {
		String[] datas = data.split("\\|");
		try {
			etat.setDataAddress(datas[1], datas[2]);
			etat.setDataPort(Integer.valueOf(datas[3]));
			etat.setActive(true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_500_ERREUR_INTERNE, 
					"Erreur lors de la résolution de l'adresse hôte");
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES, 
				"Port mis à jour avec succès");
	}

}
