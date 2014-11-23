package zex.command;

import zex.Main;

import java.awt.Color;

//TODO There ought to be better ways of dealing with scrapping threads.
public class CommandCZure {
	private static Thread color = null;
	private static volatile boolean running = false;
	
	public static ReturnValue czure(String[] args){
		if(color == null)
			color = new Thread(new Runnable(){
				public void run(){
					int i = 1;
					Color back;
					while(running){
						try{
							if(i == 4) i = 1;
							Thread.sleep(50);
							switch(i){
							case 3:
								back = Color.BLUE;
								break;
							case 1:
								back = Color.RED;
								break;
							case 2:
								back = Color.GREEN;
								break;
							default:
								back = Color.BLACK;
								break;
							}
							Main.window.setBackgroundColor(back);
							i++;
						}catch(Exception e){}	
					}
					Main.window.setBackgroundColor(Color.BLACK);
				}
			});
		switch(args.length){
		case 1:
			if(!isRunning()){
				running(true);
				color.start();
			}
			break;
		case 2:
			if(args[1].equals("stop"))
				running(false);
				color = null;
			break;
		default:
			break;
		}
		return new ReturnValue(null, true);
	}
	
	private static void running(boolean arg){
		running = arg;
	}
		
	private static boolean isRunning(){
		return running;
	}
}
