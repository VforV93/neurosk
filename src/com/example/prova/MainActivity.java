 package com.example.prova;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prova.mathgenerator.MathQuestionsGenerator;
import com.example.prova.mathgenerator.Question;
import com.neurosky.thinkgear.*;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;
import android.content.Intent;

public class MainActivity extends ActionBarActivity{
	
	//public static TGDevice tgDevice; 
	public static TGDevice tgDevice; 
	BluetoothAdapter btAdapter;
	
	final boolean rawEnabled = false;
	
	MathQuestionsGenerator mqg;
	private List<Question> randomQuestions;
	

	private final float textview_textsize = 20;
	private final int time_connection = 5000;
	public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
	
	private TextView tv;
	private Button b;
	private Toast toast2;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 tv = (TextView)findViewById(R.id.textView1);
		 tv.setText("");
	    // tv.setLineSpacing(700f, 700f);
	     tv.setTextSize(textview_textsize);
	     
		 b = (Button)findViewById(R.id.button1);
		
	     
	     mqg = new MathQuestionsGenerator();
	     this.randomQuestions = mqg.getAllQuestions();
	  /*   for (Question question : randomQuestions) {
	    	 tv.append(question.toString()+"\n");
	        }*/
	     
	    tv.append("Android version: " + Integer.valueOf(android.os.Build.VERSION.SDK) + "\n" );
	   
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
		
		Log.v("HelloEEG", "IL MIO STATO: " + tgDevice.getState()); 
		
		if(tgDevice.getState() != TGDevice.STATE_CONNECTED)
			tgDevice.connect(rawEnabled);
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
					  /*Se connesso coloro il Button di verde ed imposto il nome del Button "Connected"*/
					  tgDevice.start();
					  b.setClickable(true);
					  b.setAlpha(1f);
					  break; 
				  case TGDevice.STATE_DISCONNECTED: 
					  tv.append("Disconnected mang\n");
					  b.setClickable(false);
					  b.setAlpha(0.2f);
					  
					  new Handler().postDelayed(new Runnable() {
					        @Override
					        public void run() {
					        	tgDevice.connect(rawEnabled);
					        }
					    }, time_connection);
					  
					  break; 
				  case TGDevice.STATE_NOT_FOUND: 
					  //tv.setTextColor(Color.RED);
					  tv.append("Can't find... Activate bluetooth or turn-on the Mindwave Mobile\n");
					  toast2.show();
					  //tv.setTextColor(Color.BLACK);
					  new Handler().postDelayed(new Runnable() {
					        @Override
					        public void run() {
					        	tgDevice.connect(rawEnabled);
					        }
					    }, time_connection);
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
				tv.append("PoorSignal: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_ATTENTION: 
				//if(msg.arg1 > 0 && msg.arg1 <= 100)
				tv.append("Attention: " + msg.arg1 + "\n");
				//Log.v("HelloEEG", "Attention: " + msg.arg1); 
				break; 
			case TGDevice.MSG_RAW_DATA: 
				//int rawValue = msg.arg1; 
				break; 
			case TGDevice.MSG_EEG_POWER: 
				TGEegPower ep = (TGEegPower)msg.obj; 
				//Log.v("HelloEEG", "Delta: " + ep.delta + "etcetc"); 
			case TGDevice.MSG_HEART_RATE:
        		//tv.append("Heart rate: " + msg.arg1 + "\n");
                break;
			case TGDevice.MSG_MEDITATION:
				//if(msg.arg1 > 0 && msg.arg1 <= 100)
				tv.append("Meditation: " + msg.arg1 + "\n");
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
	    	tgDevice.close();
	        super.onDestroy();
	    }
	 
	 public void doStuff(View view) {
	    	//if(tgDevice.getState() != TGDevice.STATE_CONNECTING && tgDevice.getState() != TGDevice.STATE_CONNECTED)
	    	//	tgDevice.connect(rawEnabled);  
	    	//else{
	    		Log.v("HelloEEG", "LANCIO L-ALTRA ACTIVITY");
	    		Intent intent = new Intent(this, StartGameTest.class);
	    		String message = "messaggio quasi massaggio";
	    		intent.putExtra(EXTRA_MESSAGE, message);
	    		
	    		
	    		startActivity(intent);
	    	//}
	    }
}
