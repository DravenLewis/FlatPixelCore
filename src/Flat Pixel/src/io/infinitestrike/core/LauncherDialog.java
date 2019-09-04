package io.infinitestrike.core;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import io.infinitestrike.core.util.ArrayCompare;

public class LauncherDialog extends JFrame{
	
	public int width = 640;
	public int height = 480;
	
	public int frequency = 60;
	
	public boolean fullscreen = false;
	public boolean vsync = false;
	public boolean debug = false;
	
	public boolean isInputChosen = false;
	
	public LauncherDialog(){
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		JPanel panel =  new JPanel();
		panel.setLayout(null);
		panel.setSize(300,126);
		
		JLabel label = new JLabel("Game Resolution");
		label.setBounds(10, 11, 97, 14);
		panel.add(label);
		
		JComboBox resolutionBox = new JComboBox();
		resolutionBox.setBounds(10, 26, 280, 20);
		DisplayMode[] modes = null;
		try {
			modes = Display.getAvailableDisplayModes();
		} catch (LWJGLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int[] resolutions = new int[modes.length];
		
		for(int i = 0; i < modes.length; i++){
			resolutions[i] = modes[i].getWidth() * modes[i].getHeight();
		}
		
		int max = ArrayCompare.getMaxValueInArray(resolutions)[0];
		int min = ArrayCompare.getMinValueInArray(resolutions)[0];
		
		for(int i = 0; i < modes.length; i++){
			DisplayMode mode = modes[i];
			if(i == min){
				resolutionBox.addItem(mode.getWidth() + "x" + mode.getHeight() + " - Lowest");
			}else if(i == max){
				resolutionBox.addItem(mode.getWidth() + "x" + mode.getHeight() + " - Highest");
			}else{
				resolutionBox.addItem(mode.getWidth() + "x" + mode.getHeight());
			}
		}
		
		panel.add(resolutionBox);
		
		JCheckBox fullScreen = new JCheckBox("Fullscreen");
		fullScreen.setBounds(10, 53, 97, 23);
		panel.add(fullScreen);
		
		JCheckBox vSync = new JCheckBox("VSync");
		vSync.setBounds(109, 53, 75, 23);
		//
		vSync.setSelected(true);
		vSync.setEnabled(false);
		//
		panel.add(vSync);
		
		JCheckBox chckbxDebugMode = new JCheckBox("Debug Mode");
		chckbxDebugMode.setBounds(193, 53, 97, 23);
		panel.add(chckbxDebugMode);
		
		JButton btnLaunch = new JButton("Skip");
		btnLaunch.setBounds(201, 92, 89, 23);
		btnLaunch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, "Starting Game with default options!");
				isInputChosen = true;
			}
		});
		panel.add(btnLaunch);
		
		JButton button = new JButton("Launch");
		button.setBounds(102, 92, 89, 23);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					DisplayMode mode = Display.getAvailableDisplayModes()[resolutionBox.getSelectedIndex()];
					
					width = mode.getWidth();
					height = mode.getHeight();
					fullscreen = fullScreen.isSelected();
					vsync = vSync.isSelected();
					frequency = mode.getFrequency();
					debug = chckbxDebugMode.isSelected();
					
					isInputChosen = true;
				} catch (LWJGLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(button);
		
		setSize(315,164);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); 
		int x = (d.width / 2) - (getWidth()/2);
		int y = (d.height / 2) - (getHeight()/2);
		
	
		setTitle("Launcher");
		setResizable(false);
		setLocation(x,y);
		setContentPane(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	

	public void destroy() {
		// TODO Auto-generated method stub
		dispose();
	}
}