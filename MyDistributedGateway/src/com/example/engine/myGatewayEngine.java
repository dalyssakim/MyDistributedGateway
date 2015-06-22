package com.example.engine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.mydistributedgateway.MainActivity;
import com.example.userdefinedpart.IntegerSensorList;
import com.mydistributedsystem.interfaces.Job;
import com.mydistributedsystem.message.JDMessage;
import com.mydistributedsystem.message.JDMessageType;

public class myGatewayEngine extends Thread {

	private String INET_ADDR = "127.0.0.1";
	private int PORT = 50505;
	private int id = 2;
	private InetAddress address;
	private boolean stopEngine = false;
	private Job job = null;
	private boolean run = false;
	public myGatewayEngine(String ip, int port, int id){
		this.INET_ADDR = ip;
		this.PORT = port;
		this.id = id;
		
		
	}
	
	public void initialize(){
		try {
			

			byte[] buf = new byte[1024];
			address = InetAddress.getByName(INET_ADDR);
			
			Socket clientSocket = new Socket(address, PORT);
			OutputStream outputStream = clientSocket.getOutputStream();
			InputStream inputStream = clientSocket.getInputStream();
			JDMessage dObject = new JDMessage();
			dObject.id=id;
			dObject.type = JDMessageType.pullInterface;
			dObject.data = null;
			outputStream.write(serialize(dObject));
		
			int charsRead = 0;

				System.out.println("Client Sent Message");
				inputStream.read(buf);
				while(buf.length <= 0){
					inputStream.read(buf);
				}

				Object obj = deserialize(buf);
				dObject = (JDMessage)obj;
				job = (Job) dObject.data;
				System.out.println("Interface Received");
				clientSocket.close();
				
				if(dObject.type == JDMessageType.halt){
					run = false;
					((MainActivity) MainActivity.main).setStatus("Idle");
					
				}else{
					run = true;
				((MainActivity) MainActivity.main).setStatus("Run");
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				this.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}			
	}
		
	public void run(){
		
		while(!stopEngine){
		byte[] buf = new byte[1024];
		
		try{
			JDMessage dObject = new JDMessage();
			
			if(job == null){
				initialize();
			}
				if(dObject.id == 0){
					
					Socket clientSocket = new Socket(address, PORT);
					OutputStream outputStream = clientSocket.getOutputStream();
					dObject.id = id;
					dObject.type = JDMessageType.pushResult;
					if(IntegerSensorList.getIntegerSensorList().getSize() == IntegerSensorList.sensors.getMaxSize()){
					dObject.data = job.doJob(IntegerSensorList.getIntegerSensorList().getAllData());
					}else{
					dObject.data = null;
					}
					System.out.println("Sending Result : " + dObject.data);
					((MainActivity) MainActivity.main).setCommitingData();
					outputStream = clientSocket.getOutputStream();
					outputStream.write(serialize(dObject));
					
					//
					InputStream inputStream = clientSocket.getInputStream();
					inputStream.read(buf);
					while(buf.length <= 0){
						inputStream.read(buf);
					}

					Object obj = deserialize(buf);
					dObject = (JDMessage)obj;
					job = (Job) dObject.data;
					System.out.println("ACK");

					if(dObject.type == JDMessageType.halt){
						run = false;
						job = null; 
						((MainActivity) MainActivity.main).setStatus("Ready");
						
					}else{
					
						run = true;
					((MainActivity) MainActivity.main).setStatus("Run");
					}
					
					clientSocket.close();
				}
			
		} catch(Exception e){
			e.printStackTrace();
			((MainActivity) MainActivity.main).setStatus("Exception");
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

}
