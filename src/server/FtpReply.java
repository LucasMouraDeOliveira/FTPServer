package server;

public class FtpReply {
	
	protected String code;
	
	protected String message;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isEmpty(){
		return this.code == null || this.code.isEmpty();
	}
	
	@Override
	public String toString(){
		return this.code + " " + this.message;
	}

}
