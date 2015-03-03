 package com.example.prova;

import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import android.app.ActionBar;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.util.Log;
import android.content.Intent;

public class MainActivity extends ActionBarActivity {
	
	TGDevice tgDevice; 
	BluetoothAdapter btAdapter;
	ButtCountDownTimer countDownTimer;
	final boolean rawEnabled = false;
	
	MathQuestionsGenerator mqg;
	private List<Question> randomQuestions;
	
	private final long startTime = 2000;
	private final long interval = 1000;
	public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
	
	TextView tv;
	Button b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 tv = (TextView)findViewById(R.id.textView1);
		 b = (Button)findViewById(R.id.button1);
		 countDownTimer = new ButtCountDownTimer(this.startTime,this.interval);
	     tv.setText("");
	     mqg = new MathQuestionsGenerator();
	     this.randomQuestions = mqg.getAllQuestions();
	  /*   for (Question question : randomQuestions) {
	    	 tv.append(question.toString()+"\n");
	        }*/
	     
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
					  b.getBackground().setColorFilter(null);
					  b.getBackground().setColorFilter(new LightingColorFilter(0xFF76FF03, 0x000000));
					  b.setText("Connected");
					  tgDevice.start();
					  countDownTimer.start();
					  b.setText("Connected");
					  break; 
				  case TGDevice.STATE_DISCONNECTED: 
					  tv.append("Disconnected mang\n");
					  b.getBackground().setColorFilter(null);
					  b.setText("CONNECT");
					  countDownTimer.start();
					  break; 
				  case TGDevice.STATE_NOT_FOUND: 
					  tv.append("Can't find\n");
					  b.getBackground().setColorFilter(null);
					  b.getBackground().setColorFilter(new LightingColorFilter(0xFFFFEB3B, 0x000000));
					  b.setText("Can't find");
					  countDownTimer.start();
					  break;
				  case TGDevice.STATE_NOT_PAIRED: 
					  tv.append("not paired\n");
					  b.getBackground().setColorFilter(null);
					  b.getBackground().setColorFilter(new LightingColorFilter(0xFFFFEB3B, 0x000000));
					  b.setText("Not paired");
					  countDownTimer.start();
					  break;
				  default: 
					  break;
				}
				break; 	
			case TGDevice.MSG_POOR_SIGNAL: 
				Log.v("HelloEEG", "PoorSignal: " + msg.arg1); 
			case TGDevice.MSG_ATTENTION: 
				if(msg.arg1 > 0 && msg.arg1 <= 100)
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
				if(msg.arg1 > 0 && msg.arg1 <= 100)
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
	    	tgDevice.close();
	        super.onDestroy();
	    }
	 
	 public void doStuff(View view) {
	    	if(tgDevice.getState() != TGDevice.STATE_CONNECTING && tgDevice.getState() != TGDevice.STATE_CONNECTED)
	    		tgDevice.connect(rawEnabled);  
	    	else{
	    		Log.v("HelloEEG", "LANCIO L-ALTRA ACTIVITY");
	    		Intent intent = new Intent(this, StartGameTest.class);
	    		String message = "messaggio quasi massaggio";
	    		intent.putExtra(EXTRA_MESSAGE, message);
	    		startActivity(intent);
	    	}
	    	//tgDevice.ena
	    }
	 
	// CountDownTimer class
			public class ButtCountDownTimer extends CountDownTimer
				{

					public ButtCountDownTimer(long startTime, long interval)
						{
							super(startTime, interval);
						}

					@Override
					public void onFinish()
						{
							//text.setText("Time's up!");
							//timeElapsedView.setText("Time Elapsed: " + String.valueOf(startTime));
						b.getBackground().setColorFilter(null);
						b.setText("CONNECT");
						}

					@Override
					public void onTick(long arg0) {
						//NULL
					}

				/*	@Override
					public void onTick(long millisUntilFinished)
						{
							text.setText("Time remain:" + millisUntilFinished);
							timeElapsed = startTime - millisUntilFinished;
							timeElapsedView.setText("Time Elapsed: " + String.valueOf(timeElapsed));
						}*/
				}
	
}
