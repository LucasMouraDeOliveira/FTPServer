package command;

import java.io.IOException;

import org.junit.Test;

import command.Command;
import command.LoggedCommand;
import command.PassCommand;
import command.UserCommand;
import errors.BadPropertyException;
import errors.NullPropertyException;
import junit.framework.Assert;
import server.FtpReply;
import server.FtpServer;
import user.UserState;
import utility.FtpStatusCodes;

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
	
	@Test
	public void testCodeErreurNonConnecte(){
		UserState notConnectedUserState = new UserState();
		FtpReply reply = new LoggedCommand() {
			@Override
			public FtpReply executeLogged(String data, UserState userState, FtpServer server) {
				return FtpStatusCodes.buildReply(FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES, 
						"ok");
			}
		}.execute(null, notConnectedUserState, server);
		Assert.assertEquals("530", reply.getCode());
	}
	
}
