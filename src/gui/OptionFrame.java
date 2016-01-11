package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class OptionFrame extends JFrame {
	
	public OptionFrame(){
		//メインフレーム
		this.setTitle("Option");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setName("OptionFrame");
		this.setLayout(new BorderLayout());
		this.setSize(400, 300);
		this.setVisible(true);
	}
	
}
