package com.sevendeleven.jsonchat.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;

import com.sevendeleven.jsonchat.packet.PacketJoin;
import com.sevendeleven.jsonchat.packet.PacketLeave;
import com.sevendeleven.jsonchat.packet.PacketMessage;

import de.grobmeier.jjson.JSONHelper;
import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.convert.JSONDecoder;

public class ClientMain {
	
	private Socket socket;
	private ReceiveThread receiveThread;
	private SendThread sendThread;
	
	private JFrame frame;
	private JPanel mainPanel;
	private JTextArea messageArea;
	private JList<String> userList;
	private ListModel<String> userListModel;
	
	private Map<Integer, String> userMap = new HashMap<>();
	
	public static void main(String[] args) {
		(new ClientMain()).start();
	}
	
	public void start() {
		
		frame = new JFrame("Message Service");
		frame.setSize(600, 400);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
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
			
			//SENDING MESSAGE
		});
		
		//Usernames List
		userListModel = new DefaultListModel<>();
		userList = new JList<>(userListModel);
		
		userList.setPreferredSize(new Dimension(100, 500));
		mainPanel.add(userList, BorderLayout.WEST);
		
		
		frame.setContentPane(mainPanel);
		frame.setVisible(true);
		
		try {
		socket = new Socket("localhost", 2026);
		
		receiveThread = new ReceiveThread();
		sendThread = new SendThread();
		
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public class ReceiveThread implements Runnable {
		
		Thread thread;
		public ReceiveThread() {
			thread = new Thread(this, "ReceiveThread");
			thread.start();
		}
		
		public void run() {
			Scanner sc = new Scanner(socket.getInputStream());
			while (true) {
				String next = sc.nextLine();
				JSONDecoder dec = new JSONDecoder(next);
				JSONObject packet = (JSONObject) dec.decode();
				String type = JSONHelper.getString(packet, "ptype");
				switch (type) {
				case "msg":
					PacketMessage pm = new PacketMessage(packet);
					break;
				case "join":
					PacketJoin pj = new PacketJoin(packet);
					if (!userMap.containsKey(pj.getId())) {
						
					} else {
						addMessage("Error: User with id " + pj.getId() + " already exists! OLD USER: " + userMap.get(pj.getId()) + ", NEW USER: " + pj.getUsername());
					}
					break;
				case "leave":
					PacketLeave pl = new PacketLeave(packet);
					if (userMap.containsKey(pl.getId())) {
						addMessage(userMap.get(pl.getId()) + " has left the server!");
						userListMo
						userMap.remove(pl.getId());
					} else {
						addMessage("Error: User with id " + pl.getId() + " left, but was not in the list!");
					}
					
					break;
				}
			}
		}
		
	}
	
	public void addMessage(String message) {
		messageArea.setText(messageArea.getText() + message + "\n");
	}
	
	public class SendThread implements Runnable {
		
		Thread thread;
		public SendThread() {
			thread = new Thread(this, "SendThread");
			thread.start();
		}
		
		public void run() {
			
		}
		
	}
	
}
