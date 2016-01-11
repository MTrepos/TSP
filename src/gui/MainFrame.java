package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import simulate.Location;
import simulate.Map;

public class MainFrame extends JFrame implements ActionListener, MapMediator{

	private static final long serialVersionUID = 1L;
	
	//Model
	Map map;
	
	//View
	JMenuBar menubar;
	MapPanel mapPanel;
	
	//Mediator
	MapMediator mapMediator;
	
	public MainFrame(){
		//Modelの初期化
		this.map = new Map(60, 45);
		
		//メインフレーム
		this.setTitle("TSP");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setBounds(100, 100, 1280, 720);
		this.setVisible(true);
		
		//メニューバー
		this.menubar = new JMenuBar();

		JMenu menuFile = new JMenu("File"); //ファイルメニュー

		JMenuItem itemNew  = new JMenuItem("New Map...");
		itemNew.setActionCommand("newMap");
		itemNew.addActionListener(this);
		menuFile.add(itemNew);
		
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
		
		this.add(mapPanel);
		
		this.validate();
		
		//Mediator
		this.mapMediator = this;
		this.mapPanel.mapMediator = this;
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}

	public void setMapMediator(MapMediator mapMediator){
		this.mapMediator = mapMediator;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand();
		
		switch(command){
		case "newMap":
			System.out.println("newMap");
			break;
		case "saveMap":
			System.out.println("saveMap");
			break;
		case "run":
			System.out.println("run");
			break;
		default:
			break;
		}
	}

	@Override
	public boolean existsLocation(Location l) {
		return map.existsLocation(l);
	}

	@Override
	public void setLocationType(Location l, int type) {
		map.setLocationType(l, type);
		this.mapPanel.repaint();
	}

	@Override
	public ArrayList<Location> getLocationList() {
		return map.getLocationList();
	}

}
