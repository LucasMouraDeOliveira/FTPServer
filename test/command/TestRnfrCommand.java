package command;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import server.FtpReply;

public class TestRnfrCommand extends TestConnected{

	@Override
	public void init(){
		super.init();
		try {
			new File(userState.getRepository() + "/testRnfr.txt").createNewFile();
			new File(userState.getRepository() + "/testfichierexistant.txt").createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.command = new RnfrCommand();
	}

	@Override
	public void testCodeCasNominal() {
//		Command rnto = new RntoCommand();
//		FtpReply ftpReply;
//		
		Assert.assertTrue(new File(userState.getRepository() + "/testRnfr.txt").exists());
		FtpReply ftpReply = this.command.execute("testRnfr.txt", userState, server);
		Assert.assertEquals("350", ftpReply.getCode());
		Assert.assertEquals(userState.getRenameFile().getName(),"testRnfr.txt");
//		
//		ftpReply = rnto.execute("testRnto.txt", userState);
//		assertEquals(ftpReply.getCode(),FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES);
//		assertFalse(new File(userState.getRepository() + "/testRnfr.txt").exists());
//		assertTrue(new File(userState.getRepository() + "/testRnto.txt").exists());
//
	
	}
	
	@Test
	public void testCodeErreurFichierInexistant(){
		// rename un fichier non existant
		FtpReply ftpReply = this.command.execute("inexistant.txt", userState, server);
		Assert.assertEquals("550", ftpReply.getCode());
	}
//		
//		//reset le rename
//		ftpReply = rnfr.execute("testRnto.txt", userState);
//		ftpReply = rnto.execute("testRnfr.txt", userState);
//		assertTrue(new File(userState.getRepository() + "/testRnfr.txt").exists());
//		assertFalse(new File(userState.getRepository() + "/testRnto.txt").exists());
	
	@Test
	public void testDroit() {
//		Command rnfr = new RnfrCommand();
//		Command rnto = new RntoCommand();
		FtpReply ftpReply = this.command.execute("./admin", userState, server);
		Assert.assertEquals("550", ftpReply.getCode());
		
//		ftpReply = rnfr.execute("testRnfr.txt", userState);
//		// ne peux pas rename en fichier deja existant
//		ftpReply = rnto.execute("testfichierexistant.txt", userState);
//		assertEquals(ftpReply.getCode(),FtpStatusCodes.CODE_550_ACTION_NON_REALISEE);
	}
	
	@After
	public void close(){
		new File(userState.getRepository() + "/testRnfr.txt").delete();
		new File(userState.getRepository() + "/testfichierexistant.txt").delete();
	}
}
