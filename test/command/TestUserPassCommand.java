package command;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.FTPLauncher;
import server.FtpReply;
import utilitary.FtpStatusCodes;
import utilitary.UserState;

public class TestUserPassCommand {
	
	UserState userState;
	Serverbidon sb;
	Socket socket;
	
	@Before
	public void init(){
		sb = new Serverbidon();
		sb.start();
		socket =new Socket();
		try {
			socket.connect(new InetSocketAddress("localhost", FTPLauncher.FTP_PORT_COMMAND));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.userState = new UserState(socket);
	}
	
	@Test
	public void testConnexion() {
		Command usecmd = new UserCommand();
		Command passcmd = new PassCommand();
		FtpReply reponse;
		reponse = usecmd.execute("admin", userState);
		assertEquals(reponse.getCode(), FtpStatusCodes.CODE_331_USER_OK_NEED_PASSWORD);
		assertEquals("admin", userState.getUser());
		reponse = passcmd.execute("admin", userState);
		assertEquals(reponse.getCode(), FtpStatusCodes.CODE_230_UTILISATEUR_CONNECTE);
		assertTrue(userState.isLogged());
	}
	
	@Test
	public void testBadLoginMdp() {
		Command usecmd = new UserCommand();
		Command passcmd = new PassCommand();
		FtpReply reponse;
		reponse = usecmd.execute("badlogin", userState);
		assertEquals(reponse.getCode(), FtpStatusCodes.CODE_331_USER_OK_NEED_PASSWORD);
		assertEquals("badlogin", userState.getUser());
		reponse = passcmd.execute("badmdp", userState);
		assertEquals(reponse.getCode(), FtpStatusCodes.CODE_530_PAS_CONNECTE);
		assertFalse(userState.isLogged());
	}
	
	@Test
	public void testMdpBeforeLogin() {
		Command passcmd = new PassCommand();
		FtpReply reponse;
		reponse = passcmd.execute("admin", userState);
		assertEquals(reponse.getCode(), FtpStatusCodes.CODE_503_MAUVAIS_ORDRE_DE_COMMANDES);
		assertFalse(userState.isLogged());
	}
	
	@After
	public void close(){
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.close();
	}

}
