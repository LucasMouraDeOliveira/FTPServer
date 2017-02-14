package lordkadoc;

import java.io.IOException;

import command.Command;
import command.PassCommand;
import command.UserCommand;
import errors.BadPropertyException;
import errors.NullPropertyException;
import server.FtpServer;
import user.UserState;

public abstract class TestConnected extends TestCommand{

	@Override
	public void init(){
		try {
			server = new FtpServer("2021", "2022", "./root");
		} catch (BadPropertyException | IOException | NullPropertyException e) {
		}
		this.userState = new UserState();
		Command usecmd = new UserCommand();
		Command passcmd = new PassCommand();
		usecmd.execute("admin", userState, server);
		passcmd.execute("admin", userState, server);
	}
	
}
