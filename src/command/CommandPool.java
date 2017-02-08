package command;

import java.util.HashMap;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

/**
 * Implémentation du pattern Command qui permet de stocker la liste des commandes FTP reconnues par l'application.
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class CommandPool {
	
	protected HashMap<String, Command> commands;
	
	public static final CommandPool instance = new CommandPool();
	
	/**
	 * Initialise la commandPool
	 */
	private CommandPool() {
		this.commands = new HashMap<String, Command>();
		this.initCommands();
	}
	
	/**
	 * Initialise la liste des commandes reconnues en associant un code (exemple : USER) à une implémentation de la commande
	 */
	private void initCommands() {
		this.commands.put("USER", new UserCommand());
		this.commands.put("PASS", new PassCommand());
		this.commands.put("OPTS", new OptsCommand());
		this.commands.put("EPRT", new EprtCommand());
		this.commands.put("PORT", new PortCommand());
		this.commands.put("EPSV", new EpsvCommand());
		this.commands.put("PASV", new PasvCommand());
		this.commands.put("NLST", new NlstCommand());
		this.commands.put("FEAT", new FeatCommand());
		this.commands.put("PWD", new PwdCommand());
		this.commands.put("XPWD", new PwdCommand());
		this.commands.put("LIST", new ListCommand());
		this.commands.put("TYPE", new TypeCommand());
		this.commands.put("CWD", new CwdCommand());
		this.commands.put("MKD", new MkdCommand());
		this.commands.put("XMKD", new MkdCommand());
		this.commands.put("RNFR", new RnfrCommand());
		this.commands.put("RNTO", new RntoCommand());
		this.commands.put("RMD", new RmdCommand());
		this.commands.put("XRMD", new RmdCommand());
		this.commands.put("RETR", new RetrCommand());
		this.commands.put("MLST", new MlstCommand());
		this.commands.put("STOR", new StorCommand());
	}
	
	/**
	 * Retourne l'instance unique de la Pool de commandes
	 * @return
	 */
	public static CommandPool getInstance() {
		return instance;
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
	public FtpReply handle(String command, String data, UserState userState){
		Command c = commands.get(command);
		if(c != null){
			return c.execute(data, userState);
		}
		return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_404_NOT_FOUND, 
				"Commande inconnue");
	}

}
