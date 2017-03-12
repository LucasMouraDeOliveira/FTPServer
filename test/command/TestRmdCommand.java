package command;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import command.MkdCommand;
import command.RmdCommand;
import server.FtpReply;

public class TestRmdCommand extends TestConnected{

	@Override
	public void init() {
		super.init();
		this.command = new RmdCommand();
	}

	@Override
	public void testCodeCasNominal() {
		new MkdCommand().execute("testMkd", userState, server);
		FtpReply ftpReply = this.command.execute("testMkd", userState, server);
		Assert.assertEquals("200", ftpReply.getCode());
		Assert.assertFalse(new File(userState.getRepository() + "/testMkd").exists());
	}
	
	@Test
	public void testCodeErreurFichierInexistant() {
		//on ne crée pas le dossier
		FtpReply ftpReply = this.command.execute("testMkd", userState, server);
		Assert.assertEquals("550", ftpReply.getCode());
		Assert.assertFalse(new File(userState.getRepository() + "/testMkd").exists());
	}
	
	@Test
	public void testCodeErreurPasLeDroitAcces() {
		new File("../testMkd").mkdir();
		FtpReply ftpReply = this.command.execute("../testMkd", userState, server);
		Assert.assertEquals("550", ftpReply.getCode());
		new File("../testMkd").delete();
	}
	
	@Test
	public void testCodeErreurParametreNull() {
		FtpReply ftpReply = this.command.execute(null, userState, server);
		Assert.assertEquals("501", ftpReply.getCode());
	}

}
