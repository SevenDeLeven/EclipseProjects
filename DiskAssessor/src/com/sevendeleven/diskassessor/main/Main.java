package com.sevendeleven.diskassessor.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Main {
	
	public static Main instance;
	
	public JFrame frame;
	public JPanel mainPanel;

	public static volatile boolean in = false; //For prompt windows; the in variable is set to false when there is not a prompt
	public static volatile long size = 0;
	public static volatile String currentFileLoading = "";
	
	FileData driveData;
	FileData currentFolderData;
	
	static File currentFolder;
	
	static Font f;
	
	Map<Object, FileData> buttonData = new HashMap<Object, FileData>();
	
	ActionListener bl = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (buttonData.containsKey(e.getSource())) {
				FileData data = buttonData.get(e.getSource());
				if (data.directory) {
					currentFolderData = data;
					Main.instance.generateButtons();
					frame.repaint();
				} else {
					System.out.println("This is a file");
				}
			}
		}
		
	};
	
	public Main(JFrame frame) {
		this.frame = frame;
		this.mainPanel = new JPanel();
	}
	
	public static void getDrive() {
		f = new Font("Arial", Font.PLAIN, 12);
		in = true;
		JFrame window = new JFrame("Drive");
		window.setSize(400, 100);
		window.setResizable(false);
		
		
		JButton okayButton = new JButton("Select");
		
		
		
		JComboBox<String> select = new JComboBox<String>();
		okayButton.addActionListener((ActionEvent e) -> {
			String selected = select.getSelectedItem().toString();
			currentFolder = new File(selected);
			window.dispose();
			in = false;
		});
		GridLayout layout = new GridLayout(2,1);
		JPanel panel = new JPanel(layout);
		
		File[] roots = File.listRoots();
		for (File root : roots) {
			select.addItem(root.getAbsolutePath());
		}
		select.setEditable(false);
		panel.add(select);
		panel.add(okayButton);
		
		window.add(panel);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		in = false;
		
		getDrive();
		
		while (in) {
			
		}
		
		JFrame frame = new JFrame("Disk Assessor");
		JScrollPane scroll = new JScrollPane();
		Main main = new Main(frame);
		Main.instance = main;
		main.generateFileData();
		
		frame.setSize(new Dimension(800, 600));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		scroll.add(main.mainPanel);
		scroll.setViewportView(main.mainPanel);
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		frame.add(scroll);
		frame.setVisible(true);
		
		main.generateButtons();
		
	}
	
	public void generateFileData() {
		System.out.println("Generating file data");
		in = true;
		new Progress();
		driveData = new FileData(currentFolder);
		in = false;
		System.out.println("Done generating file data for " + driveData.numberOfFiles + " files");
	}
	
	public int limit(double in) {
		return (int)Math.max(0, Math.min(255, in));
	}
	
	public Color getColor(double size, double containerSize) {
		double red = Math.min(1.0, size/containerSize);
		double green = 1.0-red;
		double blue = 0;
		Color c = new Color(limit(255*red), limit(255*green), limit(255*blue));
		return c;
	}
	
	public String getSizeAsString(long size) {
		if (size < 1000L) {
			return (int)(size) + "B";
		} else if (size < 1000000L) {
			return (int)(size/1000.0) + "KB";
		} else if (size < 1000000000L) {
			return (int)(size/1000000.0) + "MB";
		} else if (size < 1000000000000L) {
			return (int)(size/1000000000.0) + "GB";
		} else {
			return (int)(size/1000000000000.0) + "TB";
		}
	}
	
	public JLabel createLabel(String text, int maxWidth) {
		JLabel label = new JLabel(text);
		label.revalidate();
		int endIndex = text.length();
		System.out.println(label.getBounds().width);
		Font f = new Font("Arial", Font.PLAIN, 12);
		FontMetrics fm = label.getFontMetrics(f);
		String ctext = text;
		while (fm.stringWidth(ctext) > maxWidth) {
			endIndex--;
			ctext = text.substring(0, endIndex) + "...";
			if (endIndex == 0) {
				label.setText("...");
				label.revalidate();
				return label;
			} else {
				label.setText(ctext);
				label.revalidate();
			}
		}
		return label;
	}
	
	public void generateButtons() {
		JPanel panel = mainPanel;
		panel.removeAll();
		buttonData.clear();
		FileData[] data = getCurrentFolderData();
		if (data.length == 0) {
			currentFolderData = currentFolderData.parentFolder;
			generateButtons();
			return;
		}
		/*
		GridLayout layout = new GridLayout(data.length, 1);
		panel.setLayout(layout);
		if (currentFolderData.parentFolder != null) {
			FileData d = currentFolderData.parentFolder;
			JButton fileButton = new JButton();
			fileButton.setText((d.directory ? "D" : "F") + " :: " + d.fileName + " :: " + d.getSizeInMB());
			fileButton.setSize(600, 50);
			fileButton.setBackground(Color.LIGHT_GRAY);
			buttonData.put(fileButton, d);
			fileButton.addActionListener(bl);
			panel.add(fileButton);
		}
		for (FileData d : data) {
			JButton fileButton = new JButton();
			fileButton.setText((d.directory ? "D" : "F") + " :: " + d.fileName + " :: " + getSizeAsString(d.size));
			buttonData.put(fileButton, d);
			fileButton.addActionListener(bl);
			fileButton.setSize(600, 50);
			fileButton.setBackground(getColor(d.getSizeInMB(), (d.parentFolder == null ? size : d.parentFolder.getSizeInMB())));
			panel.add(fileButton);
		}
		*/
		
		
//		TableModel dataModel = new AbstractTableModel() {
//			private static final long serialVersionUID = 1L;
//			private Object tableData[][];
//			public int getColumnCount() {
//				return 4;
//			}
//
//			@Override
//			public int getRowCount() {
//				return data.length+1;
//			}
//			
//			public AbstractTableModel generate() {
//				tableData = new Object[this.getRowCount()][this.getColumnCount()];
//				return this;
//			}
//
//			@Override
//			public Object getValueAt(int row, int col) {
//				return tableData[row][col];
//			}
//		}.generate();
		GridLayout layout = new GridLayout(data.length+1, 4);
		panel.setLayout(layout);
		
		
//		dataModel.setV
		if (currentFolderData.parentFolder != null) {
			FileData d = currentFolderData;
			JButton fileButton = new JButton();
			fileButton.setText((d.directory ? "Select" : "View"));
			buttonData.put(fileButton, d);
			fileButton.addActionListener(bl);
			fileButton.setSize(200, 50);
			fileButton.setBackground(getColor(d.getSizeInMB(), (d.parentFolder == null ? size : d.parentFolder.getSizeInMB())));
			panel.add(new JLabel(d.directory ? "Directory" : "File"));
			panel.add(createLabel(d.fileName, 200));
			panel.add(createLabel(getSizeAsString(d.size), 50));
			panel.add(fileButton);
		}
		for (int i = 0; i < data.length; i++) {
			FileData d = data[i];
			JButton fileButton = new JButton();
			fileButton.setText((d.directory ? "Select" : "View"));
			buttonData.put(fileButton, d);
			fileButton.addActionListener(bl);
			fileButton.setSize(200, 50);
			fileButton.setBackground(getColor(d.getSizeInMB(), (d.parentFolder == null ? size : d.parentFolder.getSizeInMB())));
			panel.add(new JLabel(d.directory ? "Directory" : "File"));
			panel.add(createLabel(d.fileName, 200));
			panel.add(createLabel(getSizeAsString(d.size), 50));
			panel.add(fileButton);
//			panel.add(fileButton);
		}
		
		panel.revalidate();
		panel.getParent().revalidate();
	}
	
	public void update() {
		
	}
	
	public FileData[] getCurrentFolderData() {
		if (currentFolderData == null) {
			currentFolderData = driveData;
		}
		return currentFolderData.subfiles;
	}
	
	public static String getCurrentFileLoading() {
		return new String(currentFileLoading);
	}
	
}
