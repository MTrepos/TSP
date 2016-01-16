package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import simulate.Location;
import simulate.Map;

public class MainFrame extends JFrame implements ActionListener, MapMediator{

	private static final long serialVersionUID = 1L;

	//Model
	Map map;
	Option option;

	//View
	JMenuBar menubar;
	MapPanel mapPanel;

	MapMediator mapMediator;

	public MainFrame(){

		this.mapMediator = this;

		//メインフレーム
		this.setTitle("TSP");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setBounds(200, 100, 1200, 900);
		this.setVisible(true);

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

		JMenu menuRun  = new JMenu("Run");//実行メニュー

		JMenuItem itemRun = new JMenuItem("run");
		itemRun.setActionCommand("run");
		itemRun.addActionListener(this);
		menuRun.add(itemRun);

		this.menubar.add(menuRun);

		this.setJMenuBar(this.menubar);

		//マップパネル
		this.mapPanel = new MapPanel();
		this.mapPanel.setMapMediator(this);
		this.add(this.mapPanel, BorderLayout.CENTER);
		
		//初期化作業
		this.mapMediator.createNewMap(75, 50);
		this.mapPanel.repaint();
		
		this.validate();
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
		
		// 1. clustering
		HashMap<Location, Integer> map = option.clusteringAlgorithm.cluster(mapMediator.getLocationList(), option.k);
		
		
		
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
		this.mapPanel.repaint();
	}

	private void newMap(){
		Dimension dimension = GUIUtils.showCreateNewMapOptionPane(this);
		
		if(dimension == null){
			System.out.println("dimension == null");
			return;
		}
		
		int w = dimension.width;
		int h = dimension.height;
		System.out.println("(w, h) = (" + w + ", " + h + ")");
		this.mapMediator.createNewMap(w, h);
		this.mapPanel.repaint();
	}

	@Override
	public boolean existsLocation(Location l) {
		return map.existsLocation(l);
	}

	@Override
	public void setLocationType(Location l, int type) {
		map.setLocationType(l, type);
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

		case "run":
			resolve();
			break;

		default:
			break;
		}
	}

}
