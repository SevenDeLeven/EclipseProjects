package com.sevendeleven.chatprogram.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sevendeleven.chatprogram.protocol.packet.Packet;
import com.sevendeleven.chatprogram.protocol.packet.PacketClientConnect;
import com.sevendeleven.chatprogram.protocol.packet.PacketClientDisconnect;
import com.sevendeleven.chatprogram.protocol.packet.PacketClientInformation;
import com.sevendeleven.chatprogram.protocol.packet.PacketMessage;
import com.sevendeleven.chatprogram.protocol.packet.PacketMessageClient;
import com.sevendeleven.chatprogram.protocol.packet.PacketTranslator;

public class ClientMain implements WindowListener {
	
	public static boolean running = true;
	
	public static ClientMain instance;
	
	public Socket socket;
	public ClientConnectionHandler conHandler;
	
	public JFrame frame;
	public JTextArea messageArea;
	public JPanel mainPanel;
	public DefaultListModel<String> userListModel;
	public JList<String> userList;
	
	public List<User> users = new ArrayList<>();
	
	public static void main(String[] args) {
		instance = new ClientMain();
		instance.start();
	}
	
	public void start() {
		frame = new JFrame("Message Service");
		frame.setSize(600, 400);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		frame.addWindowListener(this);
		
		//Panel creation
		BorderLayout layout = new BorderLayout();
		mainPanel = new JPanel(layout);
		
		//Message Panel
		BorderLayout messagePanelLayout = new BorderLayout();
		JPanel messagePanel = new JPanel(messagePanelLayout);
		mainPanel.add(messagePanel, BorderLayout.CENTER);
		
		//MessageArea
		messageArea = new JTextArea();
		messageArea.setEditable(false);
		JScrollPane messagePane = new JScrollPane(messageArea);
		messagePanel.add(messagePane, BorderLayout.CENTER);
		
		//Text field
		JTextField textField = new JTextField();
		messagePanel.add(textField, BorderLayout.SOUTH);
		textField.addActionListener((event) -> {
			String message = textField.getText();
			textField.setText("");
			PacketMessageClient pmc = new PacketMessageClient(message);
			this.sendPacket(pmc);
		});
		
		//Usernames List
		userListModel = new DefaultListModel<>();
		userList = new JList<>(userListModel);
		
		userList.setPreferredSize(new Dimension(100, 500));
		mainPanel.add(userList, BorderLayout.WEST);
		
		
		frame.setContentPane(mainPanel);
		frame.setVisible(true);
		
		try {
			socket = new Socket("localhost", 2024);
			conHandler = new ClientConnectionHandler(socket);
			sendPacket(new PacketClientInformation(JOptionPane.showInputDialog("What is your username?")));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
	
	
	/**
	 * Adds a message 
	 * @param message
	 */
	public void addMessage(String message) {
		messageArea.setText(messageArea.getText()+"\n"+message);
	}
	
	public User getUser(int id) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getID() == id) {
				return users.get(i);
			}
		}
		return null;
	} 
	
	public void sendPacket(Packet p) {
		byte[] data = PacketTranslator.interpretPacket(p);
		conHandler.getSend().sendBytes(data);
	}
	
	public void usePacket(Packet p) {
		if (p instanceof PacketClientConnect) {
			PacketClientConnect pcc = (PacketClientConnect) p;
			System.out.println("Received packet client connect");
			User user = new User(pcc.getUserID(), pcc.getUsername());
			System.out.println(user.getID());
			System.out.println(user.getUsername());
			users.add(user);
			userListModel.addElement(user.getUsername());
		} else if (p instanceof PacketClientDisconnect) {
			PacketClientDisconnect pcd = (PacketClientDisconnect) p;
			User user = null;
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getID() == pcd.getUserID()) {
					user = users.get(i);
					users.remove(i);
					break;
				}
			}
			if (user == null) {
				System.err.println("User did not exist!");
				return;
			}
			String username = user.getUsername();
			for (int i = 0; i < userListModel.getSize(); i++) {
				if (userListModel.getElementAt(i).equals(username)) {
					userListModel.removeElementAt(i);
					break;
				}
			}
			addMessage("Goodbye, " + username + "!");
		} else if (p instanceof PacketMessage) {
			PacketMessage pm = (PacketMessage) p;
			String message = pm.getMessage();
			User user = getUser(pm.getUserID());
			if (user == null) {
				System.out.println("user does not exist!");
				return;
			}
			addMessage("<" + user.getUsername() + "> " + message);
		} else {
			System.err.println("Invalid packet received");
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
		ClientMain.running = false;
		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame.dispose();
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
