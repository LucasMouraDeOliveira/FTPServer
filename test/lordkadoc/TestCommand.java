package lordkadoc;

import org.junit.Before;
import org.junit.Test;

import command.Command;
import server.FtpServer;
import user.UserState;

public abstract class TestCommand {
	
	protected Command command;
	
	protected FtpServer server;
	
	protected UserState userState;
	
	@Before
	public abstract void init();

	@Test
	public abstract void testCodeCasNominal();

}
