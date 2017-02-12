package utilitary;

import java.text.Normalizer;

import server.FtpLongReply;
import server.FtpReply;

/**
 * Classe utilitaire listant les codes de retours utilisés par le serveur
 * 
 * @author Lucas Moura de Oliveira
 */
public class FtpStatusCodes {
	
	public final static String CODE_125_CONNEXION_ETABLIE_TRANSFERT_DEMARRE = "125";
	
	public final static String CODE_150_STATUS_FICHIER_OK_OUVERTURE_EN_COURS = "150";
	
	public final static String CODE_200_ACTION_REALISEE_AVEC_SUCCES = "200";
	
	public final static String CODE_202_COMMANDE_NON_PRISE_EN_CHARGE = "202";
	
	public final static String CODE_211_FEATURES = "211";

	public final static String CODE_220_SERVICE_PRET_POUR_UN_NOUVEL_UTILISATEUR = "220";
	
	public final static String CODE_221_DECONNEXION = "221";
	
	public final static String CODE_226_FERMETURE_CONNEXION_DONNEES = "226";
	
	public final static String CODE_227_MODE_PASSIF_ACTIVE = "227";
	
	public final static String CODE_229_MODE_PASSIF_ETENDU_ACTIVE = "229";
	
	public final static String CODE_230_UTILISATEUR_CONNECTE = "230";
	
	public final static String CODE_250_ACTION_SUR_LE_FICHIER_REALISEE_AVEC_SUCCES = "250";
	
	public final static String CODE_257_CHEMIN_D_ACCES_RETOURNE = "257";
	
	public final static String CODE_331_USER_OK_NEED_PASSWORD = "331";
	
	public final static String CODE_350_EN_ATTENTE_D_INFORMATION_SUPPLEMENTAIRE = "350";
	
	public final static String CODE_404_NOT_FOUND = "404";
	
	public final static String CODE_500_ERREUR_INTERNE = "500";
	
	public final static String CODE_501_ERREUR_DE_SYNTAXE = "501";
	
	public final static String CODE_502_COMMANDE_NON_IMPLEMENTEE = "502";
	
	public final static String CODE_503_MAUVAIS_ORDRE_DE_COMMANDES = "503";
	
	public final static String CODE_530_PAS_CONNECTE = "530";
	
	public final static String CODE_550_ACTION_NON_REALISEE = "550";
	
	/**
	 * Construit une FTPReply en fonction d'un code de retour et d'un message
	 * 
	 * @param FTPStatusCode le code de retour
	 * @param message le message de retour
	 * 
	 * @return une réponse FTP
	 */
	public static FtpReply buildReply(String FTPStatusCode, String message){
		return buildReply(FTPStatusCode, message, true);
	}
	
	/**
	 * Construit une FTPReply en fonction d'un code de retour et d'un message
	 * 
	 * @param FTPStatusCode le code de retour
	 * @param message le message de retour
	 * @param removeAccents si vrai, on retire les accents du message (évite les erreurs d'encodage)
	 * 
	 * @return une réponse FTP
	 */
	public static FtpReply buildReply(String FTPStatusCode, String message, boolean removeAccents){
		if(removeAccents){
			message = stripAccents(message);
		}
		FtpReply reply = new FtpReply();
		reply.setCode(FTPStatusCode);
		reply.setMessage(message);
		return reply;
	}
	
	/**
	 * Construit une FTPLongReply (multi-ligne) en fonction d'un code de retour et d'un message
	 * 
	 * @param FTPStatusCode le code de retour
	 * @param message le message de retour
	 * @return une réponse FTP multi-ligne
	 */
	public static FtpLongReply buildLongReply(String FTPStatusCode, String message){
		FtpLongReply longReply = new FtpLongReply();
		longReply.setCode(FTPStatusCode);
		longReply.setMessage(stripAccents(message));
		return longReply;
	}
	
	/**
	 * Retire les accents d'une chaine de caractères
	 * 
	 * @param s la chaine de caractères
	 * 
	 * @return la chaine de caractères sans accents
	 */
	public static String stripAccents(String s) {
	    s = Normalizer.normalize(s, Normalizer.Form.NFD);
	    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
	    return s;
	}
	
}
