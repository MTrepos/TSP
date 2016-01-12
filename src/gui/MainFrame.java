package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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

public class MainFrame extends JFrame implements ActionListener, WindowListener, MapMediator{

	private static final long serialVersionUID = 1L;

	//Model
	Map map;
	Option option;

	//View
	JMenuBar menubar;
	MapPanel mapPanel; //ScrollPane
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
		updateMapPanel();
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
		//メインフレームを操作不可にしてcreateNewMapPanelの操作が終わるまで操作を移譲する
		this.setEnabled(false);

		CreateNewMapPanel createNewMapPanel = new CreateNewMapPanel();
		createNewMapPanel.setLocation(
				this.getLocation().x + (this.getWidth()/2) - (createNewMapPanel.getWidth()/2),
				this.getLocation().y + (this.getHeight()/2) - (createNewMapPanel.getHeight()/2)
				);
		createNewMapPanel.setMapMediator(this);
		createNewMapPanel.addWindowListener(this);
		createNewMapPanel.setVisible(true);
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
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();

		switch(command){
		case "newMap":
			//System.out.println("newMap");
			newMap();
			break;

		case "loadMap":
			//System.out.println("loadMap");
			loadMap();
			updateMapPanel();
			break;

		case "saveMap":
			//System.out.println("saveMap");
			saveMap();
			break;

		case "run":
			//System.out.println("run");
			resolve();
			break;

		default:
			break;
		}
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {

		String srcWindowName = e.getWindow().getName();

		switch(srcWindowName){
			case "CreateNewMapFrame":
				this.setEnabled(true); //操作を元に戻す
				updateMapPanel();
				break;

			case "OptionFrame":
				break;

			default:
				break;
		}

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getMapWidth() {
		return this.map.getMapWidth();
	}

	@Override
	public int getMapHeight() {
		return this.map.getMapHeight();
	}

}
