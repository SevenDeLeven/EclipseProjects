package com.sevendeleven.files.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FileMain {
	
	
	
	public static void main(String[] args) {
		JFileChooser chooser = new JFileChooser("Choose a file");
		chooser.showOpenDialog(null);
		
		File f = chooser.getSelectedFile();
		if (f == null) {
			System.exit(0);
		}
		
		try {
		String contents = "";
		FileInputStream fis = new FileInputStream(f);
		Scanner sc = new Scanner(fis);
		while (sc.hasNext()) {
			contents += sc.nextLine() + "\n";
		}
		sc.close();
		fis.close();
		
		
		//Editor Frame
		JFrame frame = new JFrame("Editor");
		frame.setSize(500, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		//Scroll Pane creation
		BorderLayout layout = new BorderLayout();
		JPanel mainPanel = new JPanel(layout);
		frame.setContentPane(mainPanel);
		
		JTextArea textArea = new JTextArea(contents);
		JScrollPane scroll = new JScrollPane(textArea);
		mainPanel.add(scroll, BorderLayout.CENTER);
		
		//Save button
		FlowLayout buttonLayout = new FlowLayout();
		JPanel buttonPanel = new JPanel(buttonLayout);
		JButton save = new JButton("Save");
		buttonPanel.add(save);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		save.addActionListener((event) -> {
			try {
			PrintWriter writer = new PrintWriter(f);
			writer.write(textArea.getText());
			writer.close();
			frame.dispose();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});
		
		frame.setVisible(true);
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
