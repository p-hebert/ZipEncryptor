package zex.command;

public class ReturnValue{
	
	private String message;
	private boolean bool;
	private boolean success;
	private int constructor;
	
	public ReturnValue(String arg0, boolean arg1){
		this.message = arg0;
		this.success = arg1;
		this.constructor = 1;
	}
	
	public ReturnValue(boolean arg0, boolean arg1){
		this.bool = arg0;
		this.success = arg1;
		this.constructor = 2;
	}
	
	public int constructor(){
		return this.constructor;
	}
	
	public boolean isBool() throws Exception{
		if(this.constructor == 1) throw new Exception();
		return this.bool;
	}
	
	public boolean isSuccess(){
		return this.success;
	}
	
	public String getMessage() throws Exception{
		if(this.constructor == 2) throw new Exception();
		return this.message;
	}
}
