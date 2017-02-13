package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.Properties;

import command.CommandPool;
import errors.BadPropertyException;
import errors.NoPropertyFileException;
import errors.NullPropertyException;
import utility.UserHandler;

/**
 * Lance un serveur FTP sur un port spécifié soit par un fichier de configuration, 
 * soit par les arguments si lancé en ligne de commande
 * 
 * @author Lucas Moura de Oliveira
 */
public class FtpServer {
	
	//Port pour la socket de données
	protected int dataPort;
	
	//Port par défaut pour la socket de commande
	protected int commandPort;
	
	//Dossier root par défaut du serveur FTP
	protected UserHandler userHandler;
	
	//La socket serveur
	protected ServerSocket serverSocket;
	
	//La pool de commandes
	protected CommandPool commandPool;
	
	/**
	 * Démarre le meilleur serveur FTP du monde
	 *
	 * @throws NoPropertyFileException si le fichier de configuration est introuvable
	 * @throws BadPropertyException si l'une des propriétés du fichier de configuration ou l'un des arguments en ligne de commande est invalide
	 * @throws IOException si la serverSocket n'a pas pu s'ouvrir
	 */
	public FtpServer() throws NoPropertyFileException, BadPropertyException, IOException{
		this.autoConfigure();
		this.commandPool = new CommandPool();
		this.serverSocket = new ServerSocket(commandPort);
		System.out.println("Serveur ouvert sur le port " + commandPort + " avec la configuration standard");
	}
	
	public FtpServer(String defaultCommandPort, String defaultDataPort, String homeDir) throws BadPropertyException, NullPropertyException, IOException {
		this.configure(defaultCommandPort, defaultDataPort, homeDir);
		this.commandPool = new CommandPool();
		this.serverSocket = new ServerSocket(commandPort);
		System.out.println("Serveur ouvert sur le port " + commandPort + " avec la configuration personnalisée");
	}
	
	/**
	 * Lit le fichier de configuration du serveur
	 */
	private void autoConfigure() throws NoPropertyFileException, BadPropertyException{
		Properties prop = new Properties();
		try {
			InputStream input = new FileInputStream("configuration.properties");
			prop.load(input);
		} catch (IOException e) {
			throw new NoPropertyFileException("configuration.properties");
		}
		this.commandPort = Integer.parseInt(prop.getProperty("FTP_PORT_COMMAND"));
		this.dataPort = Integer.parseInt(prop.getProperty("FTP_PORT_FILE"));
		try {
			this.userHandler = new UserHandler(prop.getProperty("ROOT"));
		} catch (IOException e) {
			throw new BadPropertyException("ROOT");
		}
	}
	
	private void configure(String defaultCommandPort, String defaultDataPort, String homeDir) throws NullPropertyException, BadPropertyException {
		if(defaultCommandPort == null)
			throw new BadPropertyException("Port de commande");
		if(defaultDataPort == null)
			throw new BadPropertyException("Port de données");
		if(homeDir == null)
			throw new BadPropertyException("Dossier root");
		try{
			this.commandPort = Integer.valueOf(defaultCommandPort); 
		} catch(NumberFormatException e){
			throw new BadPropertyException("Port de commande");
		}
		try{
			this.dataPort = Integer.valueOf(defaultDataPort); 
		} catch(NumberFormatException e){
			throw new BadPropertyException("Port de données");
		}
		try{
			this.userHandler = new UserHandler(homeDir);
		} catch(IOException e){
			throw new BadPropertyException("Dossier root");
		}
	}

	/**
	 * Démarre l'écoute de connexion sur le port de commande
	 * 
	 * @throws IOException si une IOException a lieu lors de l'écoute de connexion
	 */
	public void startServer() throws IOException{
		while(true){
			new ThreadCommand(this, serverSocket.accept(), commandPool).start();
		} 
	}
	
	public int getCommandPort() {
		return commandPort;
	}
	
	public int getDataPort() {
		return dataPort;
	}
	
	public UserHandler getUserHandler() {
		return userHandler;
	}

}
