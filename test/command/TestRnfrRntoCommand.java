package command;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.FtpReply;
import utilitary.FtpStatusCodes;

public class TestRnfrRntoCommand extends TestConnecte {
	
	@Before
	public void init(){
		super.init();
		try {
			new File(userState.getRepository() + "/testRnfr.txt").createNewFile();
			new File(userState.getRepository() + "/testfichierexistant.txt").createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testexecute() {
		Command rnfr = new RnfrCommand();
		Command rnto = new RntoCommand();
		FtpReply ftpReply;
		
		assertTrue(new File(userState.getRepository() + "/testRnfr.txt").exists());

		ftpReply = rnfr.execute("testRnfr.txt", userState);
		assertEquals(ftpReply.getCode(),FtpStatusCodes.CODE_350_EN_ATTENTE_D_INFORMATION_SUPPLEMENTAIRE);
		assertEquals(userState.getRenameFile().getName(),"testRnfr.txt");
		
		ftpReply = rnto.execute("testRnto.txt", userState);
		assertEquals(ftpReply.getCode(),FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES);
		assertFalse(new File(userState.getRepository() + "/testRnfr.txt").exists());
		assertTrue(new File(userState.getRepository() + "/testRnto.txt").exists());

		// rename un fichier non existant
		ftpReply = rnfr.execute("testRnfr.txt", userState);
		assertEquals(ftpReply.getCode(),FtpStatusCodes.CODE_550_ACTION_NON_REALISEE);
		
		//reset le rename
		ftpReply = rnfr.execute("testRnto.txt", userState);
		ftpReply = rnto.execute("testRnfr.txt", userState);
		assertTrue(new File(userState.getRepository() + "/testRnfr.txt").exists());
		assertFalse(new File(userState.getRepository() + "/testRnto.txt").exists());
	}
	
	@Test
	public void testDroit() {
		Command rnfr = new RnfrCommand();
		Command rnto = new RntoCommand();
		FtpReply ftpReply;
		ftpReply = rnfr.execute("./admin", userState);
		assertEquals(ftpReply.getCode(),FtpStatusCodes.CODE_550_ACTION_NON_REALISEE);
		
		ftpReply = rnfr.execute("testRnfr.txt", userState);
		// ne peux pas rename en fichier deja existant
		ftpReply = rnto.execute("testfichierexistant.txt", userState);
		assertEquals(ftpReply.getCode(),FtpStatusCodes.CODE_550_ACTION_NON_REALISEE);
	}
	
	@Test
	public void testFichierNoExist() {
		Command rnfr = new RnfrCommand();
		FtpReply ftpReply;
		ftpReply = rnfr.execute("noexist.txt", userState);
		assertEquals(ftpReply.getCode(),FtpStatusCodes.CODE_550_ACTION_NON_REALISEE);
	}

	@After
	public void close(){
		super.close();
		new File(userState.getRepository() + "/testRnfr.txt").delete();
		new File(userState.getRepository() + "/testfichierexistant.txt").delete();
	}
}
