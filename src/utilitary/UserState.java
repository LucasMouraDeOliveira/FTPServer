package utilitary;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class UserState {
	
	private String user;
	private boolean isLogged;
	private String repository;
	
	private Integer controlPort;
	private Socket controlSocket;
	
	private Integer dataPort;
	private InetAddress dataAddress;
	
	private boolean active;
	
	private File renameFile;
	
	public UserState(Socket socket) {
		this.controlPort = socket.getPort();
		this.controlSocket = socket;
		this.active = true;
	}
	
	public String getRepository() {
		return repository;
	}
	
	public String getUser() {
		return user;
	}
	
	public boolean isLogged() {
		return isLogged;
	}
	
	public void setRepository(String repository) {
		this.repository = repository;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}
	
	public Integer getDataPort() {
		return dataPort;
	}
	
	public void setDataPort(Integer dataPort) {
		this.dataPort = dataPort;
	}
	
	public InetAddress getDataAddress() {
		return dataAddress;
	}
	
	public void setDataAddress(String mode, String address) throws UnknownHostException{
		if("1".equals(mode)){
			dataAddress = Inet4Address.getByName(address);
		}else if("2".equals(mode)){
			dataAddress = Inet6Address.getByName(address);
		}else{
			//TODO erreur : mode inconnu (retourner 500)
		}
	}
	
	public Integer getControlPort() {
		return controlPort;
	}
	
	public Socket getControlSocket() {
		return controlSocket;
	}
	
	public void transferCompleted() {
		Connexion.write(controlSocket, "226 Transfer completed");
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setRenameFile(File f) {
		this.renameFile = f;
	}
	
	public File getRenameFile() {
		return renameFile;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
}
