package command;

import java.io.File;
import java.io.IOException;

public class TestRntoCommand extends TestConnected{
	
	@Override
	public void init(){
		super.init();
		try {
			new File(userState.getRepository() + "/testRnfr.txt").createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.command = new RntoCommand();
	}

	@Override
	public void testCodeCasNominal() {
		new RnfrCommand().execute("", userState, server);
	}

}
