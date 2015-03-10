package com.example.prova;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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



public class DialogFragBChart extends DialogFragment{
	int mNum;
	private GraphicalView mChart;
	private String[] mMonth = new String[] {
			 "Attention", "Meditation"
			 };
	
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
		 int[] income = { 100, 55 };

		// Creating an XYSeries for Income
		 XYSeries incomeSeries = new XYSeries("Val");
		 // Adding data to Income and Expense Series
		 for(int i=0;i<x.length;i++){
		 incomeSeries.add(i,income[i]);
		 }
		 
		// Creating a dataset to hold each series
		 XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		 // Adding Income Series to the dataset
		 dataset.addSeries(incomeSeries);

		// Creating XYSeriesRenderer to customize incomeSeries
		 XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
		 incomeRenderer.setColor(Color.CYAN); //color of the graph set to cyan
		 incomeRenderer.setFillPoints(true);
		 incomeRenderer.setLineWidth(2);
		 incomeRenderer.setDisplayChartValues(true);
		 incomeRenderer.setDisplayChartValuesDistance(10); //setting chart value distance 
		 
		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		 XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
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
		  multiRenderer.addSeriesRenderer(incomeRenderer);

		  //this part is used to display graph on the xml
		  LinearLayout chartContainer = (LinearLayout) v.findViewById(R.id.chart);
		  //remove any views before u paint the chart
		  chartContainer.removeAllViews();
		  //drawing bar chart
		  mChart = ChartFactory.getBarChartView(getActivity().getApplicationContext(), dataset, multiRenderer,Type.DEFAULT);
		  chartContainer.addView(mChart);
		  dataset.getSeriesAt(0).add(1, 100);
		  mChart.repaint();
/*		 
		  //adding the view to the linearlayout
		  chartContainer.addView(mChart);
		 // dataset.clear();
		  //incomeSeries.add(0,100);
		  dataset.getSeriesAt(0).add(0, 100);
		 // dataset.addSeries(incomeSeries);
		  //mChart.repaint();
*/
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
}
