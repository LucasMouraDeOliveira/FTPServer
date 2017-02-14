package lordkadoc;

import org.junit.Assert;

import command.PwdCommand;
import server.FtpReply;

public class TestPwdCommand extends TestConnected{

	@Override
	public void init() {
		super.init();
		this.command = new PwdCommand();
	}

	@Override
	public void testCodeCasNominal() {
		FtpReply reply = this.command.execute(null, userState, server);
		Assert.assertEquals("257", reply.getCode());
	}
}
