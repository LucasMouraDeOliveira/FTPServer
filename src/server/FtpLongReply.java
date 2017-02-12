package server;

import java.util.ArrayList;
import java.util.List;

/**
 * Extension de la classe FTPReply qui permet d'envoyer des réponses multi-ligne
 * 
 * @author Lucas Moura de Oliveira
 */
public class FtpLongReply extends FtpReply {
	
	private List<String> lines;
	
	/**
	 * Initialise la FTPLongReply
	 */
	public FtpLongReply(){
		this.lines = new ArrayList<String>();
	}
	
	/**
	 * Ajoute une ligne à la fin de la réponse, et suffixe la ligne par "\r\n"
	 * 
	 * @param line la ligne à ajouter
	 */
	public void addLine(String line){
		this.lines.add(line+"\r\n");
	}
	
	@Override
	public String toString(){
		String retour = super.toString();
		for(String line : lines){
			retour+=line;
		}
		return retour;
	}

}
