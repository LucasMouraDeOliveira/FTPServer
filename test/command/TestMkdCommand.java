package command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import command.MkdCommand;
import server.FtpReply;

public class TestMkdCommand extends TestConnected{
	
	@Override
	public void init(){
		super.init();
		this.command = new MkdCommand();
	}

	@Override
	public void testCodeCasNominal() {
		FtpReply reply = this.command.execute("testMkd", userState, server);
		assertEquals("200", reply.getCode());
		assertTrue(new File(userState.getRepository() + "/testMkd").exists());
		new File(userState.getRepository() + "/testMkd").delete();
	}
	
	@Test
	public void testCodeErreurParametreNull() {
		FtpReply reply = this.command.execute(null, userState, server);
		assertEquals("501", reply.getCode());
	}
	
	@Test
	public void testCodeErreurCreeUnDossierDejaExistant() {
		this.command.execute("testMkd", userState, server);
		FtpReply ftpReply = this.command.execute("testMkd", userState, server);
		assertEquals("550", ftpReply.getCode());
		assertTrue(new File(userState.getRepository() + "/testMkd").exists());
		new File(userState.getRepository() + "/testMkd").delete();
	}
	
	@Test
	public void testCodeErreurPasLeDroitAcces() {
		FtpReply ftpReply = this.command.execute("../truc", userState, server);
		assertEquals("550", ftpReply.getCode());
	}
	
//	@Test
//	public void testexecute() {
//		Command mkdcmd = new MkdCommand();
//		Command rmdcmd = new RmdCommand();
//		FtpReply ftpReply;
//		
//		//crée un dossier
//		ftpReply = mkdcmd.execute("testMkd", userState);
//		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES);
//		assertTrue(new File(userState.getRepository() + "/testMkd").exists());
//		
//		//recrée le dossier error
//		ftpReply = mkdcmd.execute("testMkd", userState);
//		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_550_ACTION_NON_REALISEE);
//		assertTrue(new File(userState.getRepository() + "/testMkd").exists());
//		
//		//supp le dossier
//		ftpReply = rmdcmd.execute("testMkd", userState);
//		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES);
//		assertFalse(new File(userState.getRepository() + "/testMkd").exists());
//		
//		//resupp le dossier error
//		ftpReply = rmdcmd.execute("testMkd", userState);
//		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_550_ACTION_NON_REALISEE);
//		assertFalse(new File(userState.getRepository() + "/testMkd").exists());
//	}
//	
//	@Test
//	public void testPasLesDroitSurLeDossier() {
//		Command mkdcmd = new MkdCommand();
//		Command rmdcmd = new RmdCommand();
//		FtpReply ftpReply;
//		
//		ftpReply = mkdcmd.execute("../truc", userState);
//		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_550_ACTION_NON_REALISEE);
//		
//		ftpReply = rmdcmd.execute("../admin", userState);
//		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_550_ACTION_NON_REALISEE);
//	}
//	
//	@Test
//	public void testSuppRecursif() {
//		Command mkdcmd = new MkdCommand();
//		Command rmdcmd = new RmdCommand();
//		FtpReply ftpReply;
//		
//		ftpReply = mkdcmd.execute("truc", userState);
//		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES);
//		ftpReply = mkdcmd.execute("truc/1truc", userState);
//		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES);
//		ftpReply = mkdcmd.execute("truc/2truc", userState);
//		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES);
//		ftpReply = mkdcmd.execute("truc/3truc", userState);
//		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES);
//		
//		ftpReply = rmdcmd.execute("truc", userState);
//		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_200_ACTION_REALISEE_AVEC_SUCCES);
//	}


}
