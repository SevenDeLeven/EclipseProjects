package com.sevendeleven.sockets.client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainClient {
	
	public static void main(String[] args) {
		try {
		Socket s = new Socket("localhost", 2024);
		PrintStream out = new PrintStream(s.getOutputStream());
		
		JFrame frame = new JFrame("Connect");
		frame.setSize(500, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BorderLayout layout = new BorderLayout();
		JPanel panel = new JPanel(layout);
		
		//Text Area
		JTextArea textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		//Button Panel
		FlowLayout buttonLayout = new FlowLayout();
		buttonLayout.setHgap(0);
		buttonLayout.setVgap(0);
		JPanel buttonPanel = new JPanel(buttonLayout);
		
		
		//Submit Button
		JButton button = new JButton("Submit");
		buttonPanel.add(button);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		button.addActionListener((event) -> {
			out.println(textArea.getText());
			textArea.setText("");
		});
		
		//Exit Button
		JButton exitButton = new JButton("Exit");
		buttonPanel.add(exitButton);
		exitButton.addActionListener((event) -> {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
		});
		
		frame.setContentPane(panel);
		frame.setVisible(true);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
