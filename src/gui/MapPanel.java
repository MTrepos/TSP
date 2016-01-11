package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import simulate.Location;

class MapPanel extends JPanel implements MouseListener{
	
	public static final int INTERVAL = 15;
	private static final long serialVersionUID = 1L;
	
	MapMediator mapMediator;
	
	BufferedImage mapImage;
	
	public MapPanel(){
		this.setVisible(true);
		this.addMouseListener(this);
		
		this.mapImage = new BufferedImage(1280, 720, BufferedImage.TYPE_3BYTE_BGR);
	}	

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		//画面クリア
		try{
			Graphics2D ig2 = this.mapImage.createGraphics();
			
			ig2.setBackground(Color.WHITE);
			ig2.clearRect(0, 0, mapImage.getWidth(), mapImage.getHeight());
			
			ig2.setColor(Color.BLACK);
			for (int i = 0; i < this.getWidth(); i++) {
				for (int j = 0; j < this.getHeight(); j++) {
					ig2.fillRect(i*INTERVAL, j*INTERVAL, 1, 1);
				}
			}
			
			ig2.setColor(Color.BLUE);
			for (int i = 0; i < this.getWidth(); i++) {
				for (int j = 0; j < this.getHeight(); j++) {
					if(mapMediator.existsLocation(new Location(i, j, Location.TYPE_NORMAL_LOCATION))){
						ig2.fillRect(i*INTERVAL-2, j*INTERVAL-2, 5 ,5);
					}
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

	private void removeAllMouseListener(){
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

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getPoint().x;
		int y = e.getPoint().y;
		//System.out.println("(x, y) = (" + x + ", " + y + ")");
		
		if( ((x%INTERVAL)<=8||(x%INTERVAL)>=7) && ((y%INTERVAL)<=8||(y%INTERVAL)>=7)){
			int mx = x/INTERVAL;
			int my = y/INTERVAL;
			Location l = new Location(mx, my, Location.TYPE_NORMAL_LOCATION);
			
			if(mapMediator.existsLocation(l)){
				this.mapMediator.removeLocation(l);
				System.out.println("exists location then remove (" + mx + ", " + my + ")");
			}else {
				this.mapMediator.addLocation(l);
				System.out.println("not exists location so add (" + mx + ", " + my + ")");
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
