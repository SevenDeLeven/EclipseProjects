package com.sevendeleven.computer.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {
	
	private String validChars = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-_=+[{]};:'\"\\|/?.>,<`~!@#$%^&*()";
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && Main.text.length() > 0) {
			Main.text = Main.text.substring(0, Main.text.length()-1);
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
			System.out.println(Main.text);
			System.exit(0);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if (validChars.indexOf(e.getKeyChar()) != -1) {
			Main.text += e.getKeyChar();
		}
	}
	
}
