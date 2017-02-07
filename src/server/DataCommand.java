package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import command.DataCommandExecutor;
import utilitary.Connexion;
import utilitary.UserState;

public class DataCommand extends Thread{
	
	protected String data;
	
	protected UserState userState;
	
	protected Socket dataSocket;
	
	protected ServerSocket serverSocket;

	private DataCommandExecutor dataCommandExecutor;
	
	public DataCommand(String data, UserState userState, DataCommandExecutor dataCommandExecutor){
		this.data = data;
		this.userState = userState;
		this.dataCommandExecutor  = dataCommandExecutor;
	}
	
	@Override
	public void run(){
		this.connectSocket();
		if(this.dataSocket.isConnected() && !this.dataSocket.isClosed()){
			dataCommandExecutor.executeThread(this.data, this.userState, this.dataSocket);
		}
		this.closeSocket();
	}
	
	public void openActiveSocket(){
		this.dataSocket = new Socket();
		try {
			this.dataSocket.connect(new InetSocketAddress(userState.getDataAddress(), userState.getDataPort()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void openPassiveSocket() {
		try {
			this.serverSocket = new ServerSocket(userState.getDataPort());
			this.dataSocket = serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void connectSocket(){
		if(userState.isActive()){
			this.openActiveSocket();
		}else{
			this.openPassiveSocket();
		}
		Connexion.write(userState.getControlSocket(), dataCommandExecutor.getStartCode());
	}
	
	public void closeSocket() {
		Connexion.write(userState.getControlSocket(), dataCommandExecutor.getEndCode());
		if(this.serverSocket != null){
			try {
				this.serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(this.dataSocket != null){
			try {
				this.dataSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
