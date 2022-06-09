package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class HauptFenster {
	//Frames, Panel, etc.
	//MainFenster
	private JFrame HauptFenster;
	
	private JPanel contentPanel = new JPanel();
	
	//MainMenuBar
	private JMenuBar MmenuBar = new JMenuBar();
	
	//MenuButtons
	private JMenu JMStudListChange = new JMenu("Kurs verwalten");
	private JMenu JMHelp = new JMenu("Hilfe");
	
	//ScrollPanes
	private JScrollPane ListScrollPane = new JScrollPane();
	
	//Model der Liste
//	private static DefaultListModel<E> ListModel = new DefaultListModel<>();
	
	//Liste
//	private static JList<E> List = new JList<>(ListModel);
	
	//Panel Überschrift
	private TitledBorder ListTitledBorder = BorderFactory.createTitledBorder("KursTabelle");
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Methoden
	public void MainFenster() {
		//HauptFenster Eigenschaften
		HauptFenster = new JFrame("Überschrift");
		HauptFenster.setSize(1100, 600);
		HauptFenster.setLayout(new BorderLayout());
		HauptFenster.setVisible(true);
		HauptFenster.add(MmenuBar, BorderLayout.NORTH);
		
		//MenuBar Inhalt
		MmenuBar.add(JMStudListChange);
		MmenuBar.add(JMHelp);
		
		//ActionListener hinzugefügt
		JMStudListChange.addActionListener(new ActChangeList());
		
		//ContentPanel Eigenschaften
		HauptFenster.add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout());
		contentPanel.setPreferredSize(new Dimension(700, 800));
		contentPanel.setBorder(new LineBorder(Color.black, 1));
		
		//contentPanel Inhalt
		contentPanel.add(ListScrollPane, BorderLayout.NORTH);
		ListScrollPane.setPreferredSize(new Dimension(contentPanel.getSize().width, 700));
		ListTitledBorder.setTitlePosition(TitledBorder.TOP);
		ListTitledBorder.setBorder(new LineBorder(Color.black, 1));
		ListScrollPane.setBorder(ListTitledBorder);
//		ListScrollPane.setViewportView(List);
//		List.setModel(ListModel);
		
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Actionlistener
	class ActChangeList implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
