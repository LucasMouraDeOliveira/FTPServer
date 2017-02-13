package command;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import server.FtpReply;
import utilitary.FtpStatusCodes;

public class TestMkdRmdCommand extends TestConnecte {

	@Test
	public void testexecute() {
		Command mkdcmd = new MkdCommand();
		Command rmdcmd = new RmdCommand();
		FtpReply ftpReply;
		
		//crée un dossier
		ftpReply = mkdcmd.execute("testMkd", userState);
		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES);
		assertTrue(new File(userState.getRepository() + "/testMkd").exists());
		
		//recrée le dossier error
		ftpReply = mkdcmd.execute("testMkd", userState);
		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_550_ACTION_NON_REALISEE);
		assertTrue(new File(userState.getRepository() + "/testMkd").exists());
		
		//supp le dossier
		ftpReply = rmdcmd.execute("testMkd", userState);
		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES);
		assertFalse(new File(userState.getRepository() + "/testMkd").exists());
		
		//resupp le dossier error
		ftpReply = rmdcmd.execute("testMkd", userState);
		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_550_ACTION_NON_REALISEE);
		assertFalse(new File(userState.getRepository() + "/testMkd").exists());
	}
	
	@Test
	public void testPasLesDroitSurLeDossier() {
		Command mkdcmd = new MkdCommand();
		Command rmdcmd = new RmdCommand();
		FtpReply ftpReply;
		
		ftpReply = mkdcmd.execute("../truc", userState);
		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_550_ACTION_NON_REALISEE);
		
		ftpReply = rmdcmd.execute("../admin", userState);
		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_550_ACTION_NON_REALISEE);
	}
	
	@Test
	public void testSuppRecursif() {
		Command mkdcmd = new MkdCommand();
		Command rmdcmd = new RmdCommand();
		FtpReply ftpReply;
		
		ftpReply = mkdcmd.execute("truc", userState);
		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES);
		ftpReply = mkdcmd.execute("truc/1truc", userState);
		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES);
		ftpReply = mkdcmd.execute("truc/2truc", userState);
		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES);
		ftpReply = mkdcmd.execute("truc/3truc", userState);
		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES);
		
		ftpReply = rmdcmd.execute("truc", userState);
		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES);
	}

}
