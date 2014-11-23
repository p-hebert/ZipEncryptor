package zex.command;

import java.awt.BorderLayout;
import java.io.File;
import java.util.regex.Matcher;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import zex.Main;
import zex.encryptor.Encryptor;
import zex.explorer.FileExplorer;
import zex.explorer.FileStream;

public class CommandEncrypt {
	private File source;
	private File destination;
	private static CommandEncrypt singleton;
	
	private CommandEncrypt(){}
	
	public static boolean init(){
		if(singleton == null){
			singleton = new CommandEncrypt();
			return true;
		}else
			return false;
	}
	
	public static ReturnValue encrypt_manager(String[] args){
		ReturnValue value;
		if(args[0].equals("encrypt")){
			value = singleton.encryptParser(args);
			if(value.isSuccess()){
				boolean verbose = false;
				try{
					verbose = value.isBool();
				}catch(Exception e){
					e.printStackTrace();
				}
				return singleton.crypt(verbose, true);
			}else
				return value;
		}else{
			value = singleton.decryptParser(args);
			if(value.isSuccess()){
				boolean verbose = false;
				try{
					verbose = value.isBool();
				}catch(Exception e){
					e.printStackTrace();
				}
				return singleton.crypt(verbose, false);
			}else
				return value;
		}
	}
	
	private ReturnValue encryptParser(String[] args){
		boolean verbose = false;
		switch(args.length){
		case 2:
			if(args[1].equals("-v"))
				verbose = true;
			else
				return new ReturnValue("Command Incomplete", false);
		case 1:
			File proxy;
			Main.window.getPane().appendln("Please set your source file");
			try{
				Thread.sleep(500);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			proxy = FileExplorer.getFile(true);
			if(proxy == null) return new ReturnValue("Process was aborted by user", false);
			singleton.setSource(proxy);
			Main.window.getPane().appendln("Please set your destination file");
			try{
				Thread.sleep(500);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			proxy = FileExplorer.saveToFile(true);
			if(proxy == null) return new ReturnValue("Process was aborted by user", false);
			singleton.setDestination(proxy);	
			break;
		case 4:
			if(args[3].equals("-v"))
				verbose = true;
			else
				return new ReturnValue("Wrong Arguments", false); 
		case 3:
			File in, out;
			try{
				in = new File(FileExplorer.directory.toString() + "\\" + args[1]);
				String in_path = in.toString();
				if(!in.exists() || !in.isFile()){
					throw new Exception("Source file does not exist or is a directory");
				}else if(in_path.lastIndexOf(".zip") != in_path.length()-4){
					throw new Exception("Source file is not a ZIP Archive. Please specify the extension of the file.");
				}
				out = new File(FileExplorer.directory.toString() + "\\" + args[2]);
				String out_path = out.toString();
				if(out.isDirectory()) throw new Exception("Destination file is a directory");
				if(out.exists()) Main.window.getPane().appendln("Destination file will be overriden");
				if(out_path.lastIndexOf(".zef") != out_path.length()-4) throw new Exception("Destination file should be of extension .zef");
			}catch(Exception e){
				return new ReturnValue(e.getMessage(), false);
			}
			singleton.setSource(in);
			singleton.setDestination(out);
			break;
		default:
			return new ReturnValue("Wrong Arguments", false); 
		}
		return new ReturnValue(verbose, true);
	}
	
	private ReturnValue decryptParser(String[] args){
		boolean verbose = false;
		switch(args.length){
		case 2:
			if(args[1].equals("-v"))
				verbose = true;
			else
				return new ReturnValue("Command Incomplete", false);
		case 1:
			File proxy;
			Main.window.getPane().appendln("Please set your source file");
			try{
				Thread.sleep(500);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			proxy = FileExplorer.getFile(false);
			if(proxy == null) return new ReturnValue("Process was aborted by user", false);
			singleton.setSource(proxy);
			Main.window.getPane().appendln("Please set your destination file");
			try{
				Thread.sleep(500);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			proxy = FileExplorer.saveToFile(false);
			if(proxy == null) return new ReturnValue("Process was aborted by user", false);
			singleton.setDestination(proxy);
			break;
		case 4:
			if(args[3].equals("-v"))
				verbose = true;
			else
				return new ReturnValue("Wrong Arguments", false); 
		case 3:
			File in, out;
			try{
				in = new File(FileExplorer.directory.toString() + "\\" + args[1]);
				String in_path = in.toString();
				if(!in.exists() || !in.isFile()){
					throw new Exception("Source file does not exist or is a directory");
				}else if(in_path.lastIndexOf(".zef") != in_path.length()-4){
					throw new Exception("Source file is not a ZIP Encrypted File. Please specify the extension of the file.");
				}
				out = new File(FileExplorer.directory.toString() + "\\" + args[2]);
				String out_path = out.toString();
				if(out.isDirectory()) throw new Exception("Destination file is a directory");
				if(out.exists()) Main.window.getPane().appendln("Destination file will be overriden");
				if(out_path.lastIndexOf(".zip") != in_path.length()-4) throw new Exception("Destination file should be of extension .zip");
			}catch(Exception e){
				return new ReturnValue(e.getMessage(), false);
			}
			singleton.setSource(in);
			singleton.setDestination(out);
			break;
		default:
			return new ReturnValue("Wrong Arguments", false); 
		}
		return new ReturnValue(verbose, true);
	}
	
	public ReturnValue crypt(boolean verbose, boolean encrypt){
		Main.window.getPane().appendln("Reading from file...");
		ReturnValue value = FileStream.read(singleton.source, verbose);
		if(value.isSuccess()){
			Main.window.getPane().appendln("Reading was successful.");
		}else{
			return value;
		}
		value = passwordWindow();
		String password = null;
		if(value.isSuccess()){
			try{
				password = value.getMessage();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else
			return value;
		Main.window.getPane().appendln((encrypt)? "Encrypting..." : "Decrypting...");
		value = Encryptor.crypt(password, singleton.destination, encrypt);
		if(value.isSuccess()){
			Main.window.getPane().appendln((encrypt)? "Encryption was successful." : "Decryption was successful.");
		}else{
			return value;
		}
		Main.window.getPane().appendln("Writing to " + singleton.destination.toString());
		value = FileStream.write(singleton.destination, Encryptor.getResult(encrypt), verbose);
		if(value.isSuccess()){
			Main.window.getPane().appendln("Writing to file was successful.");
		}else{
			return value;
		}
		return new ReturnValue("The operation was successful.", true);
	}
	
	private void setSource(File file){
		this.source = file;
	}
	
	private void setDestination(File file){
		this.destination = file;
	}
	
	private static ReturnValue passwordWindow(){
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Please enter the encryption password");
		JPasswordField pass = new JPasswordField(30);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		pass.setHorizontalAlignment(SwingConstants.CENTER);
		BorderLayout layout = new BorderLayout();
		layout.setVgap(5);
		panel.setLayout(layout);
		panel.add(label, BorderLayout.NORTH);
		panel.add(pass, BorderLayout.SOUTH);
		String[] options = {"OK", "Cancel"};
		boolean valid = false;
		do{
			int option = JOptionPane.showOptionDialog(null, panel, "Enter Password",
			                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
			                         null, options, options[1]);
			if(option == 1) return new ReturnValue("Process was aborted by user", false);
			String password = new String(pass.getPassword());
			if(password.matches("^[\\u0020-\\u007E]{8,63}$")){
				valid = true;
			}else{
				Main.window.getPane().appendln("Password should be 8 to 63 characters long and be" +
				"comprised only of ASCII characters (a-zA-Z0-9,./\\'\"[](){};:+=-_*&^%$#@!~?|<> and whitespace).");
			}
		}while(!valid);
		String password = new String(pass.getPassword());
		return new ReturnValue(password, true);
	}
}
