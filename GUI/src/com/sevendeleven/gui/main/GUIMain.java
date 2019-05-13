package com.sevendeleven.gui.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class GUIMain extends Canvas {

	private static final long serialVersionUID = 1L;
	
	//Example 1: Buttons and Checkboxes
	
	/*
	public static int counter = 0;
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Counter");
		frame.setSize(400, 200);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridLayout layout = new GridLayout(3,2);
		JPanel panel = new JPanel(layout);
		frame.setContentPane(panel);
		
		
		JLabel counterLabel = new JLabel("0");
		panel.add(counterLabel);
		
		JCheckBox check = new JCheckBox("Should add two");
		panel.add(check);
		check.addActionListener((event) -> {
			System.out.println("Check box has been " + (check.isSelected() ? "checked" : "unchecked"));
		});
		
		
		JTextArea textArea = new JTextArea();
		textArea.setSize(300, 200);
		JScrollPane scrollPane = new JScrollPane(textArea);
		panel.add(scrollPane);
		
		JButton counterButton = new JButton("Add to counter");
		panel.add(counterButton);
		counterButton.addActionListener((event) -> {
			counterLabel.setText(Integer.toString((check.isSelected() ? counter+=2 : ++counter)));
			if (check.isSelected()) {
				textArea.setText(textArea.getText()+"you added 2!\n");
			}
		});
		
		
		
//		if (check.isSelected()) {
//			return "checked";
//		} else {
//			return "unchecked;
//		}
		
		frame.setVisible(true);
		
//		counterButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent event) {
//				
//			}
//		});
		
	}
	*/
	
	
	
	//EXAMPLE 2: Text fields and input
	
	
//	public static void main(String[] args) {
//		JFrame frame = new JFrame("login");
//		frame.setSize(200, 100);
//		frame.setResizable(false);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		GridLayout layout = new GridLayout(3,1);
//		JPanel panel = new JPanel(layout);
//		frame.setContentPane(panel);
//		
//		JTextField usernameField = new JTextField();
//		panel.add(usernameField);
//		
//		JPasswordField passwordField = new JPasswordField();
//		panel.add(passwordField);
//		
//		JButton loginButton = new JButton("Login");
//		loginButton.addActionListener((event) -> {
//			String username = usernameField.getText();
//			String password = new String(passwordField.getPassword());
////			String s = new String(new char[] {'c', 'h', 'a', 'r'});
////			s = "char";
//			if (username.equals("testusername") && password.equals("testpassword")) {
//				System.out.println("Login successful");
//			} else {
//				System.out.println("Login unsuccessful, wrong username/password");
//			}
//		});
//		panel.add(loginButton);
//		
//		frame.setVisible(true);
//		
//	}
	
	public static void main(String[] args) {
		(new GUIMain()).run();
	}
	
	
	
	//Variables
	private JFrame frame;
	private EventHandler eh;
	private BufferedImage sprite;
	private int px = 0, py = 0;
	
	
	
	
	
	
	
	//Constructor
	
	
	public GUIMain() {
		
		sprite = ImageLoader.getImage("/sprite.png");
		
		frame = new JFrame("Draw canvas");
		frame.setSize(500, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(this);
		eh = new EventHandler();
		this.addKeyListener(eh);
		this.addMouseListener(eh);
		
		frame.setVisible(true);
		this.requestFocus();
		
	}
	
	
	
	//Run function
	
	public void run() {
		
		long now = System.currentTimeMillis();
		long last = now;
		double delta = 0;
		double tps = 60.0;
		
		while (true) {
			
			now = System.currentTimeMillis();
			delta += (now-last)/1000.0*tps;
			
			if (delta >= 1.0) {
				delta = 0;
				update();
				draw();
			}
			
			last = now;
			
		}
		
	}
	
	
	
	
	//Update function
	
	public void update() {
		int dx = 0;
		int dy = 0;
		if (eh.keysPressed[KeyEvent.VK_D])
			dx+=1;
		if (eh.keysPressed[KeyEvent.VK_A])
			dx-=1;
		if (eh.keysPressed[KeyEvent.VK_W])
			dy-=1;
		if (eh.keysPressed[KeyEvent.VK_S])
			dy+=1;
		
		px += dx;
		py += dy;
		
	}
	
	
	
	
	//Render function
	
	public void draw() {
		
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		//Render START
		
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 500, 500);
		
		g.drawImage(sprite, px, py, null);
		
		
		//Render END
		g.dispose();
		bs.show();
		
	}
	
	
}
