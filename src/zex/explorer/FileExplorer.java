package zex.explorer;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public abstract class FileExplorer {
	public static File directory;
	protected static File searchdirectory;
	
	public static void init(){
		try{
			directory = new File(System.getProperty("user.home"));
		}catch(Exception e){}
	}
	
	public static File getFile(boolean encrypt){
		File openedFile = null;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        final JFrame frame = new JFrame("Open File");
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        if(!encrypt)
        	chooser.setFileFilter(new FileNameExtensionFilter("ZIP Encrypted Files (.zef)","zef"));
        else
        	chooser.setFileFilter(new FileNameExtensionFilter("ZIP Files (.zip)","zip"));
        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
        	try{
        		openedFile = chooser.getSelectedFile();	
        	}catch(Exception e){}
        }
		return openedFile;
	}
	
	public static File saveToFile(boolean encrypt){
		File targetFile = null;
		int save = JOptionPane.ERROR_MESSAGE;
		
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        final JFrame frame = new JFrame("Save File");
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        if(encrypt)
        	chooser.setFileFilter(new FileNameExtensionFilter("ZIP Encrypted Files (.zef)","zef"));
        else
        	chooser.setFileFilter(new FileNameExtensionFilter("ZIP Files (.zip)","zip"));
        
        
        if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
        	try{
        		targetFile = chooser.getSelectedFile();	
        	}catch(Exception e){}
        	if(targetFile.exists()){
            	String dialog = "Saving over a file will overwrite it. Are you sure you want to proceed?";
            	save = JOptionPane.showConfirmDialog(null, dialog, "Save As", JOptionPane.YES_NO_OPTION);
            	switch(save){
            	case JOptionPane.YES_OPTION:
            		break;
            	case JOptionPane.NO_OPTION:
            		saveToFile(encrypt);
            		break;
            	case JOptionPane.CLOSED_OPTION:
            	case JOptionPane.CANCEL_OPTION:
            	default:
            		targetFile = null;
            		break;
            	}
        	}
        }
		return targetFile;	
	}
	
	public static String complete(String[] args){
		if(args.length > 2){
			String[] ret = escapeSpaces(args);
			if(ret != null)
				complete(ret);
		}else{
			try{
				searchdirectory = new File(System.getProperty("user.home") + args[1].substring(1, args[1].lastIndexOf("\\") + 1));
			}catch(Exception e){
				e.printStackTrace();
			}
			if(searchdirectory.exists()){
				if(!searchdirectory.isDirectory())
					return null;
				String[] ls = searchdirectory.list();
				String tocomplete = args[1].substring(args[1].lastIndexOf("\\") + 1, args[1].length());
				ArrayList<Integer> indexes = new ArrayList<Integer>(0);
				for(int i = 0 ; i < ls.length ; i++){
					if(ls[i].contains(tocomplete))
						indexes.add(new Integer(i));
				}
				indexes.trimToSize();
				if(indexes.size() == 1){
					String[] regex_doesnt_work = ls[indexes.get(0).intValue()].split(" ");
					String with_backslashes = "";
					for(String str : regex_doesnt_work){
						with_backslashes += "\\ " + str;
					}
					args[1] = args[1].substring(0, args[1].lastIndexOf("\\") + 1) + with_backslashes.substring(2);
					return args[1];
				}
			}else{
				System.err.println("searchdirectory does not exist");
			}
		}
		return null;
	}
	
	protected static String[] escapeSpaces(String[] args){
		if(args[1].lastIndexOf('\\') == args[1].length() - 1 ){
			String[] ret = new String[args.length -1];
			ret[0] = args[0];
			ret[1] = args[1].substring(0, args[1].length()-1) + " " + args[2];
			for(int i = 2; i < args.length - 1; i++){
				ret[i] = args[i+1];
			}
			return ret;
		}else{
			return null;
		}
	}
}
	
