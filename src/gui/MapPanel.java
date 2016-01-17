package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import simulate.Location;

class MapPanel extends JPanel implements MouseListener{

	public static final int VIEW_OFFSET = 10;
	public static final int DOT_PITCH = 15;

	MapMediator mapMediator;

	public MapPanel(){
		this.setVisible(true);
		this.addMouseListener(this);
	}

	public void setMapMediator(MapMediator mapMediator){
		this.mapMediator = mapMediator;
	}

	public void setSize(){
		int w = (VIEW_OFFSET*2) + (mapMediator.getMapWidth() * DOT_PITCH);
		int h = (VIEW_OFFSET*2) + (mapMediator.getMapHeight() * DOT_PITCH);
		this.setPreferredSize(new Dimension(w, h));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, this.getWidth(), this.getHeight());

		// マップの描画
		try {

			ArrayList<Location> locationList = mapMediator.getLocationList();
			for (Location l : locationList) {
				int i = VIEW_OFFSET + l.getPoint().x * DOT_PITCH;
				int j = VIEW_OFFSET + l.getPoint().y * DOT_PITCH;
				switch (l.getType()) {
				case Location.TYPE_NORMAL_LOCATION:
					g2.setColor(Color.BLACK);
					g2.fillRect(i, j, 1, 1);
					break;
				case Location.TYPE_PATH_LOCATION:
					g2.setColor(Color.BLUE);
					g2.fillRect(i - 2, j - 2, 5, 5);
					break;
				case Location.TYPE_EXCLUDE_LOCATION:
					g2.setColor(Color.RED);
					g2.fillRect(i - 3, j - 3, 7, 7);
					break;
				default:
					break;
				}
			}

		} catch (NullPointerException ne) {
			ne.printStackTrace();
		}

		g2.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		int x = e.getPoint().x;
		int y = e.getPoint().y;
		//System.out.println("Clicked (x, y) = (" + x + ", " + y + ")");

		int dp = DOT_PITCH;
		if( ((x%dp)<=8||(x%dp)>=7) && ((y%dp)<=8||(y%dp)>=7)){
			int mx = x/dp;
			int my = y/dp;
			//System.out.println("clicked map point: (" + mx + ", " + my + ")");
			Location l = new Location(mx, my, Location.TYPE_NORMAL_LOCATION);

			switch(e.getButton()){
				//左クリック→経路地点化
				case MouseEvent.BUTTON1:
					mapMediator.setLocationType(l, Location.TYPE_PATH_LOCATION);
					//System.out.println("changed type : TYPE_PATH_LOCATION");
					break;

				//右クリック→ノーマル地点化
				case MouseEvent.BUTTON3:
					mapMediator.setLocationType(l, Location.TYPE_NORMAL_LOCATION);
					//System.out.println("changed type : TYPE_NORMAL_LOCATION");
					break;

				default:
					break;
			}
			this.repaint();
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
