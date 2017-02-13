package command;

import static org.junit.Assert.*;

import org.junit.Test;

import server.FtpReply;
import utilitary.FtpStatusCodes;

public class TestTypeCommand extends TestConnecte {

	@Test
	public void testexecute() {
		Command typecmd = new TypeCommand();
		FtpReply ftpReply = typecmd.execute("", userState);
		assertEquals(ftpReply.getCode(),FtpStatusCodes.CODE_502_COMMANDE_NON_IMPLEMENTEE);
	}	

}
