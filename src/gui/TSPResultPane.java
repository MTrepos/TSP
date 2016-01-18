package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

import javax.swing.JPanel;

import simulate.Location;

class TSPResultPanel extends JPanel{

	public static final int VIEW_OFFSET = 10;
	public static final int DOT_PITCH = 15;

	HashMap<Location, Integer> resultMap;

	MapMediator mapMediator;

	private static final Color[] COLORS = {
		Color.RED,
		Color.GREEN,
		Color.BLUE,
		Color.YELLOW,
		Color.PINK,
		Color.CYAN,
		Color.MAGENTA,
		Color.ORANGE,
		Color.GRAY,
		Color.BLACK
	};

	public TSPResultPanel(HashMap<Location, Integer> resultMap){
		this.resultMap = resultMap;
		this.setVisible(true);
	}

	public void setMapMediator(MapMediator mapMediator){
		this.mapMediator = mapMediator;
	}


	public void setSize(){
		int w = (VIEW_OFFSET*2) + (mapMediator.getMapWidth() * DOT_PITCH);
		int h = (VIEW_OFFSET*2) + (mapMediator.getMapHeight() * DOT_PITCH);
		this.setPreferredSize(new Dimension(w, h));
	}

	public void paintClusterResult(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, this.getWidth(), this.getHeight());

		// マップの描画
		try {
			//draw point with its color
			for(Location l : this.resultMap.keySet()){
				int x = VIEW_OFFSET + (l.getPoint().x * DOT_PITCH);
				int y = VIEW_OFFSET + (l.getPoint().y * DOT_PITCH);
				g2.setColor(COLORS[this.resultMap.get(l)]);
				g2.fillOval(x, y, 7, 7);
			}

		} catch (NullPointerException ne) {
			ne.printStackTrace();
		}

		g2.dispose();
	}

}