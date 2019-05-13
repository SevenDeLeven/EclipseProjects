package dev.winterblue.guitest.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;

public class ClientMain extends JFrame {

	private static final long serialVersionUID = -6683768957200523729L;

	static private Socket s;
	static private PrintStream ps;
	
	static String ID;
	static JFrame frame;
	static JPanel panel;
	static JTextArea messageArea;
	static JScrollPane scrollPane;
	static JTextField inputArea;
	static String username;
	
	public static void receive(String msg) {
		messageArea.setText(messageArea.getText()+"\n"+msg);
	}
	
	public static void main(String[] args) {
		init();
		username = JOptionPane.showInputDialog("Enter your username: ");
		frame.setTitle("BluText Demo | " + username);
		
		try {
			s = new Socket("localhost", 777);
			ps = new PrintStream(s.getOutputStream());
			JSONObject conpacket = new JSONObject();
			conpacket.put("ptype", new JSONString("con"));
			conpacket.put("uname", new JSONString(username));
			ps.println(conpacket.toJSON());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		new ClientReceiver(s);
	}
	
	public static void init() {
		frame = new JFrame("BluText Demo");
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setVgap(2);
		
		panel = new JPanel(borderLayout);
		panel.setBackground(Color.gray);
		
		frame.setSize(500, 400);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setFocusable(false);
		
		messageArea = new JTextArea();
		messageArea.setEditable(false);
		messageArea.setBackground(Color.white);
		scrollPane = new JScrollPane(messageArea);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		inputArea = new JTextField();
		inputArea.setBackground(new Color(143, 186, 219));
		panel.add(inputArea, BorderLayout.SOUTH);

		inputArea.addActionListener((event) -> {
			JSONObject packet = new JSONObject();
			packet.put("ptype", new JSONString("msg"));
			packet.put("msg", new JSONString(inputArea.getText()));
			packet.put("uname", new JSONString(username));
			ps.println(packet.toJSON());
			inputArea.setText("");
		});
		
//		frame.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				JSONObject packet = new JSONObject();
//				packet.put("ptype", new JSONString("discon"));
//				packet.put("uname", new JSONString(username));
//				ps.println(packet.toJSON());
//				
//				try {
//					ps.close();
//					s.close();
//					frame.dispose();
//				} catch (IOException exc) {
//					exc.printStackTrace();
//				}
//			}
//		});
		
		frame.setContentPane(panel);
		frame.setVisible(true);
	}
}
