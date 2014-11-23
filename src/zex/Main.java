package zex;
import zex.command.CommandEncrypt;
import zex.explorer.FileExplorer;
import zex.view.ProgramWindow;

public class Main {
	public static ProgramWindow window;
	
	public static void main(String[] args) {
		
		
		//This is to test the encrypt() method
		/*int max = 2048;
		int[] array = new int[max];
		int count = 2;
		for(int i = 0; i < max ; i++){
			int answer = Encryptor.encrypt(i, max);
				answer = Encryptor.encrypt(answer, max);
			array[answer] += 1;
			//System.out.println("i:" + i + " ans: " + answer);
			//System.err.println();
		}
		boolean works = true;
		for(int i = 0 ; i < array.length ; i++){
			if(array[i] != 1){
				System.err.println(i + ": " + array[i]);
				works = false;
			}
		}
		System.err.println(works);*/
		FileExplorer.init();
		CommandEncrypt.init();
		window = new ProgramWindow();
	}

}
