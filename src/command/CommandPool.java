package command;

import java.util.HashMap;

import server.FtpReply;
import server.FtpServer;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

/**
 * Implémentation du pattern Command qui permet de stocker la liste des commandes FTP reconnues par l'application.
 * Les commandes sont stockées dans une map qui associe à un nom de commande (ex : USER) une implémentation de commande.
 * Dans le cas où une commande possède un ou plusieurs alias (comme MKD et XMKD), on associe deux noms au même objet commande.
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class CommandPool {
	
	protected HashMap<String, Command> commands;
	
	
	/**
	 * Initialise la commandPool
	 */
	public CommandPool() {
		this.commands = new HashMap<String, Command>();
		this.initCommands();
	}
	
	/**
	 * Initialise la liste des commandes reconnues en associant un code (exemple : USER) à une implémentation de la commande
	 */
	private void initCommands() {
		
		/* COMMANDES SANS ALIAS */
		this.commands.put("CWD", new CwdCommand());
		this.commands.put("EPRT", new EprtCommand());
		this.commands.put("EPSV", new EpsvCommand());
		this.commands.put("FEAT", new FeatCommand());
		this.commands.put("LIST", new ListCommand());
		this.commands.put("MLST", new MlstCommand());
		this.commands.put("NLST", new NlstCommand());
		this.commands.put("OPTS", new OptsCommand());
		this.commands.put("PASS", new PassCommand());
		this.commands.put("PASV", new PasvCommand());
		this.commands.put("PORT", new PortCommand());
		this.commands.put("RETR", new RetrCommand());
		this.commands.put("RNFR", new RnfrCommand());
		this.commands.put("RNTO", new RntoCommand());
		this.commands.put("STOR", new StorCommand());
		this.commands.put("TYPE", new TypeCommand());
		this.commands.put("USER", new UserCommand());
		
		/* COMMANDES AVEC ALIAS */
		
		MkdCommand mkdCommand = new MkdCommand();
		this.commands.put("MKD", mkdCommand);
		this.commands.put("XMKD", mkdCommand);
		
		PwdCommand pwdCommand = new PwdCommand();
		this.commands.put("PWD", pwdCommand);
		this.commands.put("XPWD", pwdCommand);
		
		RmdCommand rmdCommand = new RmdCommand();
		this.commands.put("RMD", rmdCommand);
		this.commands.put("XRMD", rmdCommand);
	}
	
	/**
	 * Execute une commande et renvoie un code de retour
	 * 
	 * @param command le code de la commande reçue
	 * @param data les paramètres de la commande
	 * @param userState l'état de l'utilisateur, qui contient les informations relatives à sa session
	 * 
	 * @return un code qui correspond au code de retour de la commande, 404 si la commande n'est pas reconnue par l'application
	 */
	public FtpReply handle(String command, String data, UserState userState, FtpServer server){
		//On récupère la commande correspondante dans la map
		Command c = commands.get(command);
		//Si celle ci n'est pas null (la commande existe), on l'exécute et on retourne sa réponse
		if(c != null){
			return c.execute(data, userState, server);
		}
		//Sinon si la commande n'existe pas, on retourne un code d'erreur 404
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_404_NOT_FOUND, 
				"Commande inconnue");
	}

}
