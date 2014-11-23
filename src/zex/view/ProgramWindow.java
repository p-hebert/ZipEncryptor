package zex.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;


public class ProgramWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JScrollPane scroll;
	private JConsolePane console;
	
	
	public ProgramWindow(){
		super();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(680, 340));
		this.setTitle("ZipEncryptor");
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());
		this.panel.setBorder(null);
		setConsole();
		this.panel.add(scroll, BorderLayout.CENTER);
		this.panel.setBackground(Color.BLACK);
		this.add(this.panel);
		this.setVisible(true);
		
	}
	
	private void setConsole(){
		console = new JConsolePane("ZipEncryptor version 1.0\nAll rights reserved.\nFor help, enter \"help\".\n" +
				"[Case-sensitive: Commands should be queried in lower case]");
		scroll = new JScrollPane(console);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
	}
	
	public JConsolePane getPane(){
		return this.console;
	}
	
	public void setBackgroundColor(Color color){
		this.console.setBackground(color);
	}
	
}
