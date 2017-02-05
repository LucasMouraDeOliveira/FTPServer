package utilitary;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import server.FTPLauncher;

public class UserState {
	
	private String user;
	private boolean isLogged;
	private String repository;
	
	private Integer controlPort;
	private Socket controlSocket;
	
	private Integer dataPort;
	private Socket dataSocket;
	
	private File renameFile;
	
	public UserState(Socket socket) {
		this.controlPort = socket.getPort();
		this.controlSocket = socket;
		this.dataPort = FTPLauncher.FTP_PORT_FILE;
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
	
	public Integer getControlPort() {
		return controlPort;
	}
	
	public Socket getControlSocket() {
		return controlSocket;
	}

	public void connectUser() {
		if(this.dataPort == null)
			this.dataPort = this.controlPort-1;
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					ServerSocket socket = new ServerSocket(dataPort);
					dataSocket = socket.accept();
					System.out.println("socket connectee");
					socket.close();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(r).start();
	}
	
	public void writeString(String message){
		if(this.dataSocket == null)
			return;
			Connexion.write(dataSocket, message);
	}

	public void setRenameFile(File f) {
		this.renameFile = f;
	}
	
	public File getRenameFile() {
		return renameFile;
	}
}
