package com.example.prova;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment1 extends Fragment implements OnClickListener{
	
	private TextView tv;
	private final float textview_textsize = 20;
	private Button b;
	
	@Override 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment1, container, false);
		tv =  (TextView)view.findViewById(R.id.textView1);
		tv.append("Android version: " + Integer.valueOf(android.os.Build.VERSION.SDK) + "\n" );
		b = (Button)view.findViewById(R.id.button1);
		b.setOnClickListener(this);
		
		if(((MainActivity)getActivity()).isConnected())
			this.enableB(true);
		else this.enableB(false);
		
		//toast2 = Toast.makeText(this, "Activate bluetooth or Turn-on the Mindwave Mobile", Toast.LENGTH_SHORT);
		Log.d("HelloEEG", "[Fragment1] onCreateView<---------------  ");
		return view;
	}
	
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tv.setText("");
		tv.setTextSize(textview_textsize);
	}
	
	
	public void putMessage(String msg){
		tv.append(msg);
	}
	
	
	 public void doStuff(View view) {
	    	//if(tgDevice.getState() != TGDevice.STATE_CONNECTING && tgDevice.getState() != TGDevice.STATE_CONNECTED)
	    	//	tgDevice.connect(rawEnabled);  
	    	//else{
	    		Log.d("HelloEEG", "[Fragment1] LANCIO L-ALTRA ACTIVITY");
	    		//Intent intent = new Intent(this, StartGameTest.class);
	    		//String message = "messaggio quasi massaggio";
	    		//intent.putExtra(EXTRA_MESSAGE, message);
	    		
	    		//startActivity(intent);
	    	//}
	    }


	public void enableB(boolean flag) {
		if(flag){
			b.setClickable(true);
			b.setAlpha(1f);
		}else{
				b.setClickable(false);
				b.setAlpha(0.2f);
			}			
	}


	@Override
	public void onClick(View arg0) {
		Log.d("HelloEEG", "[Fragment1] LANCIO L-ALTRA ACTIVITY");
		((MainActivity)getActivity()).replaceFragments();
	}
}
