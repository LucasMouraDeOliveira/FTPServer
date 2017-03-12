package command;

import org.junit.Assert;
import org.junit.Test;

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
		reply = this.command.execute("EPRT |2|::1|5282|", userState, server);
		Assert.assertEquals("200", reply.getCode());
		Assert.assertEquals("0:0:0:0:0:0:0:1", userState.getDataAddress().getHostAddress());
		Assert.assertEquals(5282, userState.getDataPort().intValue());
	}
	
	@Test
	public void testCodeErreurPortInvalide(){
		this.command.execute("|1|132.235.1.2|6275|", userState, server);
		Integer port = userState.getDataPort();
		FtpReply reply = this.command.execute("|1|132.235.1.2|bonjour|", userState, server);
		Assert.assertEquals("501", reply.getCode());
		Assert.assertEquals(port, userState.getDataPort());
	}
	
	@Test
	public void testCodeErreurAdresseInvalide(){
		this.command.execute("|1|132.235.1.2|6275|", userState, server);
		Integer port = userState.getDataPort();
		String adresse = userState.getDataAddress().getHostAddress();
		FtpReply reply = this.command.execute("|1|999.999.999.999|5282|", userState, server);
		Assert.assertEquals("500", reply.getCode());
		Assert.assertEquals(port, userState.getDataPort());
		Assert.assertEquals(adresse, userState.getDataAddress().getHostAddress());
	}
	
	@Test
	public void testCodeErreurPortHorsDesBornes() {
		this.command.execute("|1|132.235.1.2|6275|", userState, server);
		Integer port = userState.getDataPort();
		FtpReply reply = this.command.execute("|1|132.235.1.2|1000|", userState, server);
		Assert.assertEquals("501", reply.getCode());
		Assert.assertEquals(port, userState.getDataPort());
		reply = this.command.execute("|1|132.235.1.2|100000|", userState, server);
		Assert.assertEquals("501", reply.getCode());
		Assert.assertEquals(port, userState.getDataPort());
	}

}
