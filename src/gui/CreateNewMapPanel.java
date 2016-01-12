package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CreateNewMapPanel extends JFrame implements ActionListener{
	
	MapMediator mapMediator;
	
	private JLabel labels[];
	private JFormattedTextField textFields[];
	private JButton buttons[];
	
	public CreateNewMapPanel(){
		this.setTitle("New Map ...");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setName("CreateNewMapFrame");
		this.setLayout(new BorderLayout());
		this.setSize(240, 180);
		//this.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		this.add(panel, BorderLayout.CENTER);
		
		this.labels = new JLabel[2];
		this.labels[0] = new JLabel("x dot :");
		this.labels[1] = new JLabel("y dot :");
		
		this.textFields = new JFormattedTextField[2];
		DecimalFormat df = new DecimalFormat("###"); //入力制限
		df.setMaximumFractionDigits(3);
		for(int i=0; i<textFields.length; i++){
			textFields[i] = new JFormattedTextField(df);
		}

		this.buttons = new JButton[2];
		this.buttons[0] = new JButton("cancel");
		this.buttons[0].setActionCommand("cancel");
		this.buttons[1] = new JButton("OK");
		this.buttons[1].setActionCommand("OK");
		
		//配置
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		
		gbc.ipadx = 30;
		gbc.ipady = 15;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(labels[0], gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;		
		panel.add(labels[1], gbc);
		
		gbc.ipadx = 40;
		gbc.ipady = 10;		
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel.add(textFields[0], gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;		
		panel.add(textFields[1], gbc);
		
		gbc.ipadx = 5;
		gbc.ipady = 0;
		gbc.gridx = 2;
		gbc.gridy = 2;
		panel.add(buttons[0], gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		panel.add(buttons[1], gbc);
		
		buttons[0].addActionListener(this);
		buttons[1].addActionListener(this);

	}

	public void setMapMediator(MapMediator mapMediator){
		this.mapMediator = mapMediator;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		switch(command){
			case "cancel":
				this.dispose();
				break;
				
			case "OK":
				try{
					int mw = Integer.parseInt(textFields[0].getText());
					int mh = Integer.parseInt(textFields[1].getText());
					this.mapMediator.createNewMap(mw, mh);
					
					this.dispose();
				} catch(NumberFormatException nfe){
					new JOptionPane().showMessageDialog(this, "please input digits");
				}
				break;
				
			default:
				break;
		}
	}
	
}
