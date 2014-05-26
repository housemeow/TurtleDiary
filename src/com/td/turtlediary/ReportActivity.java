package com.td.turtlediary;

import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.td.models.TurtleDiaryDatabaseHelper;

public class ReportActivity extends Activity {
	private LinearLayout layout;
	private GraphViewData[] FData = new GraphViewData[4];
	private GraphViewData[] WData = new GraphViewData[4];
	private TurtleDiaryDatabaseHelper helper = new TurtleDiaryDatabaseHelper(
			this);
	private int pid = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);

		pid = getIntent().getIntExtra("pid", -1);
		Log.i ("pid is ", pid+"");
		this.setTitle("營養");
		// init example series data
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
		// createNutritionChart();
		createNutritionChart();
	}

	// 營養
	public void createNutritionChart() {
		layout.removeAllViews();
		/*
		// Graph 1
		GraphView graphView1 = new LineGraphView(this, "粗蛋白 (%)");
		List<GraphViewData> proteinGraphViewDataList = helper
				.getProteinGraphViewDataList(pid);
		Log.i ("proteinGraphViewDataList in report size: ", proteinGraphViewDataList.size()+"");
		GraphViewSeries graphViewSeries = new GraphViewSeries(
				proteinGraphViewDataList
						.toArray(new GraphViewData[proteinGraphViewDataList
								.size()]));
		graphView1.addSeries(graphViewSeries);
		int width = getWindowManager().getDefaultDisplay().getWidth();
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, width);
		Resources r = this.getResources(); // 取得手機資源
		int px2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				20, r.getDisplayMetrics());
		params.setMargins(px2, px2, px2, px2);
		graphView1.setLayoutParams(params);
		int size = proteinGraphViewDataList.size();
		if (size > 0) {
			String[] labels = new String[size];
			for (int i = 1; i < size - 1; i++) {
				labels[i] = "";
			}
			labels[0] = helper.getFeedLogDateString(pid, true);//firstLabel;
			labels[size - 1] = helper.getFeedLogDateString(pid, false);//lastLabel;
			graphView1.setHorizontalLabels(labels);
		}
		graphView1.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView1.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView1.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		
		graphView1.getGraphViewStyle().setNumHorizontalLabels(4);
		graphView1.getGraphViewStyle().setNumVerticalLabels(10);
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20,
				r.getDisplayMetrics());
		graphView1.getGraphViewStyle().setTextSize(px);
		*/
		layout.addView(getProteinGraphView());
	}
	
	// 營養報表
	public void createNutritionReport() {
		layout.removeAllViews(); // 清除畫面
		layout.addView(getProteinGraphView()); // 加入粗蛋白報表
	}
	
	// getProteinGraphView
	public GraphView getProteinGraphView() {
		GraphView graphView = new LineGraphView(this, "粗蛋白 (%)");
		List<GraphViewData> proteinGraphViewDataList = helper
				.getProteinGraphViewDataList(pid);
		GraphViewSeries graphViewSeries = new GraphViewSeries(
				proteinGraphViewDataList
						.toArray(new GraphViewData[proteinGraphViewDataList
								.size()]));
		graphView.addSeries(graphViewSeries);
		int width = getWindowManager().getDefaultDisplay().getWidth();
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, width);
		Resources r = this.getResources(); // 取得手機資源
		int px2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				20, r.getDisplayMetrics());
		params.setMargins(px2, px2, px2, px2);
		graphView.setLayoutParams(params);
		int size = proteinGraphViewDataList.size();
		if (size > 0) {
			String[] labels = new String[size];
			for (int i = 1; i < size - 1; i++) {
				labels[i] = "";
			}
			labels[0] = helper.getFeedLogDateString(pid, true);//firstLabel;
			labels[size - 1] = helper.getFeedLogDateString(pid, false);//lastLabel;
			graphView.setHorizontalLabels(labels);
		}
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		
		graphView.getGraphViewStyle().setNumHorizontalLabels(4);
		graphView.getGraphViewStyle().setNumVerticalLabels(10);
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20,
				r.getDisplayMetrics());
		graphView.getGraphViewStyle().setTextSize(px);
		return graphView;
	}

	// 甲長
	public void createShellLengthChart() {
		layout.removeAllViews();
		List<GraphViewData> graphViewDataList = helper
				.getShellLengthGraphViewDataList(pid);
		createMeasureLogGraphView(graphViewDataList);
	}

	// 體重
	public void createWeightChart() {
		layout.removeAllViews();

		List<GraphViewData> graphViewDataList = helper
				.getWeightGraphViewDataList(pid);
		createMeasureLogGraphView(graphViewDataList);
	}

	// 傑克森量表
	public void createJacksonRatioChart() {
		layout.removeAllViews();

		List<GraphViewData> graphViewDataList = helper
				.getJacksonGraphViewDataList(pid);
		createMeasureLogGraphView(graphViewDataList);
	}

	private void createMeasureLogGraphView(List<GraphViewData> graphViewDataList) {
		String firstLabel = helper.getMeasureLogDateString(pid, true);
		String lastLabel = helper.getMeasureLogDateString(pid, false);
		createGraphView(graphViewDataList, firstLabel, lastLabel);
	}

	private void createGraphView(List<GraphViewData> graphViewDataList,
			String firstLabel, String lastLabel) {
		int size = graphViewDataList.size();
		GraphViewSeries shellLengthSeries = new GraphViewSeries(
				graphViewDataList.toArray(new GraphViewData[size]));

		GraphView graphView = createLineGraphView();

		graphView.addSeries(shellLengthSeries); // data

		if (size > 0) {
			String[] labels = new String[size];
			for (int i = 1; i < size - 1; i++) {
				labels[i] = "";
			}
			labels[0] = firstLabel;
			labels[size - 1] = lastLabel;
			graphView.setHorizontalLabels(labels);
		}

		graphView.getGraphViewStyle().setNumVerticalLabels(8);
		layout.addView(graphView);
	}

	private GraphView createLineGraphView() {
		GraphView graphView = new LineGraphView(this, "");
		int width = getWindowManager().getDefaultDisplay().getWidth();
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, width);
		int px2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				20, getResources().getDisplayMetrics());
		params.setMargins(px2, px2, px2, px2);
		graphView.setLayoutParams(params);
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		int measureLogCount = (int) helper.getMeasureLogCount(pid);
		graphView.getGraphViewStyle().setNumHorizontalLabels(measureLogCount);
		return graphView;
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
			this.setTitle("營養");
			createNutritionChart();
			return super.onOptionsItemSelected(item);
		case R.id.shellLength:
			this.setTitle("甲長");
			createShellLengthChart();
			return super.onOptionsItemSelected(item);
		case R.id.weight:
			this.setTitle("體重");
			createWeightChart();
			return super.onOptionsItemSelected(item);
		case R.id.jacksonRatio:
			this.setTitle("傑克森量表");
			createJacksonRatioChart();
			return super.onOptionsItemSelected(item);
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * public void createFavoriteFoodChart() { layout.removeAllViews();
	 * GraphViewSeries FFSeries = new GraphViewSeries( new
	 * GraphView.GraphViewData[] { new GraphViewData(1, 4d), new
	 * GraphViewData(2, 7d), new GraphViewData(3, 11d), new GraphViewData(4,
	 * 22d) }); GraphView graphView = new LineGraphView(this, "");
	 * graphView.addSeries(FFSeries); // data graphView .setHorizontalLabels(new
	 * String[] { "xxx", "xxx", "xxx", "xxx" });
	 * graphView.getGraphViewStyle().setGridColor(Color.BLACK);
	 * graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
	 * graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
	 * graphView.getGraphViewStyle().setNumHorizontalLabels(4);
	 * graphView.getGraphViewStyle().setNumVerticalLabels(8);
	 * layout.addView(graphView); }
	 */
}