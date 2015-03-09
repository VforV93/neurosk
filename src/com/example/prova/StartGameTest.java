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
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class StartGameTest  extends Fragment{
	
	private TextView tv;
	
	private final float textview_textsize = 200;
	ButtCountDownTimer countDownTimer;
	
	private final long timeXquests = 5000;
	private final long interval = 1000;
	
	MathQuestionsGenerator mqg;
	private List<Question> randomQuestions;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.activity_start_game, container, false);
		tv =  (TextView)view.findViewById(R.id.textView2);
		tv.setTextSize(textview_textsize);
		tv.setText("44+23");
		//tgDevice = MainActivity.tgDevice;

		mqg = new MathQuestionsGenerator();
	    this.randomQuestions = mqg.getAllQuestions();
	     
		countDownTimer = new ButtCountDownTimer(this.randomQuestions.size()*timeXquests,this.interval, this.randomQuestions, this.tv);
		countDownTimer.start();
		
		return view;
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
