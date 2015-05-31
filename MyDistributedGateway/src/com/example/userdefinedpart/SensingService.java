package com.example.userdefinedpart;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class SensingService extends Service{

	private final IBinder mBinder = new MyBinder();
	
	public void startCollection(){
		IntegerSensorCollector collector = new IntegerSensorCollector();
		collector.startCollector();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		    Log.d("service","onBind");
		    // Get messager from the Activity
		  
		    return mBinder;
	}


public class MyBinder extends Binder {
     public SensingService getService() {
        return SensingService.this;
    }
}
}
