package command;

import java.net.Socket;

import utilitary.UserState;

public interface DataCommandExecutor {
	
	public void executeThread(String data, UserState userState, Socket socket);
	
}
