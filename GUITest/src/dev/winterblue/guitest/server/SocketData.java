package dev.winterblue.guitest.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketData {
	public PrintStream ps;
	public Scanner sc;
	public Socket s;
	public String username;
	
	public SocketData(Socket s) {
		this.s = s;
		try {
			sc = new Scanner(s.getInputStream());
			ps = new PrintStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
