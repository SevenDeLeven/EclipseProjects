package com.sevendeleven.threadding.main;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainClient implements Runnable {
	static Thread t;
	static Socket socket;
	public static void main(String[] args) {
		try {
			socket = new Socket("localhost", 2024);
			Scanner sc = new Scanner(socket.getInputStream());
			t = new Thread(new MainClient(), "Connection");
			t.start();
			while (true) {
				System.out.println(sc.nextLine());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		try {
			PrintStream stream = new PrintStream(socket.getOutputStream());
			while (true) {
				Scanner sc = new Scanner(System.in);
				String nl;
				stream.println(nl = sc.nextLine());
				if (nl.equals("/exit")) {
					sc.close();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
