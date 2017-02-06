package command;

import java.util.HashMap;

import utilitary.UserState;

public class CommandPool {
	
	protected HashMap<String, Command> commands;
	
	public static final CommandPool instance = new CommandPool();
	
	private CommandPool() {
		this.commands = new HashMap<String, Command>();
		this.initCommands();
	}
	
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
	}

	public static CommandPool getInstance() {
		return instance;
	}
	
	public String handle(String command, String data, UserState etat){
		Command c = commands.get(command);
		if(c != null){
			return c.execute(data, etat);
		}
		return "404 - Commande inconnue dans la pool";
	}

}
