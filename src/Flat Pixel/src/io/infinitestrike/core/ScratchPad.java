package io.infinitestrike.core;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ScratchPad extends JPanel {

	/**
	 * Create the panel.
	 */
	public ScratchPad() {
		setSize(300,126);
		setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(10, 26, 280, 20);
		add(comboBox);
		
		JCheckBox chckbxFullscreen = new JCheckBox("FullScreen");
		chckbxFullscreen.setBounds(10, 53, 97, 23);
		add(chckbxFullscreen);
		
		JCheckBox chckbxVsync = new JCheckBox("VSync");
		chckbxVsync.setBounds(109, 53, 75, 23);
		add(chckbxVsync);
		
		JButton btnLaunch = new JButton("Skip");
		btnLaunch.setBounds(201, 92, 89, 23);
		add(btnLaunch);
		
		JLabel lblResolution = new JLabel("Resolution");
		lblResolution.setBounds(10, 11, 97, 14);
		add(lblResolution);
		
		JButton btnLaunch_1 = new JButton("Launch");
		btnLaunch_1.setBounds(102, 92, 89, 23);
		add(btnLaunch_1);
		
		JCheckBox chckbxDebugMode = new JCheckBox("Debug Mode");
		chckbxDebugMode.setBounds(193, 53, 97, 23);
		add(chckbxDebugMode);
	}
}
