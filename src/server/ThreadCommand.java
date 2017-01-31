package server;

import java.net.Socket;

import command.CommandPool;
import utilitary.Connexion;
import utilitary.UserState;

public class ThreadCommand extends Thread {
	
	protected Socket socket;
	protected UserState state;
	
	public ThreadCommand(Socket socket) {
		this.socket = socket;
		this.state = new UserState(socket);
	}
	
	@Override
	public void run() {
		while(isSocketOpen()){
			String message = receiveCommand();
			System.out.println("Message reçu : " + message);
			String[] data = message.split(" ", 2);
			if(data.length == 0){
				Connexion.write(socket, "400 - il manque pas un truc là ?");
			}else if(data.length == 1){
				Connexion.write(socket,""+(interpreteCommand(data[0], null)));
			} else {
				Connexion.write(socket,""+(interpreteCommand(data[0], data[1])));
			}
		}
	}
	
	public String receiveCommand(){
		return Connexion.read(socket);
	}
	
	public String interpreteCommand(String command, String data){
		return CommandPool.getInstance().handle(command, data, this.state);
	}
	
	public boolean isSocketOpen() {
		return true;
	}

}
