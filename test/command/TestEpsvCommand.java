package command;

import org.junit.Assert;

import command.EpsvCommand;
import server.FtpReply;

public class TestEpsvCommand extends TestConnected{
	
	@Override
	public void init(){
		super.init();
		this.command = new EpsvCommand();
	}

	@Override
	public void testCodeCasNominal() {
		FtpReply reply = this.command.execute(null, userState, server);
		Assert.assertEquals("229", reply.getCode());
		
	}

}
