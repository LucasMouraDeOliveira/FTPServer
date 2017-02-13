package command;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserHandler;

public class TestCwdCommand extends TestConnecte {

	@Before
	public void init(){
		super.init();
		new File(userState.getRepository() + "/dossierTest").mkdir();
	}
	
	@Test
	public void testexecute() {
		
		Command cwdcmd = new CwdCommand();
		// cd dossierTest
		FtpReply ftpReply = cwdcmd.execute("dossierTest", userState);
		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_250_ACTION_SUR_LE_FICHIER_REALISEE_AVEC_SUCCES);
		assertEquals(userState.getRepository(),UserHandler.getRoot("admin")+"/dossierTest");
		// cd dossierTest mais il y a pas ce dossier ici
		ftpReply = cwdcmd.execute("dossierTest", userState);
		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_550_ACTION_NON_REALISEE);
		assertEquals(userState.getRepository(),UserHandler.getRoot("admin")+"/dossierTest");
		
		// cd .. retour en arriere
		ftpReply = cwdcmd.execute("..", userState);
		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_250_ACTION_SUR_LE_FICHIER_REALISEE_AVEC_SUCCES);
		assertEquals(userState.getRepository(),UserHandler.getRoot("admin"));
		
		// pas le droit d'aller plus haut que le dossier root
		ftpReply = cwdcmd.execute("..", userState);
		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_550_ACTION_NON_REALISEE);
		assertEquals(userState.getRepository(),UserHandler.getRoot("admin"));
		
	}
	
	@After
	public void close(){
		super.close();
		new File(userState.getRepository() + "/dossierTest").delete();
	}

}
