package zex.explorer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import zex.Main;
import zex.command.ReturnValue;
import zex.view.ProgressBar;

public class FileStream {
	public static byte[] data;
	
	public static ReturnValue read(File f_in, boolean verbose){
		DataInputStream stream = null;
		ReturnValue value;
		int length;
		if(f_in.exists() && f_in.isFile()){
			if(f_in.length() > Integer.MAX_VALUE)		
				return new ReturnValue("Reading aborted.\nSource file is too large, program will not encrypt it.", false);
			else
				length = (int)f_in.length();
		}else
			return new ReturnValue("Reading aborted.\nSource file is not a file.", false);
		
		try{
			stream = new DataInputStream(new BufferedInputStream(new FileInputStream(f_in)));
		}catch(IOException e){
			e.printStackTrace();
			value = closeStream(stream, "Reading aborted.\nSource file could not be read; verify if file is not hidden, protected or unavailable.", false);
			return value;
		}
		data = new byte[length];
		if(verbose){
			try{
				ProgressBar.newBar();
			}catch(Exception e){
				value = closeStream(stream, e.getMessage(), false);
				return value;
			}
			for(int i = 0; i < length; i++){
				try{
					data[i] = stream.readByte();
					ProgressBar.increase((int)(((i+1) / (double)length)*100));
				}catch(IOException e){
					e.printStackTrace();
					value = closeStream(stream, "Reading aborted.\nSource file is no longer readable; verify if file is not unavailable.", false);
					return value;
				}catch(Exception e){
					value = closeStream(stream, e.getMessage(), false);
					return value;
				}
			}
			value = closeStream(stream, null, true);
			return value;
		}else{
			for(int i = 0; i < length; i++){
				try{
					data[i] = stream.readByte();
				}catch(IOException e){
					e.printStackTrace();
					value = closeStream(stream, "Reading aborted.\nSource file is no longer readable; verify if file is not unavailable.", false);
					return value;
				}
			}
			value = closeStream(stream, null, true);
			return value;
		}
	}
	
	public static ReturnValue write(File f_out, byte[] crypted, boolean verbose){
		DataOutputStream stream = null;
		ReturnValue value = null;
		try{
			stream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f_out.toString())));
		}catch(IOException e){
			e.printStackTrace();
			value = closeStream(stream, "Writing aborted.\nDestination file has been moved or altered by another program.", false);
			return value;
		}
		try{
			stream.write(crypted);
		}catch(IOException e){
			e.printStackTrace();
			value = closeStream(stream, "Writing aborted.\nDestination file has been moved or altered by another program.", false);
			return value;
		}
		return closeStream(stream, null, true);
	}
	
	private static ReturnValue closeStream(DataInputStream stream, String message, boolean success){
		if(!success)
			data = null; //to activate garbage collector on data (don't want to have this much useless memory used)
		try{
			stream.close();
		}catch(Exception e){
			Main.window.getPane().appendln("Writing aborted.\nInput stream cannot be closed." + 
											"\n\n Program will now terminate.");
			try{
				Thread.sleep(3000);
			}catch(InterruptedException e2){}
			System.exit(1);
		}
		return new ReturnValue(message, success);
	}
	
	private static ReturnValue closeStream(DataOutputStream stream, String message, boolean success){
		if(!success)
			data = null; //to activate garbage collector on data (don't want to have this much useless memory used)
		try{
			stream.close();
		}catch(Exception e){
			Main.window.getPane().appendln("Reading aborted.\nInput stream cannot be closed." + 
											"\n\n Program will now terminate.");
			try{
				Thread.sleep(3000);
			}catch(InterruptedException e2){}
			System.exit(1);
		}
		return new ReturnValue(message, success);
	}
	
}
