package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import algorithm.AlgorithmUtilities;
import simulate.Location;
import simulate.Map;

public class MainFrame extends JFrame implements ActionListener, MapMediator{

	//Model
	Map map;
	Option option;

	//View
	JMenuBar menubar;
	JScrollPane mapScrollPane;
	MapPanel mapPanel;

	MapMediator mapMediator;

	public MainFrame(){

		this.mapMediator = this;

		//メインフレーム
		this.setTitle("TSP");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setBounds(50, 50, 1300, 700);

		//メニューバー
		this.menubar = new JMenuBar();

		JMenu menuFile = new JMenu("File"); //ファイルメニュー

		JMenuItem itemNew  = new JMenuItem("New Map...");
		itemNew.setActionCommand("newMap");
		itemNew.addActionListener(this);
		menuFile.add(itemNew);

		JMenuItem itemLoad = new JMenuItem("Load Map...");
		itemLoad.setActionCommand("loadMap");
		itemLoad.addActionListener(this);
		menuFile.add(itemLoad);

		JMenuItem itemSave = new JMenuItem("Save Map...");
		itemSave.setActionCommand("saveMap");
		itemSave.addActionListener(this);
		menuFile.add(itemSave);

		this.menubar.add(menuFile);

		JMenu menuEdit  = new JMenu("Edit");//編集メニュー

		JMenuItem itemGenerate = new JMenuItem("Generate random location...");
		itemGenerate.setActionCommand("generate");
		itemGenerate.addActionListener(this);
		menuEdit.add(itemGenerate);

		JMenuItem itemClear = new JMenuItem("Clear Map...");
		itemClear.setActionCommand("clearMap");
		itemClear.addActionListener(this);
		menuEdit.add(itemClear);

		this.menubar.add(menuEdit);
		this.setJMenuBar(this.menubar);

		JMenu menuRun  = new JMenu("Run");//実行メニュー

		JMenuItem itemRun = new JMenuItem("run");
		itemRun.setActionCommand("run");
		itemRun.addActionListener(this);
		menuRun.add(itemRun);

		this.menubar.add(menuRun);

		//マップパネル
		this.mapScrollPane = new JScrollPane();
		this.mapPanel = new MapPanel();
		this.mapPanel.setMapMediator(this);
		this.mapScrollPane.setViewportView(this.mapPanel);
		this.add(this.mapScrollPane, BorderLayout.CENTER);

		//初期化
		this.mapMediator.createNewMap(75, 40);
		this.mapPanel.setSize();
		this.mapPanel.repaint();
		this.mapScrollPane.doLayout();

		this.validate();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new MainFrame();
	}

	public void setMapMediator(MapMediator mapMediator){
		this.mapMediator = mapMediator;
	}

	private void resolve(){

		Option option = GUIUtils.showOptionPane(this);

		if(option == null){
			return;
		}

		//this.setEnabled(false);
		// 1. make resultPanel
		  // A. make Label
		int w = (MapPanel.VIEW_OFFSET * 2) + (mapMediator.getMapWidth() * MapPanel.DOT_PITCH);
		int h = (MapPanel.VIEW_OFFSET * 2) + (mapMediator.getMapHeight() * MapPanel.DOT_PITCH);

		JFrame resultFrame = new JFrame("Result sp=" + option.k +
				", clustering=" + option.clusteringAlgorithm +
				", tsp=" + option.tspAlgorithm);
		resultFrame.setBounds(this.getLocation().x + 50, this.getLocation().y + 50, w+100, h+100);
		resultFrame.setLayout(new BorderLayout());
		resultFrame.setVisible(true);
		JScrollPane jsPane = new JScrollPane();
		resultFrame.add(jsPane);
		JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		resultFrame.add(statusBar, BorderLayout.SOUTH);
		resultFrame.validate();

		BufferedImage tspResultImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		JLabel tspResultLabel = new JLabel(new ImageIcon(tspResultImage));
		tspResultLabel.setHorizontalAlignment(JLabel.LEFT);
		tspResultLabel.setVerticalAlignment(JLabel.TOP);
		tspResultLabel.setPreferredSize(new Dimension(w, h));

		  // B. draw Label
		Graphics2D g2 = (Graphics2D)tspResultImage.getGraphics(); // ! -> g2 returns null until show something
		final Color[] COLORS = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.PINK, Color.CYAN,
				Color.MAGENTA, Color.ORANGE, Color.GRAY, Color.BLACK };
		try {
			g2.setBackground(Color.WHITE);
			g2.clearRect(0, 0, tspResultImage.getWidth(), tspResultImage.getHeight());

			g2.setColor(Color.BLACK);
			for(int i=0; i<this.mapMediator.getMapWidth(); i++){
				for(int j=0; j<this.mapMediator.getMapHeight(); j++){
					int x = MapPanel.VIEW_OFFSET + i * MapPanel.DOT_PITCH;
					int y = MapPanel.VIEW_OFFSET + j * MapPanel.DOT_PITCH;
					g2.fillRect(x, y, 1, 1);
				}
			}

			ArrayList<Location> locationList = mapMediator.getLocationList();
			g2.setColor(Color.BLUE);
			for (Location l : locationList) {
				int x = MapPanel.VIEW_OFFSET + l.getPoint().x * MapPanel.DOT_PITCH;
				int y = MapPanel.VIEW_OFFSET + l.getPoint().y * MapPanel.DOT_PITCH;
				g2.fillRect(x, y, 1, 1);
			}

		} catch (NullPointerException ne) {
			ne.printStackTrace();
		}
		g2.dispose();

		jsPane.setViewportView(tspResultLabel);
		jsPane.doLayout();

		//-> use SwingWorker later
		new Thread(new Runnable(){

			@Override
			public void run() {

				// 2. clustering
				  // A. do clustering in background thread.
				HashMap<Location, Integer> map = option.clusteringAlgorithm.cluster(mapMediator.getLocationList(), option.k);

				  // B. show clustering result
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						try {
							Graphics2D g2 = (Graphics2D)tspResultImage.getGraphics();

							for (Location l : map.keySet()) {
								int x = MapPanel.VIEW_OFFSET + (l.getPoint().x * MapPanel.DOT_PITCH);
								int y = MapPanel.VIEW_OFFSET + (l.getPoint().y * MapPanel.DOT_PITCH);
								g2.setColor(COLORS[map.get(l)]);
								g2.fillOval(x-3, y-3, 7, 7);
							}
							g2.dispose();
						} catch (NullPointerException ne) {
							ne.printStackTrace();
						}
						tspResultLabel.repaint();
					}

				});
				System.out.println("clustering has finished");

				// 3. tsp
				  // ! -> resolve tsp each clusetr
				for (int i=0; i<option.k; i++) {
					// A. make same cluster list
					ArrayList<Location> list = new ArrayList<Location>();
					for(Location l : map.keySet()){
						if(map.get(l) == i){
							list.add(l);
						}
					}

					// B. resolve tsp in background thread
					option.tspAlgorithm.sort(list);

					// C. show tsp result
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {

							double distance = 0;
							try {
								Graphics2D g2 = (Graphics2D)tspResultImage.getGraphics();

								//g2.setColor(COLORS[i]);
								g2.setColor(Color.BLACK);
								Point p1, p2;
								int dx1, dy1, dx2, dy2;
								for(int li=0; li<list.size()-1; li++){
									p1 = list.get(li).getPoint();
									p2 = list.get(li+1).getPoint();
									dx1 = MapPanel.VIEW_OFFSET + (p1.x * MapPanel.DOT_PITCH);
									dy1 = MapPanel.VIEW_OFFSET + (p1.y * MapPanel.DOT_PITCH);
									dx2 = MapPanel.VIEW_OFFSET + (p2.x * MapPanel.DOT_PITCH);
									dy2 = MapPanel.VIEW_OFFSET + (p2.y * MapPanel.DOT_PITCH);
									g2.drawLine(dx1, dy1, dx2, dy2);
									g2.drawString(Integer.valueOf(li).toString(), dx1, dy1);
									distance += AlgorithmUtilities.calcDistance(p1, p2);
								}

								p1 = list.get(0).getPoint();
								p2 = list.get(list.size()-1).getPoint();
								dx1 = MapPanel.VIEW_OFFSET + (p1.x * MapPanel.DOT_PITCH);
								dy1 = MapPanel.VIEW_OFFSET + (p1.y * MapPanel.DOT_PITCH);
								dx2 = MapPanel.VIEW_OFFSET + (p2.x * MapPanel.DOT_PITCH);
								dy2 = MapPanel.VIEW_OFFSET + (p2.y * MapPanel.DOT_PITCH);
								g2.drawLine(dx1, dy1, dx2, dy2); //lastPoint to FirstPoint
								g2.drawString(Integer.valueOf(list.size()-1).toString(), dx2, dy2); //LastPoint Number
								g2.drawRect(dx1-5, dy1-5, 9, 9);
								g2.drawRect(dx2-5, dy2-5, 9, 9);
								distance += AlgorithmUtilities.calcDistance(p1, p2);

								g2.dispose();
							} catch (NullPointerException ne) {
								ne.printStackTrace();
							}
							tspResultLabel.repaint();
							resultFrame.setTitle(resultFrame.getTitle() + ", distance=" + distance);
							System.out.println("distance : " + distance);
						}

					});

				}
				System.out.println("tsp resolve has finished");

			}

		}).start();

		// 4. release graphics & wait OK button clicked


	}

	private void saveMap(){

		JFileChooser fileChooser = new JFileChooser();
		int selected =  fileChooser.showSaveDialog(this);

		switch(selected){
			case JFileChooser.APPROVE_OPTION: //保存ボタン
				//直列化して保存する
				try{
					File saveFile = new File(fileChooser.getSelectedFile().toString() + ".tspmap");

					FileOutputStream fos = new FileOutputStream(saveFile);
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(map);

					oos.flush();

					oos.close();
					fos.close();

					System.out.println("system has saved tspmap...");

				}catch (IOException io){
					System.out.println("IOException occured");
				}
				break;

			case JFileChooser.CANCEL_OPTION: //取り消し, ×ボタン

				break;

			case JFileChooser.ERROR_OPTION: //エラー発生時
				break;

			default:
				break;
		}
	}

	private void loadMap(){
		JFileChooser fileChooser = new JFileChooser();
		int selected = fileChooser.showOpenDialog(this);

		switch(selected){
			case JFileChooser.APPROVE_OPTION: //開くボタンが選択された
				//直列化されたファイルを読み込む
				try{
					File loadFile = fileChooser.getSelectedFile();

					FileInputStream fis = new FileInputStream(loadFile);
					ObjectInputStream ois = new ObjectInputStream(fis);
					this.map = (Map)ois.readObject();

					ois.close();
					fis.close();

					System.out.println("system has loaded tspmap...");

				} catch(IOException e){
					System.out.println("IOException occured");

				} catch(ClassNotFoundException cnfe){
					System.out.println("maybe not tspmap.");
				}
				break;

			case JFileChooser.CANCEL_OPTION: //取り消し, ×ボタン
				break;

			case JFileChooser.ERROR_OPTION: //エラー発生時
				break;

			default:
				break;
		}
		this.mapPanel.setSize();
		this.mapPanel.repaint();
		this.mapScrollPane.doLayout();
	}

	private void newMap(){
		Dimension dimension = GUIUtils.showCreateNewMapOptionPane(this);

		if(dimension == null){
			return;
		}

		int w = dimension.width;
		int h = dimension.height;

		if((w<1) || (h<1)){
			return;
		}
		//System.out.println("(w, h) = (" + w + ", " + h + ")");

		this.mapMediator.createNewMap(w, h);
		this.mapPanel.setSize();
		this.mapPanel.repaint();
		this.mapScrollPane.doLayout();
	}

	private void clearMap(){
		this.mapMediator.clearLocation();
		this.mapPanel.repaint();
	}

	private void generateRandomLocation(){

		int locations = GUIUtils.showGenerateRaondomLocationPane(this);

		this.mapMediator.clearLocation();

		int max = this.mapMediator.getMapWidth() * this.mapMediator.getMapHeight();
		if((locations<1) ||(locations>max)){
			//System.out.println(max);
			return;
		}

		ArrayList<Location> tmpList = new ArrayList<Location>();
		for(int x=0; x<this.mapMediator.getMapWidth(); x++){
			for(int y=0; y<this.mapMediator.getMapHeight(); y++){
				tmpList.add(new Location(x, y));
			}
		}
		Collections.shuffle(tmpList);
		for(int i=0; i<locations; i++){
			this.mapMediator.addLocation(tmpList.get(i));
		}
		this.mapPanel.repaint();
	}

	@Override
	public boolean existsLocation(Location l) {
		return map.existsLocation(l);
	}

	@Override
	public ArrayList<Location> getLocationList() {
		return map.getLocationList();
	}

	@Override
	public void createNewMap(int mw, int mh) {
		this.map = new Map(mw, mh);
	}

	@Override
	public int getMapWidth() {
		return this.map.getMapWidth();
	}

	@Override
	public int getMapHeight() {
		return this.map.getMapHeight();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();
		System.out.println(command);

		switch(command){
		case "newMap":
			newMap();
			break;

		case "loadMap":
			loadMap();
			break;

		case "saveMap":
			saveMap();
			break;

		case "clearMap":
			clearMap();
			break;

		case "generate":
			generateRandomLocation();
			break;

		case "run":
			resolve();
			break;

		default:
			break;
		}
	}

	@Override
	public void clearLocation() {
		this.map.clearLocation();

	}

	@Override
	public boolean addLocation(Location l) {
		this.map.addLocation(l);
		return false;
	}

	@Override
	public boolean removeLocation(Location l) {
		this.map.removeLocation(l);
		return false;
	}


}
