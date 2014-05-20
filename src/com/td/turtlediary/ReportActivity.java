package com.td.turtlediary;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class ReportActivity extends Activity {
	private LinearLayout layout;
	private GraphViewData[] FData = new GraphViewData[4];
	private GraphViewData[] WData = new GraphViewData[4];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		// init example series data
		this.setTitle("甲長");
		layout = (LinearLayout) findViewById(R.id.chart);
		FData = new GraphViewData[4];
		WData = new GraphViewData[4];
		FData[0] = new GraphViewData(1, 0.8d);
		FData[1] = new GraphViewData(2, 0.8d);
		FData[2] = new GraphViewData(3, 0.9d);
		FData[3] = new GraphViewData(4, 1.0d);
		WData[0] = new GraphViewData(1, 0.7d);
		WData[1] = new GraphViewData(2, 0.74d);
		WData[2] = new GraphViewData(3, 0.84d);
		WData[3] = new GraphViewData(4, 0.9d);
		createNutritionChart();
	}

	public void createShellLengthChart() {
		layout.removeAllViews();
		GraphViewSeries FSeries = new GraphViewSeries(FData);
		GraphView graphView = new LineGraphView(this, "");
		graphView.addSeries(FSeries); // data
		graphView.setHorizontalLabels(new String[] { "4/11", "4/12", "4/13",
				"4/14" });
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setNumHorizontalLabels(4);
		graphView.getGraphViewStyle().setNumVerticalLabels(8);
		layout.addView(graphView);
	}

	public void createFavoriteFoodChart() {
		layout.removeAllViews();
		GraphViewSeries FFSeries = new GraphViewSeries(
				new GraphView.GraphViewData[] { new GraphViewData(1, 4d),
						new GraphViewData(2, 7d), new GraphViewData(3, 11d),
						new GraphViewData(4, 22d) });
		GraphView graphView = new LineGraphView(this, "");
		graphView.addSeries(FFSeries); // data
		graphView
				.setHorizontalLabels(new String[] { "xxx", "xxx", "xxx", "xxx" });
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setNumHorizontalLabels(4);
		graphView.getGraphViewStyle().setNumVerticalLabels(8);
		layout.addView(graphView);
	}

	public void createJacksonRatioChart() {
		layout.removeAllViews();
		GraphViewSeries FSeries = new GraphViewSeries("甲長",
				new GraphViewSeriesStyle(Color.MAGENTA, 1), FData);
		GraphViewSeries WSeries = new GraphViewSeries("體重",
				new GraphViewSeriesStyle(Color.BLUE, 1), WData);
		GraphView graphView = new LineGraphView(this, "");
		graphView.addSeries(FSeries); // data
		graphView.addSeries(WSeries);
		graphView.setShowLegend(true);
		graphView
				.setHorizontalLabels(new String[] { "xxx", "xxx", "xxx", "xxx" });
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setNumHorizontalLabels(4);
		graphView.getGraphViewStyle().setNumVerticalLabels(8);
		layout.addView(graphView);
	}

	public void createNutritionChart() {
		layout.removeAllViews();
		GraphViewSeries NSeries = new GraphViewSeries(
				new GraphView.GraphViewData[] { new GraphViewData(1, 80d),
						new GraphViewData(2, 57d), new GraphViewData(3, 100d),
						new GraphViewData(4, 92d) });
		GraphView graphView = new LineGraphView(this, "");
		graphView.addSeries(NSeries); // data
		graphView
				.setHorizontalLabels(new String[] { "xxx", "xxx", "xxx", "xxx" });
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setNumHorizontalLabels(4);
		graphView.getGraphViewStyle().setNumVerticalLabels(8);
		layout.addView(graphView);
	}

	public void createWeightChart() {
		layout.removeAllViews();
		GraphViewSeries WSeries = new GraphViewSeries(WData);
		GraphView graphView = new LineGraphView(this, "");
		graphView.addSeries(WSeries); // data
		graphView.setHorizontalLabels(new String[] { "4/11", "4/12", "4/13",
				"4/14" });
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setNumHorizontalLabels(4);
		graphView.getGraphViewStyle().setNumVerticalLabels(8);
		layout.addView(graphView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.report, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		this.setTitle(item.getTitle());
		switch (item.getItemId()) {
		case R.id.nutrition:
			createNutritionChart();
			return super.onOptionsItemSelected(item);
		case R.id.shellLength:
			createShellLengthChart();
			return super.onOptionsItemSelected(item);
		case R.id.weight:
			createWeightChart();
			return super.onOptionsItemSelected(item);
		case R.id.jacksonRatio:
			createJacksonRatioChart();
			return super.onOptionsItemSelected(item);
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}