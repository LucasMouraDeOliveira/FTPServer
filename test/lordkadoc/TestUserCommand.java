package lordkadoc;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;

import command.UserCommand;
import errors.BadPropertyException;
import errors.NullPropertyException;
import server.FtpReply;
import server.FtpServer;
import user.UserState;

public class TestUserCommand extends TestCommand {
	
	@Before
	public void init() {
		this.command = new UserCommand();
		try {
			this.server = new FtpServer("2021", "2022", "./root");
		} catch (BadPropertyException | IOException | NullPropertyException e) {
			e.printStackTrace();
		}
		this.userState = new UserState();
	}
	
	@Override
	public void testCodeCasNominal() {
		//On appelle la commande avec les paramètres adéquats
		FtpReply reply = this.command.execute("lucas", userState, server);
		//On vérifie que la FtpReply est valide
		Assert.assertEquals("331", reply.getCode());
	}
	
}
