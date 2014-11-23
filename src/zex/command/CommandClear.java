package zex.command;

import zex.Main;

public class CommandClear {
	public static ReturnValue clear(String[] args){
		ReturnValue value;
		switch(args.length){
		case 1:
			Main.window.getPane().clear();
			value = new ReturnValue(null, true);
			return value;
		default:
			return new ReturnValue("clear does not take any argument", false);
		}
	}
}
