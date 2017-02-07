package command;

import java.net.Socket;

import server.FtpReply;
import utilitary.UserState;

public interface DataCommandExecutor {
	
	public void executeThread(String data, UserState userState, Socket socket);
	
	public FtpReply getStartCode();
	
	public FtpReply getEndCode();
	
}
