package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.algorithm.clustering.ClusteringAlgorithm;
import gui.algorithm.clustering.Kmean;
import gui.algorithm.tsp.DynamicProgramming;
import gui.algorithm.tsp.TSPAlgorithm;

public class GUIUtils {

	static Dimension showCreateNewMapOptionPane(Component parentComponent){

        JPanel panel = new JPanel( new GridLayout(2, 2) );
        panel.add( new JLabel("Map Width  dots : ") );
        JTextField mapWidthTextField = new JTextField(10);
        panel.add( mapWidthTextField );
        panel.add( new JLabel("Map Height dots : ") );
        JTextField mapHeightTextField = new JTextField(10);
        panel.add( mapHeightTextField );

		int selected = JOptionPane.showConfirmDialog(
				parentComponent,
				panel,
				"New Map...",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (selected == JOptionPane.OK_OPTION) {
			try {
				return new Dimension(
					Integer.valueOf(mapWidthTextField.getText()).intValue(),
					Integer.valueOf(mapHeightTextField.getText()).intValue()
				);
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(panel, "please input digits");
			}
		}

	    return null;
	}
	
	static Option showOptionPane(Component parentComponent){

		JPanel panel = new JPanel(new GridLayout(3, 3));
		panel.add(new JLabel("split value"));
		Integer numbers[] = new Integer[10];
		for (int i = 1; i <= 10; i++) {
			numbers[i - 1] = i;
		}
		JComboBox<Integer> numbersComboBox = new JComboBox<Integer>(numbers);
		panel.add(numbersComboBox);
		
		panel.add(new JLabel("clustering algorithm"));
		ClusteringAlgorithm clusteringMenu[] = { new Kmean() };
		JComboBox<ClusteringAlgorithm> clusteringComboBox = new JComboBox<ClusteringAlgorithm>(clusteringMenu);
		panel.add(clusteringComboBox);
		
		panel.add(new JLabel("TSP algorithm"));
		TSPAlgorithm TSPMenu[] = { new DynamicProgramming() };
		JComboBox<TSPAlgorithm> TSPComboBox = new JComboBox<TSPAlgorithm>(TSPMenu);
		panel.add(TSPComboBox);
		
		int selected = JOptionPane.showConfirmDialog(
				parentComponent, 
				panel, 
				"Select TSP Options...", 
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (selected == JOptionPane.OK_OPTION) {
			int k = numbers[numbersComboBox.getSelectedIndex()];
			ClusteringAlgorithm clusteringAlgorithm = clusteringMenu[clusteringComboBox.getSelectedIndex()];
			TSPAlgorithm tspAlgorithm = TSPMenu[TSPComboBox.getSelectedIndex()];
			
				return new Option(
						k,
						clusteringAlgorithm,
						tspAlgorithm
					);
		}

	    return null;
	}	

}
