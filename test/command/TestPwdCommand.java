package command;

import static org.junit.Assert.*;

import org.junit.Test;

import server.FtpReply;
import utilitary.FtpStatusCodes;

public class TestPwdCommand extends TestConnecte {
	
	@Test
	public void testexecute() {
		PwdCommand pwdcmd = new PwdCommand();
		FtpReply ftpReply = pwdcmd.execute("", this.userState);
		assertEquals(ftpReply.getCode(), FtpStatusCodes.CODE_257_CHEMIN_D_ACCES_RETOURNE);
		assertEquals(ftpReply.getMessage(), "\""+this.userState.getRepository()+"\"");

	}

}
