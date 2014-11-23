package zex.command;

public class CommandHelp {
	private enum Definitions{
		VOID("Provides more information on specified argument.\n" +
				"\nhelp['void'|cd|ls|encrypt|decrypt|exit]\n\n"
				+ "Enter help followed by any of the argument listed in the square brackets to get more information on the command. " + 
				"Note that 'void' is the absence of a second parameter, and that parameters should be entered in the order shown."),
		CD("Change Directory according to specified argument.\n" +
				"\ncd[~|..\\][{PATH}]\n\n" + 
				"'~' allows to find the user home automatically. Using TAB will provide PATH autocompletion.\n" + 
				"'..\\' calls the current directory's parent folder. Incompatible with '~'. Can be written multiple time, until root is reached.\n" +
				"{PATH} is any properly formatted path. Both relative and absolute path are accepted. Relative path should start at current directory. " +
				"Note that only backslash will work and that spaces must be escaped using the backslash."),
		LS("List files and subdirectories in current folder.\n-v will list in verbose mode"),
		ENCRYPT("Feature unavailable for security reasons." /*+
				"Encrypts the source file to the destination file.\n" +
				"\nencrypt['void'|{SOURCE} {DESTINATION}][-v]\n\n" +
				"'void' starts the user-friendly mode with windowing interface\n" +
				"{SOURCE} {DESTINATION} provide the console alternative to user-friendly mode.\n" + 
				"{SOURCE} {DESTINATION} denote the paths for the source and destination file, relative to the current directory.\n" + 
				"Note that that for console-mode both files should be in the same directory\n" +
				"Note that if the destination file already exists, no warning will be given in the console mode" +
				"-v will encrypt in verbose mode."*/),
		DECRYPT("Decrypts the source file to the destination file.\n" +
				"\ndecrypt['void'|{SOURCE} {DESTINATION}][-v]\n\n" +
				"'void' starts the user-friendly mode with windowing interface\n" +
				"{SOURCE} {DESTINATION} provide the console alternative to user-friendly mode.\n" + 
				"{SOURCE} {DESTINATION} denote the paths for the source and destination file, relative to the current directory.\n" + 
				"Note that for console-mode both files should be in the same directory.\n" +
				"Note that if the destination file already exists, no warning will be given in the console mode" +
				"-v will decrypt in verbose mode."),
		EXIT("Exits the current instance of the program.\nDoes not take any argument.");
		
		private String message;
		
		private Definitions(String message){
			this.message = message;
		}
		
		public String getMessage(){
			return this.message;
		}
	}
	
	public static ReturnValue help(String[] args){
		switch(args.length){
		case 1:
			return new ReturnValue(Definitions.VOID.getMessage(), true);
		case 2:
			switch(args[1]){
			case "cd":
				return new ReturnValue(Definitions.CD.getMessage(), true);
			case "ls":
				return new ReturnValue(Definitions.LS.getMessage(), true);
			case "encrypt":
				return new ReturnValue(Definitions.ENCRYPT.getMessage(), true);
			case "decrypt":
				return new ReturnValue(Definitions.DECRYPT.getMessage(), true);
			case "exit":
				return new ReturnValue(Definitions.EXIT.getMessage(), true);
			default:
				break;
			}
		default:
			return new ReturnValue("Wrong parameters.", false);
		}
	}
}
