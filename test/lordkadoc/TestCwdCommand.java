package lordkadoc;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import command.CwdCommand;
import server.FtpReply;

public class TestCwdCommand extends TestConnected{
	
	@Override
	public void init() {
		super.init();
		this.command = new CwdCommand();
		new File(this.userState.getRepository() + "/dossierTest").mkdirs();
	}

	@Override
	public void testCodeCasNominal() {
		FtpReply ftpReply = this.command.execute("dossierTest", userState, server);
		assertEquals("250", ftpReply.getCode());
		assertEquals(userState.getRepository(),server.getUserHandler().getRoot("admin")+"/dossierTest");
	}
	
	@Test
	public void testCodeDossierInexistant() {
		//on fait un premier déplacement
		this.command.execute("dossierTest", userState, server);
		// cd dossierTest mais il y a pas ce dossier ici
		FtpReply ftpReply = this.command.execute("dossierTest", userState, server);
		assertEquals("550", ftpReply.getCode());
		assertEquals(userState.getRepository(),server.getUserHandler().getRoot("admin")+"/dossierTest");
	}
		
	@Test
	public void testCodeRetourArriereSucces() {
		// cd .. retour en arriere
		this.command.execute("dossierTest", userState, server);
		FtpReply ftpReply = this.command.execute("..", userState, server);
		assertEquals("250", ftpReply.getCode());
		assertEquals(userState.getRepository(),server.getUserHandler().getRoot("admin"));
	}
		
	@Test
	public void testCodeRetourEnArrierEchec() {
		// pas le droit d'aller plus haut que le dossier root
		FtpReply ftpReply = this.command.execute("..", userState, server);
		assertEquals("550", ftpReply.getCode());
		assertEquals(userState.getRepository(),server.getUserHandler().getRoot("admin"));
	}
	
	@Test
	public void testCodeRetourParametresNull() {
		FtpReply ftpReply = this.command.execute(null, userState, server);
		assertEquals("250", ftpReply.getCode());
		assertEquals(userState.getRepository(),server.getUserHandler().getRoot("admin"));
	}
	
	@After
	public void deleteFolder(){
		new File(userState.getRepository() + "/dossierTest").delete();
	}
	
}
