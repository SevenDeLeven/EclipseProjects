package com.sevendeleven.sockets.server;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainServer {
	
	private static ServerSocket ssocket;
	
	private static JTextArea textArea;
	
	public static List<ConnectionHandler> connections = new ArrayList<ConnectionHandler>();
	
	public static void main(String[] args) {
		
		try {
		
		//Frame creation
		JFrame frame = new JFrame("Client stuff");
		frame.setSize(500, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//Panel
		BorderLayout layout = new BorderLayout();
		JPanel panel = new JPanel(layout);
		
		
		//Text Area
		textArea = new JTextArea();
		JScrollPane textScroll = new JScrollPane(textArea);
		panel.add(textScroll, BorderLayout.CENTER);
		
		//Button Panel
		FlowLayout buttonLayout = new FlowLayout();
		buttonLayout.setVgap(0);
		buttonLayout.setHgap(0);
		JPanel buttonPanel = new JPanel(buttonLayout);
		
		//Button
		JButton clearButton = new JButton("Clear");
		clearButton.setSize(100, 20);
		buttonPanel.add(clearButton);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		
		clearButton.addActionListener((event) -> {
			textArea.setText("");
		});
		
		//Frame set visible
		frame.setContentPane(panel);
		frame.setVisible(true);
		
		
		//Connection handling
		ssocket = new ServerSocket(2024);
		while (true) {
			Socket s = ssocket.accept();
			connections.add(new ConnectionHandler(s));
		}
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void addInformation(String text) {
		textArea.setText(textArea.getText() + text + "\n");
	}
	
}
