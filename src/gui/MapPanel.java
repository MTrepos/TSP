package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import simulate.Location;

class MapPanel extends JPanel implements MouseListener{
	
	public static final int VIEW_OFFSET = 10;
	public static final int INTERVAL = 15;
	private static final long serialVersionUID = 1L;
	
	MapMediator mapMediator;
	
	BufferedImage mapImage;
	
	public MapPanel(){
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.addMouseListener(this);
		
		this.mapImage = new BufferedImage(1920, 1080, BufferedImage.TYPE_3BYTE_BGR);
//		ImageIcon icon = new ImageIcon(mapImage);
//		JLabel label = new JLabel(icon);
//		JScrollPane scrollPane = new JScrollPane(label);
//		add(scrollPane, BorderLayout.CENTER);
	}	

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		//マップの描画
		try{
			//オフスクリーン(BufferedImage)への描画
			Graphics2D ig2 = this.mapImage.createGraphics();
			
			ig2.setBackground(Color.WHITE);
			ig2.clearRect(0, 0, mapImage.getWidth(), mapImage.getHeight());			
			
			ArrayList<Location> locationList = mapMediator.getLocationList();
			for(Location l : locationList) {
				int i = VIEW_OFFSET + l.getPoint().x * INTERVAL;
				int j = VIEW_OFFSET + l.getPoint().y * INTERVAL;
				switch(l.getType()){
					case Location.TYPE_NORMAL_LOCATION:
						ig2.setColor(Color.BLACK);
						ig2.fillRect(i, j, 1, 1);
						break;
					case Location.TYPE_PATH_LOCATION:
						ig2.setColor(Color.BLUE);
						ig2.fillRect(i-2, j-2, 5, 5);
						break;
					case Location.TYPE_EXCLUDE_LOCATION:
						ig2.setColor(Color.RED);
						ig2.fillRect(i-3, j-3, 7, 7);
						break;
					default:
						break;
				}
			}
			
			ig2.dispose();
			
		} catch(NullPointerException ne){
			System.out.println("mapImage is null");
		}
		
		g2.drawImage(mapImage, 0, 0, null);

		g2.dispose();
	}
	
	public void setMapMediator(MapMediator mapMediator){
		this.mapMediator = mapMediator;
	}

/*	private void removeAllMouseListener(){
		for(MouseListener ml : getMouseListeners()){
			removeMouseListener(ml);
		}
		for(MouseMotionListener mml : getMouseMotionListeners()){
			removeMouseMotionListener(mml);
		}
		for(MouseWheelListener mwl : getMouseWheelListeners()){
			removeMouseWheelListener(mwl);
		}
	}
	
	private void setMouseAdapter(MouseAdapter ma){
        addMouseListener(ma);
        addMouseMotionListener(ma);
        addMouseWheelListener(ma);
	}
*/
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getPoint().x;
		int y = e.getPoint().y;
		//System.out.println("(x, y) = (" + x + ", " + y + ")");
		
		if( ((x%INTERVAL)<=8||(x%INTERVAL)>=7) && ((y%INTERVAL)<=8||(y%INTERVAL)>=7)){
			int mx = x/INTERVAL;
			int my = y/INTERVAL;
			System.out.println("clicked map point: (" + mx + ", " + my + ")");
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
