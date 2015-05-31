package com.example.datacollector;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.mydistributedsystem.message.JDMessage;

public abstract class Handler extends Thread{

	private String serverName = "AndroidGatewayServer";
	private byte[] buf = new byte[1024];
	private JDMessage msg; // whatever datatype they choose...
	protected Socket socket;
	
	public Handler(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
	}

	public void run(){
		connectionHandler();
	}
	
	public abstract void handle(JDMessage msg);

	private void connectionHandler(){
		InputStream in = null;
		OutputStream out = null;
			try{
			 in = socket.getInputStream();
			 out = socket.getOutputStream();
				
				in.read(buf);
				if(buf != null){
				Object obj = deserialize(buf);
				msg = (JDMessage)obj;
					handle(msg);
				}
				else {
					System.out.println("DATA NULL...");
				}
			} catch(Exception e){
				e.printStackTrace();
		
				System.out.println("Connection Out");

			} finally{
				try {
					in.close();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
	}
	
	public static byte[] serialize(Object obj) throws IOException {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(obj);
	    return out.toByteArray();
	}
	
	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
	    ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return is.readObject();
	}

	public void startHandler(){
		this.start();
	}
}
