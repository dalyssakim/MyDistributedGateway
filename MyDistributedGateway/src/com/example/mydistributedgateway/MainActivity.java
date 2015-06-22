package com.example.mydistributedgateway;

import com.example.userdefinedpart.IntegerSensorList;
import com.example.userdefinedpart.SensingService;
import com.example.userdefinedpart.SensingService.MyBinder;








import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button conBtn;
	private EditText txt;
	private TextView incomingData;
	private TextView commitingData;
	private TextView statusText;
	public static String ip = "";
	private SensingService myService;
	public static Activity main ;
	private int numCommittedData = 0;
	private String status = "Not Connected";
	public ServiceConnection myConnection = new ServiceConnection() {


		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
	        myService = ((SensingService.MyBinder) service).getService();
	        Log.d("ServiceConnection","connected");
	        
	        Thread thread = new Thread(new Runnable(){
	            @Override
	            public void run() {
	                try {

	        	        myService.startCollection();
	                    //Your code goes here
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });

	        thread.start(); 

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
	        Log.d("ServiceConnection","disconnected");
	        myService = null;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		main = this;

	    Log.d("dms","START!");
	

		conBtn = (Button) this.findViewById(R.id.btn_connect);
		txt = (EditText) this.findViewById(R.id.editIp);
		incomingData = (TextView) this.findViewById(R.id.v_incomingData);
		commitingData = (TextView) this.findViewById(R.id.v_committedData);
		statusText = (TextView) this.findViewById(R.id.v_state);
		conBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	ip = txt.getText().toString();

               	doBindService();
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



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
	    startService(intent);
	    Log.d("dms","doBindService");
	    bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
	}
	
	public void setIncomingData() {

	    new Thread() {
	        public void run() {
	           
	                runOnUiThread(new Runnable() {

					    @Override
					    public void run() {
					        incomingData.setText(IntegerSensorList.getIntegerSensorList().length()+"");
					    }
					});
	            }
	        
	    }.start();
	}
	
	public void setCommitingData() {

	    new Thread() {
	        public void run() {
	           
	                runOnUiThread(new Runnable() {

					    @Override
					    public void run() {
					    	numCommittedData++;
					        commitingData.setText(numCommittedData+"");
					    }
					});
	            }
	        
	    }.start();
	}
	

	public void setStatus(String val) {
		status = val;
	    new Thread() {
	        public void run() {
	           
	                runOnUiThread(new Runnable() {

					    @Override
					    public void run() {
					        statusText.setText(status+"");
					    }
					});
	            }
	        
	    }.start();
	}
}
