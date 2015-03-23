package com.example.prova;


import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGEegPower;
import com.neurosky.thinkgear.TGRawMulti;



public class DialogFragBChart extends DialogFragment{
	int mNum;
	private int[] delimitersColor = {0,10,20,30,40,50,60,70,80,90};
	private GraphicalView mChart, mChart2;
	private String[] mMonth = new String[] {
			 "Attention", "Meditation"
			 };
	
	private final int time_connection = 500;
	public Handler mHandler;
	public XYMultipleSeriesDataset dataset;
	public XYSeries incomeSeriesAtt;
	public XYSeries incomeSeriesMed;
	public LinearLayout chartContainer;
	public XYMultipleSeriesRenderer multiRenderer;
	public XYSeriesRenderer incomeRendererAtt;
	public XYSeriesRenderer incomeRendererMed;
	
	public static DialogFragBChart newInstance(int num) {
		DialogFragBChart f = new DialogFragBChart();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        mNum = getArguments().getInt("num");

	        // Pick a style based on the num.
	        int style = DialogFragment.STYLE_NORMAL, theme = 0;
	        switch ((mNum-1)%6) {
	            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
	            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
	            case 3: style = DialogFragment.STYLE_NO_INPUT; break;
	            case 4: style = DialogFragment.STYLE_NORMAL; break;
	            case 5: style = DialogFragment.STYLE_NORMAL; break;
	            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
	            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
	            case 8: style = DialogFragment.STYLE_NORMAL; break;
	        }
	        switch ((mNum-1)%6) {
	            case 4: theme = android.R.style.Theme_Holo; break;
	            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
	            case 6: theme = android.R.style.Theme_Holo_Light; break;
	            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
	            case 8: theme = android.R.style.Theme_Holo_Light; break;
	        }
	        
	        style = DialogFragment.STYLE_NORMAL;
	        theme = R.style.CustomDialog;
	        setStyle(style, theme);
	    }
	 
	 
	 private void openChart(View v){
		 int[] x = { 0,1 };
		 int incomeAtt = 0;
		 int incomeMed = 0;
		// Creating an XYSeries for Income
		 incomeSeriesAtt = new XYSeries("Val1");
		 incomeSeriesMed = new XYSeries("Val2");
		 // Adding data to Income and Expense Series
		 incomeSeriesAtt.add(x[0], incomeAtt);
		 incomeSeriesMed.add(x[1], incomeMed);
		 
		// Creating a dataset to hold each series
		 dataset = new XYMultipleSeriesDataset();
		 // Adding Income Series to the dataset
		 dataset.addSeries(incomeSeriesAtt);
		 dataset.addSeries(incomeSeriesMed);
		 
		// Creating XYSeriesRenderer to customize incomeSeries
		 incomeRendererAtt = new XYSeriesRenderer();
		 incomeRendererMed = new XYSeriesRenderer();
		 
		 incomeRendererAtt.setColor(Color.rgb(232, 245, 233));//color of the graph set to light green
		 incomeRendererAtt.setFillPoints(true);
		 incomeRendererAtt.setLineWidth(2);
		 incomeRendererAtt.setDisplayChartValues(true);
		 incomeRendererAtt.setDisplayChartValuesDistance(30); //setting chart value distance 
		 
		 
		 incomeRendererMed.setColor(Color.rgb(232, 245, 233));//color of the graph set to light green
		 incomeRendererMed.setFillPoints(true);
		 incomeRendererMed.setLineWidth(2);
		 incomeRendererMed.setDisplayChartValues(true);
		 incomeRendererMed.setDisplayChartValuesDistance(30); //setting chart value distance 
		 
		 
		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		 multiRenderer = new XYMultipleSeriesRenderer();
		 multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
		 multiRenderer.setXLabels(0);
		 multiRenderer.setChartTitle("");
		 //multiRenderer.setXTitle("Year 2014");
		 multiRenderer.setYTitle("Intensity");
		 
		 /***
		  * Customizing graphs
		  */
		  multiRenderer.setShowLegend(false);
		 //setting text size of the title
		  multiRenderer.setChartTitleTextSize(28);
		  //setting text size of the axis title
		  multiRenderer.setAxisTitleTextSize(24);
		  //setting text size of the graph lable
		  multiRenderer.setLabelsTextSize(24);
		  //setting zoom buttons visiblity
		  multiRenderer.setZoomButtonsVisible(false);
		  //setting pan enablity which uses graph to move on both axis
		  multiRenderer.setPanEnabled(false, false);
		  //setting click false on graph
		  multiRenderer.setClickEnabled(false);
		  //setting zoom to false on both axis
		  multiRenderer.setZoomEnabled(false, false);
		  //setting lines to display on y axis
		  multiRenderer.setShowGridY(false);
		  //setting lines to display on x axis
		  multiRenderer.setShowGridX(false);
		  //setting legend to fit the screen size
		  multiRenderer.setFitLegend(false);
		  //setting displaying line on grid
		  multiRenderer.setShowGrid(false);
		  //setting zoom to false
		  multiRenderer.setZoomEnabled(false);
		  //setting external zoom functions to false
		  multiRenderer.setExternalZoomEnabled(false);
		  //setting displaying lines on graph to be formatted(like using graphics)
		  multiRenderer.setAntialiasing(true);
		  //setting to in scroll to false
		  multiRenderer.setInScroll(false);
		  //setting to set legend height of the graph
		  //multiRenderer.setLegendHeight(30);
		  //setting x axis label align
		  multiRenderer.setBarWidth(200);
		  multiRenderer.setXLabelsAlign(Align.CENTER);
		  //setting y axis label to align
		  multiRenderer.setYLabelsAlign(Align.LEFT);
		  //setting text style
		  multiRenderer.setTextTypeface("sans_serif", Typeface.NORMAL);
		  //setting no of values to display in y axis
		  multiRenderer.setYLabels(15);
		  // setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.
		  // if you use dynamic values then get the max y value and set here
		  multiRenderer.setYAxisMax(100);
		  multiRenderer.setYAxisMin(0);
		  //setting used to move the graph on xaxiz to .5 to the right
		  multiRenderer.setXAxisMin(-0.5);
		 //setting max values to be display in x axis
		  multiRenderer.setXAxisMax(1.5);
		  //setting bar size or space between two bars
		  multiRenderer.setBarSpacing(1.5);
		  //Setting background color of the graph to transparent
		  multiRenderer.setBackgroundColor(Color.TRANSPARENT);
		  //Setting margin color of the graph to transparent
		  multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
		  multiRenderer.setApplyBackgroundColor(true);
		 
		  //setting the margin size for the graph in the order top, left, bottom, right
		  multiRenderer.setMargins(new int[]{30, 30, 0, 30});

		  for(int i=0; i< x.length;i++){
		  multiRenderer.addXTextLabel(i, mMonth[i]);
		  }

		  // Adding incomeRenderer and expenseRenderer to multipleRenderer
		  // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
		  // should be same
		  multiRenderer.addSeriesRenderer(incomeRendererAtt);
		  multiRenderer.addSeriesRenderer(incomeRendererMed);
		  //this part is used to display graph on the xml
		  chartContainer = (LinearLayout) v.findViewById(R.id.chart);
		  //remove any views before u paint the chart
		  chartContainer.removeAllViews();
		  //drawing bar chart
		  mChart = ChartFactory.getBarChartView(getActivity().getApplicationContext(), dataset, multiRenderer,Type.STACKED);
		  chartContainer.addView(mChart);


		/*
		  chartContainer.removeAllViews();
		  //drawing bar chart
		  
		  dataset = new XYMultipleSeriesDataset();
		  incomeSeries = new XYSeries("Val");
		  incomeSeries.add(0, 50);
		  incomeSeries.add(1, 50);
		  dataset.addSeries(incomeSeries);
		  mChart = ChartFactory.getBarChartView(getActivity().getApplicationContext(), dataset, multiRenderer,Type.DEFAULT);
		  chartContainer.addView(mChart);
*/
		  this.useHandler();
	 }
	 
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.fragment_dialog, container, false);
	        WindowManager.LayoutParams wmlp = getDialog().getWindow().getAttributes();
	        wmlp.gravity = Gravity.RIGHT;
	        View tv = v.findViewById(R.id.textView3);
	        ((TextView)tv).setText("Dialog #" + mNum + ": using style " + this.mNum); //  + getNameForNum(mNum)
	        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
	      //  getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	        /* Watch for button clicks.
	        Button button = (Button)v.findViewById(R.id.show);
	        button.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                // When button is clicked, call up to owning activity.
	                ((FragmentDialog)getActivity()).showDialog();
	            }
	        });*/
	        openChart(v);
	        return v;
	    }
	 
	 public void useHandler() {
		    mHandler = new Handler();
		    mHandler.postDelayed(mRunnable, time_connection);
		    //mHandler.removeCallbacks(mRunnable);
		  }

	 @Override
	    public void onDestroy() {
	        super.onDestroy();
	        mHandler.removeCallbacks(mRunnable);
	    }
	 
	 private int giveMyCharColor(int flag){
		int r=0, g=0, b=0;
		 
		if(flag > delimitersColor[9])
			return Color.rgb(191, 54, 12);
		if(flag > delimitersColor[8])
			return Color.rgb(216, 67, 21);
		if(flag > delimitersColor[7])
			return Color.rgb(230, 74, 25);
		if(flag > delimitersColor[6])
			return Color.rgb(255, 179, 0);
		if(flag > delimitersColor[5])
			return Color.rgb(255, 193, 7);
		if(flag > delimitersColor[4])
			return Color.rgb(255, 202, 40);
		if(flag > delimitersColor[3])
			return Color.rgb(255, 213, 79);
		if(flag > delimitersColor[2])
			return Color.rgb(165, 214, 167);
		if(flag > delimitersColor[1])
			return Color.rgb(200, 230, 201);

		
		 return Color.rgb(232, 245, 233); 
	 }
	 
	 private Runnable mRunnable = new Runnable() {
		    @Override
		    public void run() {
		    	int att = ((MainActivity)getActivity()).getCurrAtt();
				int med = ((MainActivity)getActivity()).getCurrMed();
				
		    	Log.v("HelloEEG", "Aggiornooooooo att: " + att + "  med: " + med); 
		      /** Do something **/
		    	
				  //remove any views before u paint the chart
				  chartContainer.removeAllViews();
				  //determinazione dei colori da dare alle barre
				  
				  incomeRendererAtt.setColor(giveMyCharColor(att));
				  incomeRendererMed.setColor(giveMyCharColor(med));
				  
				  
				  dataset = new XYMultipleSeriesDataset();
				  incomeSeriesAtt = new XYSeries("Val1");
				  incomeSeriesMed = new XYSeries("Val2");
				  incomeSeriesAtt.add(0, att);
				  incomeSeriesMed.add(1, med);
				  dataset.addSeries(incomeSeriesAtt);
				  dataset.addSeries(incomeSeriesMed);
				  mChart = ChartFactory.getBarChartView(getActivity().getApplicationContext(), dataset, multiRenderer,Type.STACKED);
				  chartContainer.addView(mChart);
		    	
		      mHandler.postDelayed(mRunnable, time_connection);
		    }
		  };
	 
}
