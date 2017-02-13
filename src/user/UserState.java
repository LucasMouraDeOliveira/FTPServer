package user;

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
 * Classe contenant les informations relatives � une session utilisateur
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
	
	public UserState() {}
	
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
	 * @return le r�pertoire courant de l'utilisateur sur le serveur FTP
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
	 * @return vrai si l'utilisateur est connect� et authentifi�
	 */
	public boolean isLogged() {
		return isLogged;
	}
	
	/**
	 * Met � jour le r�pertoire courant de l'utilisateur sur le serveur FTP
	 * 
	 * @param repository le nouveau r�pertoire
	 */
	public void setRepository(String repository) {
		this.repository = repository;
	}
	
	/**
	 * Met � jour le login de l'utilisateur
	 * 
	 * @param user le login de l'utilisateur
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * D�finit l'�tat de connexion/authentification de l'utilisateur
	 * 
	 * @param isLogged vrai si l'utilisateur est authentifi�, faux sinon
	 */
	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}
	
	/**
	 * @return le port de connexion � la socket de donn�es pour l'utilisateur
	 */
	public Integer getDataPort() {
		return dataPort;
	}
	
	/**
	 * Met � jour le port de connexion � la socket de donn�es pour l'utilisateur
	 * 
	 * @param dataPort le port de connexion
	 */
	public void setDataPort(Integer dataPort) {
		this.dataPort = dataPort;
	}
	
	/**
	 * @return l'adresse de connexion � la socket de donn�es pour l'utilisateur
	 */
	public InetAddress getDataAddress() {
		return dataAddress;
	}
	
	/**
	 * Met � jour l'adresse de connexion � la socket de donn�ers pour l'utilisateur
	 * 
	 * @param mode le type de l'adresse (1 = IPv4, 2 = IPv6)
	 * @param address l'adresse
	 * 
	 * @throws UnknownHostException si l'adresse pass�e en param�tre est incorrect
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
	 * @return le mode de connexion � la socket de donn�es (vrai pour actif, faux pour passif)
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Met en m�moire le nom de fichier � renommer pour la commande RNFR
	 * @param f le fichier � renommer
	 */
	public void setRenameFile(File f) {
		this.renameFile = f;
	}
	
	/**
	 * @return le nom du fichier � renommer
	 */
	public File getRenameFile() {
		return renameFile;
	}
	
	/**
	 * Met � jour le mode de connexion � la socket de donn�es
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
