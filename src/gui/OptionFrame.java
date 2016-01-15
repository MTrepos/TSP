package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class OptionFrame extends JFrame implements ActionListener{

	public OptionFrame(){

		this.setName("OptionFrame");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		this.setSize(250, 250);
		this.setResizable(false);
		this.setVisible(true);

		String numbers[] = new String[10];
		for(int i=1; i<=10; i++){
			numbers[i-1] = Integer.valueOf(i).toString();
		}
		JComboBox<String> numbersComboBox = new JComboBox<String>(numbers);

		String clusteringMenu[] = {
			"Kmeans"
		};
		JComboBox<String> clusteringComboBox = new JComboBox<String>(clusteringMenu);

		String TSPMenu[] = {
			"Dynamic Programming"
		};
		JComboBox<String> TSPComboBox = new JComboBox<String>(TSPMenu);


		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.LINE_START;

		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(new JLabel("selet split value..."), gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.add(numbersComboBox, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		this.add(new JLabel("select clustering algorithm..."), gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		this.add(clusteringComboBox, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		this.add(new JLabel("select TSP algorithm..."), gbc);
		gbc.gridx = 0;
		gbc.gridy = 5;
		this.add(TSPComboBox, gbc);

		this.validate();
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
