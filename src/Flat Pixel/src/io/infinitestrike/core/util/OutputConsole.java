package io.infinitestrike.core.util;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import io.infinitestrike.core.LogBot;
import io.infinitestrike.core.LogBot.Status;

public class OutputConsole extends Thread{
	
	private RedirectedOutputStream out;
	
	private PrintStream stdout = System.out;
	private PrintStream stderr = System.err;
	
	public void run(){
		JTextArea area = new JTextArea();
		
		JScrollPane pane = new JScrollPane(area);
		
		JPanel panel =  new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(pane,BorderLayout.CENTER);
			
		JFrame frame = new JFrame();
			frame.setTitle("Application Output");
			frame.setSize(600, 600);
			frame.setContentPane(panel);
			frame.setResizable(false);
			frame.addWindowListener(new WindowListener(){
				@Override
				public void windowActivated(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowClosed(WindowEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowClosing(WindowEvent arg0) {
					// TODO Auto-generated method stub
					kill();
				}

				@Override
				public void windowDeactivated(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowDeiconified(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowIconified(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowOpened(WindowEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);
			
		this.out = new RedirectedOutputStream(area);
		
		System.setOut(new PrintStream(out));
		System.setErr(new PrintStream(out));
	}
	
	
	public final void kill(){
		

		System.setErr(stderr);
		System.setOut(stdout);
		
		LogBot.logData(Status.INFO, "Closing External Console, Switching to System console");
		
		try{
			this.join();
		}catch(Exception e){
			LogBot.logDataVerbose(e, Status.SEVERE, e.getLocalizedMessage());
		}
	}
	
	public class RedirectedOutputStream extends OutputStream{
		private JTextArea textbox = null;

		public RedirectedOutputStream(JTextArea a){
			this.textbox = a;
		}
		
		@Override
		public void write(int arg0) throws IOException {
			// TODO Auto-generated method stub
			this.textbox.append("" + ((char) arg0));
			this.textbox.setCaretPosition(this.textbox.getText().length());
		}
	}
}
