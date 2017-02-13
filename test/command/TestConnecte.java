package command;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;

import utility.UserState;

public class TestConnecte {

	protected UserState userState;
	protected Serverbidon sb;
	protected Socket socket;
	
	@Before
	public void init(){
		sb = new Serverbidon();
		sb.start();
		socket =new Socket();
		try {
			socket.connect(new InetSocketAddress("localhost", FTPLauncher.FTP_PORT_COMMAND));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.userState = new UserState(socket);
		Command usecmd = new UserCommand();
		Command passcmd = new PassCommand();
		usecmd.execute("admin", userState);
		passcmd.execute("admin", userState);
	}

	@After
	public void close(){
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sb.close();
	}
}
