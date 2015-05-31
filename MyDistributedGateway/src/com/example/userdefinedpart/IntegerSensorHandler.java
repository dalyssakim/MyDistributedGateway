package com.example.userdefinedpart;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.example.data.Data;
import com.example.datacollector.Handler;
import com.mydistributedsystem.message.JDMessage;

public class IntegerSensorHandler extends Handler{

		public IntegerSensorHandler(Socket socket) {
		super(socket);
		// TODO Auto-generated constructor stub
	}


	

	@Override
	public void handle(JDMessage msg) {
		// TODO Auto-generated method stub

					Data is = new IntegerSensor(msg.id,"noname", msg.data);
					IntegerSensorList.getIntegerSensorList().putData(is);		
	}
}
