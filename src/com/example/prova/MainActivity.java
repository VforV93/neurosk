 package com.example.prova;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prova.mathgenerator.MathQuestionsGenerator;
import com.neurosky.thinkgear.*;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

public class MainActivity extends ActionBarActivity{
	
	//public static TGDevice tgDevice; 
	public static TGDevice tgDevice; 
	BluetoothAdapter btAdapter;
	
	final boolean rawEnabled = false;
	
	MathQuestionsGenerator mqg;
	
	private final int time_connection = 5000;
	public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
	
	private TextView tv;
	private Button b;
	private Toast toast2;
	private MenuItem signal_tb;
	private Fragment1 f1;
	
	
	/**
	 * RECREATING AN ACTIVITY
	 */
	public final String GENERAL_STATE = "general_state";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("HelloEEG", "[MainActivity] <---------------");
		ActionBar actionBar = getSupportActionBar();
		FragmentTransaction ft =  getSupportFragmentManager().beginTransaction();
		f1 = new Fragment1();
		
		ft.add(R.id.container_fs, f1).commit();
		//f1 = (Fragment1)fm.findFragmentById(R.id.fragment1);
		
		//actionBar.hide();
		// tv = (TextView)findViewById(R.id.textView1);
		// tv.setText("asdasdasd");
	   //  tv.setTextSize(textview_textsize);
	     
		// b = (Button)findViewById(R.id.button1);
	    
		 if (savedInstanceState == null) {
			 
				btAdapter = BluetoothAdapter.getDefaultAdapter(); 
				if(btAdapter != null) 
				{ 
					tgDevice = new TGDevice(btAdapter, handler); 
				}
				else
				{
					Toast.makeText(this, "Bluetooth not available", Toast.LENGTH_LONG).show();
		        	finish();
		        	return;
				}
				toast2 = Toast.makeText(this, "Activate bluetooth or Turn-on the Mindwave Mobile", Toast.LENGTH_SHORT);
				
				if(tgDevice.getState() != TGDevice.STATE_CONNECTED)
					tgDevice.connect(rawEnabled);
		    }else{
		    	 if(tgDevice.getState() != TGDevice.STATE_CONNECTED)
						tgDevice.connect(rawEnabled);
		    }
	}
	
/*	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt(GENERAL_STATE, 1);
		
		super.onSaveInstanceState(savedInstanceState);
	}*/
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		signal_tb = (MenuItem)menu.findItem(R.id.signal);
		//this.bool=true;
		return true;
	}

	//@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
	//	int id = item.getItemId();
	//	if (id == R.id.action_settings) {
	//		return true;
	//	}
	//	return super.onOptionsItemSelected(item);
	//}
	
	private final Handler handler = new Handler() { 
		
		@Override 
		public void handleMessage(Message msg) 
		{ 
			switch (msg.what) 
			{ case TGDevice.MSG_STATE_CHANGE: 
				switch (msg.arg1) 
				{ case TGDevice.STATE_IDLE: 
					break;
				  case TGDevice.STATE_CONNECTING: 
					  f1.putMessage("Connecting...\n");
					  break;
				  case TGDevice.STATE_CONNECTED: 
					  f1.putMessage("Connected.\n");
					  /*Se connesso coloro il Button di verde ed imposto il nome del Button "Connected"*/
					  tgDevice.start();
					  f1.enableB(true);
					  break; 
				  case TGDevice.STATE_DISCONNECTED: 
					  	f1.putMessage("Disconnected\n");
						f1.enableB(false);
					  if(signal_tb != null)
						  signal_tb.setIcon(R.drawable.b_no_signal);
					  
					 new Handler().postDelayed(new Runnable() {
					        @Override
					        public void run() {
					        	tgDevice.connect(rawEnabled);
					        }
					    }, time_connection);
					  
					  break; 
				  case TGDevice.STATE_NOT_FOUND: 
					  	f1.putMessage("Can't find... Activate bluetooth or turn-on the Mindwave Mobile\n");
					  	toast2.show();
					 
					  new Handler().postDelayed(new Runnable() {
					        @Override
					        public void run() {
					        	tgDevice.connect(rawEnabled);
					        }
					    }, time_connection);
					  break;
				  case TGDevice.STATE_NOT_PAIRED: 
					  f1.putMessage("not paired\n");
					  break;
				  default: 
					  break;
				}
				break; 	
			case TGDevice.MSG_POOR_SIGNAL: 
	//			Log.v("HelloEEG", "PoorSignal: " + msg.arg1); 
				f1.putMessage("PoorSignal: " + msg.arg1 + "\n");
				int temp = 100 - msg.arg1;
	//			Log.d("HelloEEG", "PoorSignal temp: " + (temp/25));
				if(signal_tb != null)
				switch(temp / 25)
				{	case 4:
					 	signal_tb.setIcon(R.drawable.b_signal_4);
						break;
					case 3:
						signal_tb.setIcon(R.drawable.b_signal_3);
						break;
					case 2:
						signal_tb.setIcon(R.drawable.b_signal_2);
						break;
					case 1:
						signal_tb.setIcon(R.drawable.b_signal_1);
						break;
					default:
					signal_tb.setIcon(R.drawable.b_signal_0);	
				}				 
				break;
			case TGDevice.MSG_ATTENTION: 
				//if(msg.arg1 > 0 && msg.arg1 <= 100)
				f1.putMessage("Attention: " + msg.arg1 + "\n");
				Log.v("HelloEEG", "Attention: " + msg.arg1); 
				break; 
			case TGDevice.MSG_RAW_DATA: 
				//int rawValue = msg.arg1; 
				break; 
			case TGDevice.MSG_EEG_POWER: 
				TGEegPower ep = (TGEegPower)msg.obj; 
				//Log.v("HelloEEG", "Delta: " + ep.delta + "etcetc"); 
			case TGDevice.MSG_HEART_RATE:
				f1.putMessage("Heart rate: " + msg.arg1 + "\n");
                break;
			case TGDevice.MSG_MEDITATION:
				//if(msg.arg1 > 0 && msg.arg1 <= 100)
				f1.putMessage("Meditation: " + msg.arg1 + "\n");
            	break;
            case TGDevice.MSG_BLINK:
            	f1.putMessage("Blink: " + msg.arg1 + "\n");
            	break;
            case TGDevice.MSG_RAW_COUNT:
            	//f1.putMessage("Raw Count: " + msg.arg1 + "\n");
            	break;
            case TGDevice.MSG_LOW_BATTERY:
     //       	Toast.makeText(getApplicationContext(), "Low battery!", Toast.LENGTH_SHORT).show();
            	break;
            case TGDevice.MSG_RAW_MULTI:
            	TGRawMulti rawM = (TGRawMulti)msg.obj;
            	//tv.append("Raw1: " + rawM.ch1 + "\nRaw2: " + rawM.ch2);
			default: 
				break;
				}//chiusura primo switch
			}
	};//fine handler
	

	 @Override
	    public void onDestroy() {
		 	Log.v("HelloEEG", "DISTRUZIONEEEEEE");
		 	tgDevice.stop();
	    	tgDevice.close();
	        super.onDestroy();
	    }
	 
	public void replaceFragments(){
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		Log.d("HelloEEG", "[MainActivity] ReplaceFragments");
		Fragment sgtFragment = (Fragment)new StartGameTest();
		transaction.replace(R.id.container_fs, sgtFragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	
	public boolean isConnected(){
		if(tgDevice.getState() == TGDevice.STATE_CONNECTED)
			return true;
			
			return false;
	}
}
