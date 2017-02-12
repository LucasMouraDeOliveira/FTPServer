package utilitary;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Classe contenant les informations relatives à une session utilisateur
 * 
 * @author Lucas Moura de Oliveira
 */
public class UserState {
	
	private String user;
	private boolean isLogged;
	private String repository;
	
	private Integer controlPort;
	private Socket controlSocket;
	
	//IO Control socket
	private BufferedReader reader;
	private PrintWriter writer;
	
	private Integer dataPort;
	private InetAddress dataAddress;
	
	private boolean active;
	
	private File renameFile;
	
	public UserState(Socket socket) {
		this.controlPort = socket.getPort();
		this.controlSocket = socket;
		this.active = true;
		try {
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.writer = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return le répertoire courant de l'utilisateur sur le serveur FTP
	 */
	public String getRepository() {
		return repository;
	}
	
	/**
	 * @return le login de l'utilisateur
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * @return vrai si l'utilisateur est connecté et authentifié
	 */
	public boolean isLogged() {
		return isLogged;
	}
	
	/**
	 * Met à jour le répertoire courant de l'utilisateur sur le serveur FTP
	 * 
	 * @param repository le nouveau répertoire
	 */
	public void setRepository(String repository) {
		this.repository = repository;
	}
	
	/**
	 * Met à jour le login de l'utilisateur
	 * 
	 * @param user le login de l'utilisateur
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * Définit l'état de connexion/authentification de l'utilisateur
	 * 
	 * @param isLogged vrai si l'utilisateur est authentifié, faux sinon
	 */
	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}
	
	/**
	 * @return le port de connexion à la socket de données pour l'utilisateur
	 */
	public Integer getDataPort() {
		return dataPort;
	}
	
	/**
	 * Met à jour le port de connexion à la socket de données pour l'utilisateur
	 * 
	 * @param dataPort le port de connexion
	 */
	public void setDataPort(Integer dataPort) {
		this.dataPort = dataPort;
	}
	
	/**
	 * @return l'adresse de connexion à la socket de données pour l'utilisateur
	 */
	public InetAddress getDataAddress() {
		return dataAddress;
	}
	
	/**
	 * Met à jour l'adresse de connexion à la socket de donnéers pour l'utilisateur
	 * 
	 * @param mode le type de l'adresse (1 = IPv4, 2 = IPv6)
	 * @param address l'adresse
	 * 
	 * @throws UnknownHostException si l'adresse passée en paramètre est incorrect
	 */
	public void setDataAddress(String mode, String address) throws UnknownHostException{
		if("1".equals(mode)){
			dataAddress = Inet4Address.getByName(address);
		}else if("2".equals(mode)){
			dataAddress = Inet6Address.getByName(address);
		}else{
			//TODO erreur : mode inconnu (retourner 500)
		}
	}
	
	/**
	 * @return le port de la socket de commande
	 */
	public Integer getControlPort() {
		return controlPort;
	}
	
	/**
	 * @return la socket de commande
	 */
	public Socket getControlSocket() {
		return controlSocket;
	}
	
	/**
	 * @return le mode de connexion à la socket de données (vrai pour actif, faux pour passif)
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Met en mémoire le nom de fichier à renommer pour la commande RNFR
	 * @param le nom du fichier à renommer
	 */
	public void setRenameFile(File f) {
		this.renameFile = f;
	}
	
	/**
	 * @return le nom du fichier à renommer
	 */
	public File getRenameFile() {
		return renameFile;
	}
	
	/**
	 * Met à jour le mode de connexion à la socket de données
	 * 
	 * @param active vrai pour mode actif, faux pour mode passif
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * @return le writer de la socket de commande
	 */
	public PrintWriter getWriter() {
		return writer;
	}
	
	/**
	 * @return le reader de la socket de commande
	 */
	public BufferedReader getReader() {
		return reader;
	}
}
