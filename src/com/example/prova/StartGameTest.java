package com.example.prova;

import java.util.List;

import com.example.prova.mathgenerator.MathQuestionsGenerator;
import com.example.prova.mathgenerator.Question;
import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGEegPower;
import com.neurosky.thinkgear.TGRawMulti;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class StartGameTest extends ActionBarActivity {
	
	private TGDevice tgDevice;
	private TextView tv;
	
	private final float textview_textsize = 200;
	ButtCountDownTimer countDownTimer;
	
	private final long timeXquests = 5000;
	private final long interval = 1000;
	
	MathQuestionsGenerator mqg;
	private List<Question> randomQuestions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_game);
		
		Intent intent = getIntent();
		//String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		
		tv = (TextView)findViewById(R.id.textView2);
		tv.setTextSize(textview_textsize);
		tv.setText("44+23");
		tgDevice = MainActivity.tgDevice;

		mqg = new MathQuestionsGenerator();
	    this.randomQuestions = mqg.getAllQuestions();
	     
		countDownTimer = new ButtCountDownTimer(this.randomQuestions.size()*timeXquests,this.interval, this.randomQuestions, this.tv);
		countDownTimer.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
					 // tv.append("Connecting...\n");
					  break;
				  case TGDevice.STATE_CONNECTED: 
					 // tv.append("Connected.\n");
					  tgDevice.start();
					  break; 
				  case TGDevice.STATE_DISCONNECTED: 
					  //tv.append("Disconnected mang\n");
					  break; 
				  case TGDevice.STATE_NOT_FOUND: 
					  //tv.setTextColor(Color.RED);
					  break;
				  case TGDevice.STATE_NOT_PAIRED: 
					 // tv.append("not paired\n");
					  break;
				  default: 
					  break;
				}
				break; 	
			case TGDevice.MSG_POOR_SIGNAL: 
				Log.v("HelloEEG", "PoorSignal: +++" + msg.arg1); 
				//tv.append("PoorSignal: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_ATTENTION: 
				//if(msg.arg1 > 0 && msg.arg1 <= 100)
				//tv.append("Attention: " + msg.arg1 + "\n");
				Log.v("HelloEEG", "Attention: +++" + msg.arg1); 
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
				//tv.append("Meditation: " + msg.arg1 + "\n");
				Log.v("HelloEEG", "Meditation: +++" + msg.arg1); 
            	break;
            case TGDevice.MSG_BLINK:
            	//tv.append("Blink: " + msg.arg1 + "\n");
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
		 Log.v("HelloEEG", "DESTROYYYYYYYYYYYYYY: +++");
		 	tgDevice.stop();
		 	tgDevice.close();
	        super.onDestroy();
	    }
	 
	 
	 public class ButtCountDownTimer extends CountDownTimer
		{
		 	private TextView tv;
		 	private List<Question> randomQuestions;
		 	private int cont = 0;
			public ButtCountDownTimer(long startTime, long interval, List<Question> randomQuestions, TextView tv)
				{
					super(startTime, interval);
					this.randomQuestions = randomQuestions;
					this.tv=tv;
				}
			
			@Override
			public void onFinish()
				{
					//text.setText("Time's up!");
					//timeElapsedView.setText("Time Elapsed: " + String.valueOf(startTime));
				tv.setText("FINISH");
				//b.getBackground().setColorFilter(null);
				//b.setText("CONNECT");
				}

			@Override
			public void onTick(long millisUntilFinished) {
				if((millisUntilFinished/1000 + 1)%5 == 0)
				{tv.setText(""+this.randomQuestions.get(cont).toString());
				cont++;
		
				}
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
