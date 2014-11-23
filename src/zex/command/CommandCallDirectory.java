package zex.command;

import java.io.File;

import zex.explorer.FileExplorer;

public class CommandCallDirectory extends FileExplorer {
	
	public static ReturnValue calldirectory(String[] args){
		try{
			searchdirectory = new File(directory.toString());
		}catch(Exception e){}
		return cd(args, false);
	}
	
	public static ReturnValue cd(String[] args, boolean went_up){
		switch(args.length){
		case 1:
			return new ReturnValue("Command Incomplete", false);
		case 2:
			if(args[1].equals("")){
				
				try{
					directory = new File(searchdirectory.toString());
					return new ReturnValue(null, true);
				}catch(Exception e){}
				
			}else if(args[1].indexOf('~') == 0){
				
				args[1] = System.getProperty("user.home") + "\\" + args[1].substring(1, args[1].length());
				return cd(args, false);
				
			}else if(args[1].indexOf("..\\") == 0){
				
				try{
					searchdirectory = searchdirectory.getParentFile();
				}catch(Exception e){}
				if(searchdirectory != null){
					args[1] = args[1].substring(3, args[1].length());
					return cd(args, true);
				}else{
					return new ReturnValue("Path specified does not exist.", false);
				} 
				
			}else{	
				
				try{
					if(went_up){
						searchdirectory = new File(searchdirectory.toString() +  "\\" + args[1]);
					}else
						searchdirectory = new File(args[1]);
				}catch(Exception e){
					return new ReturnValue("Path incorrectly formatted.", false);
				}
				if(searchdirectory.exists()){
					if(!searchdirectory.isDirectory())
						return new ReturnValue("Cannot call directory on a file.", false);
					directory = new File(searchdirectory.toString());
					return new ReturnValue(null, true);
				}else{
					try{
						searchdirectory = new File(directory.toString() + "\\" + args[1]);
					}catch(Exception e){}
					if(searchdirectory.exists()){
						if(!searchdirectory.isDirectory())
							return new ReturnValue("Cannot call directory on a file.", false);
						directory = new File(searchdirectory.toString());
						return new ReturnValue(null, true);
					}else{
						return new ReturnValue("Path specified does not exist.", false);
					}
				}
				
			}
			break;
		default:
			String[] ret = escapeSpaces(args);
			if(ret != null)
				return cd(ret, false);
			else
				return new ReturnValue("Path incorrectly formatted.", false);
		}
		return null;
	}
}
