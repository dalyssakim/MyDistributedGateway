package com.example.userdefinedpart;

import java.net.Socket;

import com.example.datacollector.DataCollector;
import com.example.datacollector.Handler;

public class IntegerSensorCollector extends DataCollector{

	@Override
	public void refineData(Socket socket) {
		// TODO Auto-generated method stub
		Handler handle = new IntegerSensorHandler(socket);
		handle.startHandler();
	}

}
