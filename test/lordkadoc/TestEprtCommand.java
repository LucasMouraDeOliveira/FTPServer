package lordkadoc;

import org.junit.Assert;

import command.EprtCommand;
import server.FtpReply;

public class TestEprtCommand extends TestConnected{
	
	@Override
	public void init() {
		super.init();
		this.command = new EprtCommand();
	}

	@Override
	public void testCodeCasNominal() {
		FtpReply reply = this.command.execute("|1|132.235.1.2|6275|", userState, server);
		Assert.assertEquals("200", reply.getCode());
		Assert.assertEquals("132.235.1.2", userState.getDataAddress().getHostAddress());
		Assert.assertEquals(6275, userState.getDataPort().intValue());
	}

}
