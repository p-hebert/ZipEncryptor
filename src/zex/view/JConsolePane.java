package zex.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JTextPane;

import zex.command.CommandParser;
import zex.explorer.FileExplorer;

public class JConsolePane extends JTextPane implements MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private String panecontent = "";
	private boolean appendable = true;
	
	public JConsolePane(String arg0){
		super();
		this.setBackground(Color.BLACK);
		this.setFont(new Font("Courier New", Font.BOLD, 13));
		this.setForeground(Color.LIGHT_GRAY);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.setText(arg0);
		this.complete_update();
		this.setCaretPosition(this.getText().length());
	}
	
	public void appendln(String text){
		this.setText(this.getText() + text + "\n");
	}
	
	public void append(String text){
		this.setText(this.getText() + text);
	}
	
	public void setAppendable(boolean a){
		this.appendable = a;
	}
	
	private String getCommand(){
		return this.getText().substring(this.panecontent.length(), this.getText().length()).trim();
	}
	
	private String getUntrimmedCommand(){
		return this.getText().substring(this.panecontent.length(), this.getText().length());
	}
	
	public void clear(){
		this.setText("");
		this.panecontent = "";
	}
	
	public String getContent(){
		return this.panecontent;
	}
	
	/**
	 * Updates the panecontent to the JTextPane content.
	 * Only to be used during methods that need to update as they are being undertaken.
	 */
	public void update(){
		this.panecontent = this.getText();
		this.setCaretPosition(this.panecontent.length());
	}
	
	/**
	 * Updates the JTextPane content and panecontent in order to archive previous commands.
	 * Append the prompt to ready the console for another command
	 */
	private void complete_update(){
		this.setText(this.getText() + "\n\n" + FileExplorer.directory.toString() + "> ");
		this.panecontent = this.getText();
		this.setCaretPosition(this.panecontent.length());
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(this.appendable){
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				appendln("");
				CommandParser.pointer_reset();
				String parse_return = CommandParser.parse(getCommand());
				if(parse_return != null)
					this.append(parse_return);
				this.complete_update();
				e.consume();
			}else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
				if(this.getCaretPosition() <= panecontent.length())
					e.consume();
			}else if(e.getKeyCode() == KeyEvent.VK_UP){
				e.consume();
				CommandParser.pointer_increase();
				if(CommandParser.get_current() !=  null)
					this.setText(panecontent + CommandParser.get_current());
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
				e.consume();
				CommandParser.pointer_decrease();
				if(CommandParser.get_current() !=  null)
					this.setText(panecontent + CommandParser.get_current());
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
				if(this.getCaretPosition() <= panecontent.length())
					e.consume();
			}else if(e.getKeyCode() == KeyEvent.VK_TAB){
				e.consume();
				if(getCommand().split(" ").length > 1){
					if(getCommand().indexOf("cd") == 0 && getCommand().split(" ")[1].indexOf('~') == 0){
						String completed = FileExplorer.complete(getCommand().split(" "));
						System.err.println(completed);
						if(completed != null)
							this.setText(panecontent + getUntrimmedCommand().substring(0, getUntrimmedCommand().indexOf("cd") + 2) + " " + completed);
					}
				}
			}
		}else{
			e.consume();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(this.getCaretPosition() < this.panecontent.length())
			this.setCaretPosition(this.panecontent.length());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
}
