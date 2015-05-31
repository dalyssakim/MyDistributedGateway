package com.example.datacollector;

import java.net.ServerSocket;
import java.net.Socket;


public abstract class DataCollector extends Thread{

	public final int PORT = 8888;
	private boolean stopThread = false;
	public abstract void refineData(Socket socket);
	
	public void startCollector(){
	ServerSocket server = null;
		
		try{
			
			 server = new ServerSocket(PORT);
			while(stopThread == false){

				Socket socket = server.accept();
			
				refineData(socket);
				
			}
			} catch(Exception e){
				e.printStackTrace();
			}
	}
	
	public void run(){
		startCollector();
	}
	
	public void runCollector(){
		this.start();
	}

}
