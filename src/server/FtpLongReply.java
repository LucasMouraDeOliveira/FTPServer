package server;

import java.util.ArrayList;
import java.util.List;

public class FtpLongReply extends FtpReply {
	
	private List<String> lines;
	
	public FtpLongReply(){
		this.lines = new ArrayList<String>();
	}
	
	public void addLine(String line){
		this.lines.add(line+"\r\n");
	}
	
	@Override
	public String toString(){
		String retour = super.toString();
		for(String line : lines){
			retour+=line;
		}
		return retour;
	}

}
