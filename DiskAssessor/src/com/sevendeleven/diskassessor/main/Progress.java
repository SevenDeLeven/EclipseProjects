package com.sevendeleven.diskassessor.main;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Progress implements Runnable {
	
	Thread thread, mainThread;
	
	
	JProgressBar progressBar;
	JLabel currentFileLabel;
	JFrame frame;
	
	long max;
	
	public Progress() {
		mainThread = Thread.currentThread();
		frame = new JFrame("Progress");
		frame.setSize(500, 100);
		frame.setResizable(false);
		max = Main.currentFolder.getTotalSpace()-Main.currentFolder.getUsableSpace();
		progressBar = new JProgressBar(0, (int)(max/1000000.0));
		progressBar.setSize(500, 50);
		progressBar.setStringPainted(true);
		JLabel loading = new JLabel("loading");
		currentFileLabel = new JLabel("");
		
		GridLayout layout = new GridLayout(3, 1);
		JPanel panel = new JPanel(layout);
		panel.add(loading);
		panel.add(currentFileLabel);
		panel.add(progressBar);
		
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		long now = System.currentTimeMillis();
		long last = now;
		double delta = 0;
		double tps = 20.0;
		while (Main.in) {
			last = now;
			now = System.currentTimeMillis();
			delta += ((now-last)*tps)/1000.0;
			if (delta >= 1.0) {
				delta = 0;
				update();
			}
		}
		frame.dispose();
	}
	
	public void update() {
		if (Main.size > max) {
			System.out.println("Size greater than max by " + (Main.size-max));
		}
		this.currentFileLabel.setText(Main.getCurrentFileLoading());
		this.progressBar.setValue((int)(Main.size/1000000.0));
		this.progressBar.repaint();
	}

}
