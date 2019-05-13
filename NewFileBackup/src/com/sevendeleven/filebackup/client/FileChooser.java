package com.sevendeleven.filebackup.client;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.sevendeleven.filebackup.client.gui.JImage;
import com.sevendeleven.filebackup.file.FileData;
import com.sevendeleven.filebackup.packet.PacketDirectoryInformation;
import com.sevendeleven.filebackup.packet.PacketRequestDirectory;

public class FileChooser extends JPanel {
	private static final long serialVersionUID = 1L;
	
	String localDirectory, remoteDirectory;
	
	List<FileData> localFiles = new ArrayList<>();
	List<FileData> remoteFiles = new ArrayList<>();
	JSplitPane filePanes = new JSplitPane();
	JPanel localPanel = new JPanel();
	JPanel remotePanel = new JPanel();
	
	public FileChooser(FileData localLocation, FileData remoteLocation) {
		this.add(filePanes);
		GridLayout localLayout = new GridLayout(1,1);
		localPanel.setLayout(localLayout);
		GridLayout remoteLayout = new GridLayout(1,1);
		remotePanel.setLayout(remoteLayout);
		filePanes.setLeftComponent(localPanel);
		filePanes.setRightComponent(remotePanel);
		this.localDirectory = localLocation.getDirectory();
		this.remoteDirectory = remoteLocation.getDirectory();
		init(localLocation, remoteLocation);
	}
	
	private void init(FileData localLocation, FileData remoteLocation) {
		getLocalDirectory(localLocation);
		getRemoteDirectory(remoteLocation);
	}
	
	private void updatePanel(JPanel panel, List<FileData> data) {
		panel.removeAll();
		for (int i = 0; i < data.size(); i++) {
			GridBagLayout gbl = new GridBagLayout();
			JPanel filePanel = new JPanel();
			filePanel.setLayout(gbl);
			JImage image = new JImage(ClientMain.folderImage);
			image.setPreferredSize(new Dimension(10,10));
			filePanel.add(image);
			JLabel name =  new JLabel(data.get(i).getName());
			filePanel.add(name);
			panel.add(filePanel);
		}
	}
	
	private void getLocalDirectory(FileData location) {
		localFiles.clear();
		for (File f : location.getFile().listFiles()) {
			localFiles.add(new FileData(f));
		}
		updatePanel(localPanel, localFiles);
	}
	
	private void getRemoteDirectory(FileData location) {
		remoteFiles.clear();
		PacketRequestDirectory prd = new PacketRequestDirectory(location.getFullPath());
		ClientMain.cmain.sendPacket(prd);
	}
	
	public void setRemoteDirectoryInformation(PacketDirectoryInformation pdi) {
		FileData[] files = pdi.getFiles();
		for (int i = 0; i < files.length; i++) {
			remoteFiles.add(files[i]);
		}
		updatePanel(remotePanel, remoteFiles);
	}
	
}
