package zex.command;

import java.io.File;

import zex.explorer.FileExplorer;

public class CommandList extends FileExplorer {

	public static ReturnValue list(String[] args){
		if(args.length > 1){
			if(args.length == 2 && args[1].equals("-v")){
				File current = new File(directory.toString());
				String[] ls = current.list();
				String ret = null;
				if(ls != null){
					ret = "";
					for(String str : ls){
						ret += str + "\n";
					}
					ret = ret.substring(0, ret.length()-1);
				}
				return new ReturnValue(ret, true);
			}else
				return new ReturnValue("ls does not take arguments", false);
		}else{
			File current = new File(directory.toString());
			String[] ls = current.list();
			if(ls != null){
				int lastLine = ls.length%2, lines = ls.length/2 + lastLine;
				String str_list = "";
				for(int i = 0; i < lines ; i+=2){
					String one = ls[i], two = ls[i+1];
					if(one.length() > 16)
						one = one.substring(0, 16) + "...";
					else
						for(int j = one.length(); j < 19 ; j++){ one += " "; }
					if(two.length() > 16)
						two = two.substring(0, 16) + "...";
					else
						for(int j = two.length(); j < 19 ; j++){ two += " "; }
					str_list += one + "\t" + two + "\n";
				}
				if(lastLine != 0){
					String three = ls[ls.length -1];
					if(three.length() > 10) three = three.substring(0, 10) + "...";
					str_list += three;
				}
				return new ReturnValue(str_list, true);
			}else
				return new ReturnValue("<empty>", true);
		}
	}
}
