package utilitary;

import java.text.Normalizer;

public class FtpStatusCodes {
	
	public final static String CODE_125_CONNEXION_ETABLIE_TRANSFERT_DEMARRE = "125";
	
	public final static String CODE_150_STATUS_FICHIER_OK_OUVERTURE_EN_COURS = "150";
	
	public final static String CODE_200_ACTION_REALISEE_AVEC_SUCCES = "200";
	
	public final static String CODE_202_COMMANDE_NON_PRISE_EN_CHARGE = "202";
	
	public final static String CODE_220_SERVICE_PRET_POUR_UN_NOUVEL_UTILISATEUR = "220";
	
	public final static String CODE_221_DECONNEXION = "221";
	
	public final static String CODE_229_MODE_PASSIF_ETENDU_ACTIVE = "229";
	
	public final static String CODE_230_UTILISATEUR_CONNECTE = "230";
	
	public final static String CODE_250_ACTION_SUR_LE_FICHIER_REALISEE_AVEC_SUCCES = "250";
	
	public final static String CODE_404_NOT_FOUND = "404";
	
	public final static String CODE_500_ERREUR_INTERNE = "500";
	
	public final static String CODE_501_ERREUR_DE_SYNTAXE = "501";
	
	public final static String CODE_502_COMMANDE_NON_IMPLEMENTEE = "502";
	
	public final static String CODE_530_PAS_CONNECTE = "530";
	
	public final static String CODE_550_ACTION_NON_REALISEE = "550";
	
	public static String buildReply(String FTPStatusCode, String message){
		return buildReply(FTPStatusCode, message, true);
	}
	
	public static String buildReply(String FTPStatusCode, String message, boolean removeAccents){
		if(removeAccents){
			message = stripAccents(message);
		}
		return FTPStatusCode + " " + message;
	}
	
	public static String stripAccents(String s) {
	    s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    return s;
	}
	
}
