package command;

import java.io.IOException;
import java.net.ServerSocket;

import server.FTPLauncher;

public class Serverbidon extends Thread {
	
	ServerSocket server;
	private boolean continu = true;
	
	public Serverbidon() {
		try {
			server = new ServerSocket(FTPLauncher.FTP_PORT_COMMAND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			server.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(continu){}
	};
	
	public void close(){
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		continu = false;
	}
	
	
}
