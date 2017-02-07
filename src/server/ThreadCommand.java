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
		Connexion.write(state.getWriter(), "220 - bienvenue sur le serveur FTP");
		while(isSocketOpen()){
			String message = receiveCommand();
			if(message == null){
				break;
			}
			String[] data = message.split(" ", 2);
			FtpReply reply;
			if(data.length == 1){
				reply = interpreteCommand(data[0], null);
			} else {
				reply = interpreteCommand(data[0], data[1]);
			}
			if(!reply.isEmpty()){
				Connexion.write(state.getWriter(), reply.toString());
			}
		}
	}
	
	public String receiveCommand(){
		return Connexion.read(state.getReader());
	}
	
	public FtpReply interpreteCommand(String command, String data){
		return CommandPool.getInstance().handle(command, data, this.state);
	}
	
	public boolean isSocketOpen() {
		return true;
	}

}
