package gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import simulate.Location;

class MapPanel extends JScrollPane implements MouseListener{

	/**
	 * MapPanel
	 * マップ表示するための機能を提供するクラス
	 *
	 */

	private static final long serialVersionUID = 1L;

	JLabel mapLabel;
	MapMediator mapMediator;

	public MapPanel(JLabel mapLabel){
		super(mapLabel);
		this.mapLabel = mapLabel;
		this.setVisible(true);
		this.setBorder(new LineBorder(Color.DARK_GRAY, 2, true));
		this.addMouseListener(this);
	}

	public void setMapMediator(MapMediator mapMediator){
		this.mapMediator = mapMediator;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		Point ep = e.getPoint();
		int x = e.getPoint().x;
		int y = e.getPoint().y;
		System.out.println("Clicked (x, y) = (" + x + ", " + y + ")");

		int bx = (this.getViewport().getWidth()/2) - (this.mapLabel.getIcon().getIconWidth()/2) -this.getViewport();
		int by = (this.getViewport().getHeight()/2) - (this.mapLabel.getIcon().getIconHeight()/2);
		System.out.println("BasePoint : (x, y) = (" + bx + ", " + by + ")");

		int dp = MapImage.DOT_PITCH;
		if( ((x%dp)<=8||(x%dp)>=7) && ((y%dp)<=8||(y%dp)>=7)){
			int mx = x/dp;
			int my = y/dp;
			//System.out.println("clicked map point: (" + mx + ", " + my + ")");
			Location l = new Location(mx, my, Location.TYPE_NORMAL_LOCATION);

			switch(e.getButton()){
				//左クリック→経路地点化
				case MouseEvent.BUTTON1:
					mapMediator.setLocationType(l, Location.TYPE_PATH_LOCATION);
					System.out.println("chaenged type : TYPE_PATH_LOCATION");
					break;

				//右クリック→ノーマル地点化
				case MouseEvent.BUTTON3:
					mapMediator.setLocationType(l, Location.TYPE_NORMAL_LOCATION);
					System.out.println("chaenged type : TYPE_NORMAL_LOCATION");
					break;

				default:
					break;
			}

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
