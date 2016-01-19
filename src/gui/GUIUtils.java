package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import algorithm.clustering.ClusteringAlgorithm;
import algorithm.clustering.Kmean;
import algorithm.clustering.RandomClustering;
import algorithm.tsp.GreedyMethod;
import algorithm.tsp.NearestNeighbor;
import algorithm.tsp.RandomResolve;
import algorithm.tsp.TSPAlgorithm;
import algorithm.tsp.Watanabesan;

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
		// ! -> ADD NEW ALGORITHM HERE
		ClusteringAlgorithm clusteringMenu[] = {
				new Kmean(),
				new RandomClustering()
			};
		JComboBox<ClusteringAlgorithm> clusteringComboBox = new JComboBox<ClusteringAlgorithm>(clusteringMenu);
		panel.add(clusteringComboBox);

		panel.add(new JLabel("TSP algorithm"));
		// ! -> ADD NEW ALGORITHM HERE
		TSPAlgorithm TSPMenu[] = {
				new NearestNeighbor(),
				new GreedyMethod(),
				new Watanabesan(),
				new RandomResolve()
			};
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

			return new Option(k, clusteringAlgorithm, tspAlgorithm);
		}

	    return null;
	}

	static int showGenerateRaondomLocationPane(Component parentComponent){

        JPanel panel = new JPanel( new GridLayout(1, 1) );
        panel.add( new JLabel("How many Locations : ") );
        JTextField howManyLocationTextField = new JTextField(10);
        panel.add( howManyLocationTextField );

		int selected = JOptionPane.showConfirmDialog(
				parentComponent,
				panel,
				"New Map...",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (selected == JOptionPane.OK_OPTION) {
			try {
				return Integer.valueOf(howManyLocationTextField.getText()).intValue();
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(panel, "please input digits");
			}
		}

	    return 0;
	}

}
