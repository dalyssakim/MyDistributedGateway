package com.example.userdefinedpart;

import java.net.Socket;

import com.example.datacollector.Handler;
import com.example.datacollector.ServerRunner;

public class IntegerSensorCollector extends ServerRunner{

	@Override
	public void refineData(Socket socket) {
		// TODO Auto-generated method stub
		Handler handle = new IntegerSensorHandler(socket);
		handle.startHandler();
	}

}
