package com.sevendeleven.threadding.main;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainClientt implements Runnable {
	
	private static Thread thread;
	private static Socket socket;
	
	
	
	public static void main(String[] args) {
		try {
			socket = new Socket("localhost", 2023);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		thread = new Thread(new MainClientt(), "Connection");
		thread.start();
		
		Scanner sc = new Scanner(System.in);
		PrintStream stream = null;
		try {
			stream = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		while (true) {
			stream.println(sc.nextLine());
			System.out.println("test");
		}
		
	}
	
	public void stop() {
		try {
			socket.close();
			thread.join();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		Scanner sc = null;
		try {
			sc = new Scanner(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		while (true) {
			String in = sc.nextLine();
			System.out.println(in);
		}
	}
	
}
