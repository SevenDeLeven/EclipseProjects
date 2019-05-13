package com.sevendeleven.filebackup.main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	
	
	public static final byte BYTE_BEGINSHARE = 0x00;		//Sent when the user clicks 'upload'
	public static final byte BYTE_INFO_LOCATION = 0x10;		//Location data after the beginshare byte
	public static final byte BYTE_FILEDATA = 0x20;			//File data during the file upload process
	public static final byte BYTE_FILEEND = 0x21;			//End of file information, signifying the end
	public static final byte BYTE_CANCEL = 0x22;			//File canceled byte, when the user cancels
	public static final byte BYTE_DATA_STRBEGIN = 0x30; 	//String begin
	public static final byte BYTE_DATA_STREND = 0x31;		//String end
	public static final byte BYTE_DATA_INT = 0x32;			//Integer data
	public static final byte BYTE_ESCAPE_CHARACTER = '~';	//Escape character to escape the end byte
	
	public static volatile boolean cancel = false;
	public static volatile long progress = 0;
	public static volatile boolean cancelCompleted = false;
	
	static JFrame window;
	static JPanel panel;
	static ClientHandler cHandler;
	static final WindowListener windowListener = new WindowListener() {

		@Override
		public void windowActivated(WindowEvent arg0) {
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			Main.cancel = true;
			while (!Main.cancelCompleted) {
				
			}
			cHandler.close();
			window.dispose();
			System.exit(-1);
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			
		}
		
	};
	
	static FileData currentDirectoryLocal;
	static FileData currentDirectoryRemote;
	
	static ChooserPane localChooserPane;
	static ChooserPane remoteChooserPane;
	
	public static void main(String[] args) {
		
		for (String c : "s    t".split(" ")) {
			System.out.println(c);
		}
		
		System.exit(0);
		window = new JFrame("File Backup Tool");
		window.setSize(800, 600);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.addWindowListener(windowListener);
		
		panel = new JPanel();
		
		
		window.setVisible(true);
	}
	
	public static void updateLocalDirectory() {
		File file = new File(currentDirectoryLocal.getAbsolutePath());
		if (!file.isDirectory()) {
			System.err.println("Current local file directory is not a directory!");
			System.exit(-1);
		}
		
		File[] files = file.listFiles();
		FileData[] fileData = new FileData[files.length];
		for (int i = 0; i < files.length; i++) { 
			String filePath = files[i].getPath();
			String fileName = files[i].getName();
			boolean dir = files[i].isDirectory();
			fileData[i] = new FileData(filePath, fileName, dir, true);
		}
		localChooserPane.setInformation(fileData);
	}
	
	public static void updateRemoteDirectory() {
		cHandler.requestCDI();
	}
	
	public static void changeDirectory(FileData data) {
		if (!data.directory) {
			System.err.println("Tried to change directory to a file!");
			return;
		}
		if (data.local) {
			currentDirectoryLocal = data;
			updateLocalDirectory();
		} else {
			currentDirectoryRemote = data;
			updateRemoteDirectory();
		}
	}
	
}
