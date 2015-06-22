package com.example.datacollector;

import java.net.ServerSocket;
import java.net.Socket;


public abstract class ServerRunner extends Thread{

	public int PORT = 8888;
	private boolean stopThread = false;
	public abstract void refineData(Socket socket);
	
	public void setPort(int port){
		this.PORT = port;
	}
	
	public void startServer(){
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
		startServer();
	}
	
	public void runCollector(){
		this.start();
	}

}
