 package com.example.prova;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.neurosky.thinkgear.*;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

public class MainActivity extends Activity {
	
	TGDevice tgDevice; 
	BluetoothAdapter btAdapter;
	final boolean rawEnabled = false;
	
	TextView tv;
	Button b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 tv = (TextView)findViewById(R.id.textView1);
	     tv.setText("");
	     tv.append("Android version: " + Integer.valueOf(android.os.Build.VERSION.SDK) + "\n" );
	     
	     
		btAdapter = BluetoothAdapter.getDefaultAdapter(); 
		if(btAdapter != null) 
		{ tgDevice = new TGDevice(btAdapter, handler); }
		else
		{
			Toast.makeText(this, "Bluetooth not available", Toast.LENGTH_LONG).show();
        	finish();
        	return;
		}
	}
	
	
	private final Handler handler = new Handler() { 
		
		@Override 
		public void handleMessage(Message msg) 
		{ switch (msg.what) 
			{ case TGDevice.MSG_STATE_CHANGE: 
				switch (msg.arg1) 
				{ case TGDevice.STATE_IDLE: 
					break;
				  case TGDevice.STATE_CONNECTING: 
					  tv.append("Connecting...\n");
					  break;
				  case TGDevice.STATE_CONNECTED: 
					  tv.append("Connected.\n");
					  tgDevice.start();
					  break; 
				  case TGDevice.STATE_DISCONNECTED: 
					  tv.append("Disconnected mang\n");
					  break; 
				  case TGDevice.STATE_NOT_FOUND: 
					  tv.append("Can't find\n");
					  break;
				  case TGDevice.STATE_NOT_PAIRED: 
					  tv.append("not paired\n");
					  break;
					  
				  default: 
					  break;
				}
				break; 
			
			case TGDevice.MSG_POOR_SIGNAL: 
				Log.v("HelloEEG", "PoorSignal: " + msg.arg1); 
			case TGDevice.MSG_ATTENTION: 
				tv.append("Attention: " + msg.arg1 + "\n");
				Log.v("HelloEEG", "Attention: " + msg.arg1); 
				break; 
			case TGDevice.MSG_RAW_DATA: 
				int rawValue = msg.arg1; 
				break; 
			case TGDevice.MSG_EEG_POWER: 
				//TGEegPower ep = ( com.neurosky.thinkgear.TGEegPower)msg.arg1; 
				//Log.v("HelloEEG", "Delta: " + ep.delta); 
				Log.v("HelloEEG", "Delta: xxxcorreggere"); 
			case TGDevice.MSG_HEART_RATE:
        		tv.append("Heart rate: " + msg.arg1 + "\n");
                break;
                
			case TGDevice.MSG_MEDITATION:

            	break;
            case TGDevice.MSG_BLINK:
            		tv.append("Blink: " + msg.arg1 + "\n");
            	break;
            case TGDevice.MSG_RAW_COUNT:
            		//tv.append("Raw Count: " + msg.arg1 + "\n");
            	break;
            case TGDevice.MSG_LOW_BATTERY:
            	Toast.makeText(getApplicationContext(), "Low battery!", Toast.LENGTH_SHORT).show();
            	break;
            case TGDevice.MSG_RAW_MULTI:
            	//TGRawMulti rawM = (TGRawMulti)msg.obj;
            	//tv.append("Raw1: " + rawM.ch1 + "\nRaw2: " + rawM.ch2);
			default: 
				break;
				
				}//chiusura primo switch
			}
	};//fine handler
	

	 @Override
	    public void onDestroy() {
	    	tgDevice.close();
	        super.onDestroy();
	    }
	 
	 public void doStuff(View view) {
	    	if(tgDevice.getState() != TGDevice.STATE_CONNECTING && tgDevice.getState() != TGDevice.STATE_CONNECTED)
	    		tgDevice.connect(rawEnabled);   
	    	//tgDevice.ena
	    }
	
}
