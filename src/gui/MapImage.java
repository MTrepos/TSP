package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import simulate.Location;

public class MapImage extends BufferedImage {

	public static final int VIEW_OFFSET = 10;
	public static final int DOT_PITCH = 15;

	private static

	MapMediator mapMediator;

	public MapImage(int width, int height){
		super(width, height, BufferedImage.TYPE_3BYTE_BGR);
	}

	public void setMapMediator(MapMediator mapMediator){
		this.mapMediator = mapMediator;
	}

	public void update(){
		//マップの描画
		try{
			//オフスクリーン(BufferedImage)への描画
			Graphics2D ig2 = this.createGraphics();

			ig2.setBackground(Color.WHITE);
			ig2.clearRect(0, 0, this.getWidth(), this.getHeight());

			ArrayList<Location> locationList = mapMediator.getLocationList();
			for(Location l : locationList) {
				int i = VIEW_OFFSET + l.getPoint().x * DOT_PITCH;
				int j = VIEW_OFFSET + l.getPoint().y * DOT_PITCH;
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
	}

}
