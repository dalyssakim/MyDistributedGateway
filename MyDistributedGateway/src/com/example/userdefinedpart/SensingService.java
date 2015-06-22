package com.example.userdefinedpart;

import com.example.engine.myGatewayEngine;
import com.example.mydistributedgateway.MainActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class SensingService extends Service{

	private final IBinder mBinder = new MyBinder();
	private final String ip = "192.168.11.7";
	private final int port = 50505;
	public void startCollection(){
		IntegerSensorCollector collector = new IntegerSensorCollector();
		collector.runCollector();
		myGatewayEngine engine = new myGatewayEngine(MainActivity.ip, port, 2);
		engine.start();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		    Log.d("dms","onBind");
		    // Get messager from the Activity
		  
		    return mBinder;
	}

	 @Override
	    public void onCreate() {
		    Log.d("dms","onCreate");

	 }

public class MyBinder extends Binder {
     public SensingService getService() {
        return SensingService.this;
    }
}
}
