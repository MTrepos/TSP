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
		int w = (VIEW_OFFSET*2) + ((mapMediator.getMapWidth()-1) * DOT_PITCH);
		int h = (VIEW_OFFSET*2) + ((mapMediator.getMapHeight()-1) * DOT_PITCH);
		this.setPreferredSize(new Dimension(w, h));
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, this.getWidth(), this.getHeight());

		//draw Map
		try {
			//dot
			g2.setColor(Color.BLACK);
			for(int i=0; i<this.mapMediator.getMapWidth(); i++){
				for(int j=0; j<this.mapMediator.getMapHeight(); j++){
					int x = VIEW_OFFSET + i * DOT_PITCH;
					int y = VIEW_OFFSET + j * DOT_PITCH;
					g2.fillRect(x, y, 1, 1);
				}
			}

			//location
			g2.setColor(Color.BLUE);
			ArrayList<Location> locationList = mapMediator.getLocationList();
			for (Location l : locationList) {
				int x = VIEW_OFFSET + l.getPoint().x * DOT_PITCH;
				int y = VIEW_OFFSET + l.getPoint().y * DOT_PITCH;
				g2.fillOval(x-2, y-2, 5, 5);
			}

		} catch (NullPointerException ne) {
			ne.printStackTrace();
		}

		g2.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		int x = e.getPoint().x - VIEW_OFFSET;
		int y = e.getPoint().y - VIEW_OFFSET;
		//System.out.println("Clicked (x, y) = (" + x + ", " + y + ")");

		int dp = DOT_PITCH;
		int mx = (x%dp>=Double.valueOf(dp*4/5).intValue()) ? (x/dp)+1 :
					(x%dp<=Double.valueOf(dp*2/5).intValue()) ? (x/dp) : -1;
		int my = (y%dp>=Double.valueOf(dp*4/5).intValue()) ? (y/dp)+1 :
						(y%dp<=Double.valueOf(dp*2/5).intValue()) ? (y/dp) : -1;
		System.out.println("map point: (" + mx + ", " + my + ")");
		
		Location l = new Location(mx, my);

		switch(e.getButton()){
			//left click -> add location
			case MouseEvent.BUTTON1:
				mapMediator.addLocation(l);
				System.out.println("add location : (" + mx + ", " + my + ")");
				break;

			//right click -> remove location
			case MouseEvent.BUTTON3:
				mapMediator.removeLocation(l);
				System.out.println("remove location : (" + mx + ", " + my + ")");
				break;

			default:
				break;
		}
		this.repaint();
		
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
