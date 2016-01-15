package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import simulate.Location;
import simulate.Map;
import simulate.Option;

public class MainFrame extends JFrame implements ActionListener, MapMediator{

	private static final long serialVersionUID = 1L;

	//Model
	Map map;
	Option option;

	//View
	JMenuBar menubar;
	MapPanel mapPanel;
	MapImage mapImage;

	MapMediator mapMediator;

	public MainFrame(){

		this.mapMediator = this;

		//メインフレーム
		this.setTitle("TSP");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setBounds(500, 200, 800, 600);
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

		//マップ
		mapMediator.createNewMap(20, 20);
		this.validate();
	}

	public static void main(String[] args) {
		new MainFrame();
	}

	public void setMapMediator(MapMediator mapMediator){
		this.mapMediator = mapMediator;
	}

	private void resolve(){
		OptionFrame optionPanel = new OptionFrame();
		optionPanel.setLocation
		(this.getLocation().x + (this.getWidth()/2) - (optionPanel.getWidth()/2),
				this.getLocation().y + (this.getHeight()/2) - (optionPanel.getHeight()/2));
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
	}

	private void newMap(){

		int[] wh = GUIUtils.showCreateNewMapOptionPane(this);

		if(wh == null){
			System.out.println("wh == null");
			return;
		}

		System.out.println("(w, h) = (" + wh[0] + ", " + wh[1] + ")");
	}

	private void updateMapPanel(){

		if(mapPanel != null) remove(mapPanel);

		int w = (MapImage.VIEW_OFFSET * 2) + (this.mapMediator.getMapWidth() * MapImage.DOT_PITCH);
		int h = (MapImage.VIEW_OFFSET * 2) + (this.mapMediator.getMapHeight() * MapImage.DOT_PITCH);

		this.mapImage = new MapImage(w, h);
		this.mapImage.setMapMediator(this);
		mapImage.update();

		this.mapPanel = new MapPanel(new JLabel(new ImageIcon(this.mapImage)));
		this.mapPanel.setMapMediator(this);
		this.mapPanel.repaint();

		this.add(mapPanel);

		this.validate();
		System.out.println("update MapPanel...");
	}

	@Override
	public boolean existsLocation(Location l) {
		return map.existsLocation(l);
	}

	@Override
	public void setLocationType(Location l, int type) {
		map.setLocationType(l, type);
		this.mapImage.update();
		this.mapPanel.repaint();
	}

	@Override
	public ArrayList<Location> getLocationList() {
		return map.getLocationList();
	}

	@Override
	public void createNewMap(int mw, int mh) {
		this.map = new Map(mw, mh);
		updateMapPanel();
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
			updateMapPanel();
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
