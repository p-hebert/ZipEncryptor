package zex.view;

import zex.Main;

public class ProgressBar {
	private static ProgressBar singleton;
	private static JConsolePane console;
	private String bar = "";
	private int progress = 0;
	
	private ProgressBar(){
		if(console == null)
			ProgressBar.console = Main.window.getPane();
		this.bar += ":";
		for(int i = 0; i < 50 ; i++){
			this.bar += "-";
		}
		this.bar += ":";
		this.progress = 0;
	}
	
	public static void newBar() throws Exception{
		if(singleton == null || singleton.progress == 100){
			singleton = new ProgressBar();
			console.update();
			console.append(console.getContent() + singleton.toString());
		}else throw new Exception("Attempt to initialize a new ProgressBar although the current one is not finished.");
	}
	
	public static void increase(int progress) throws Exception{
		if(progress == singleton.progress + 1){
			singleton.progress++;
			if(singleton.progress > 100) throw new Exception("Progress Bar out of range.");
			if(singleton.progress % 2 == 0 && singleton.progress != 0){
				singleton.bar = ":";
				int k = singleton.progress;
				for(int i = 0; i < 50 ; i++){
					if(k > 0){
						k -= 2;
						singleton.bar += "|";
					}else{
						singleton.bar += "-";
					}
				}
				singleton.bar += ":";
				update();
			}
		}
	}
	
	private static void update(){
		String panecontent = console.getContent();
		console.setText(panecontent + singleton.toString());
		if(singleton.progress == 100){
			console.appendln("... done.");
			console.update();
		}
	}
	
	@Override
	public String toString(){
		return (singleton.bar + " " + singleton.progress + "%");
	}
}
