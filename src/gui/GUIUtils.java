package gui;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIUtils {

	static int[] showCreateNewMapOptionPane(Component parentComponent){

        JPanel panel = new JPanel( new GridLayout(2, 2) );
        panel.add( new JLabel("Map Width dots : ") );
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
				return new int[] {
					Integer.valueOf(mapWidthTextField.getText()), 9 };
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(parentComponent, "please input digits");
			}
		}

	    return null;
	}

}
