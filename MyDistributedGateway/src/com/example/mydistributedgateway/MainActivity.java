package com.example.mydistributedgateway;

import com.example.userdefinedpart.SensingService;
import com.example.userdefinedpart.SensingService.MyBinder;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {


	private SensingService myService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public ServiceConnection myConnection = new ServiceConnection() {


		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
	        myService = ((SensingService.MyBinder) service).getService();
	        Log.d("ServiceConnection","connected");
	        myService.startCollection();

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
	        Log.d("ServiceConnection","disconnected");
	        myService = null;
		}
	};

	public Handler myHandler = new Handler() {
	    public void handleMessage(Message message) {
	        Bundle data = message.getData();
	    }
	};

	public void doBindService() {
	    Intent intent = null;
	    intent = new Intent(this, SensingService.class);
	    // Create a new Messenger for the communication back
	    // From the Service to the Activity

	    bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
	}
}
