package lordkadoc;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import command.PassCommand;
import command.UserCommand;
import errors.BadPropertyException;
import errors.NullPropertyException;
import junit.framework.Assert;
import server.FtpReply;
import server.FtpServer;
import user.UserState;

public class TestPassCommand extends TestCommand{
	
	@Before
	public void init() {
		this.command = new PassCommand();
		try {
			this.server = new FtpServer("2021", "2022", "./root");
		} catch (BadPropertyException | IOException | NullPropertyException e) {
			e.printStackTrace();
		}
		this.userState = new UserState();
	}

	@Override
	public void testCodeCasNominal() {
		new UserCommand().execute("lucas", userState, server);
		FtpReply reply = this.command.execute("l", userState, server);
		Assert.assertEquals("230", reply.getCode());
	}
	
	@Test
	public void testCodePasswordIncorrect() {
		new UserCommand().execute("lucas", userState, server);
		FtpReply reply = this.command.execute("mauvais_mot_de_passe", userState, server);
		Assert.assertEquals("530", reply.getCode());
	}
	
	@Test
	public void testCodeUserCommandePasEncoreEnvoyee() {
		FtpReply reply = this.command.execute("l", userState, server);
		Assert.assertEquals("503", reply.getCode());
	}

}
