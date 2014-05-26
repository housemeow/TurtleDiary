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
		createNutritionReport();
	}
	
	// 營養報表
	public void createNutritionReport() {
		layout.removeAllViews(); // 清除畫面
		int width = getWindowManager().getDefaultDisplay().getWidth();
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, width);
		Resources r = this.getResources(); // 取得手機資源
		int marginPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				20, r.getDisplayMetrics());
		float textSizePx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20,
				r.getDisplayMetrics());
		params.setMargins(marginPx, marginPx, marginPx, marginPx);
		int size = helper.getProteinGraphViewDataList(pid).size();
		if (size > 0) {
			String[] labels = new String[size];
			for (int i = 1; i < size - 1; i++) {
				labels[i] = "";
			}
			labels[0] = helper.getFeedLogDateString(pid, true);//firstLabel;
			labels[size - 1] = helper.getFeedLogDateString(pid, false);//lastLabel;
			layout.addView(getProteinGraphView(params, labels, textSizePx)); // 加入粗蛋白報表
			layout.addView(getFatGraphView(params, labels, textSizePx)); // 加入粗脂肪報表
			layout.addView(getFabricGraphView(params, labels, textSizePx)); // 加入粗纖維報表
			layout.addView(getCaGraphView(params, labels, textSizePx)); // 加入鈣報表
			layout.addView(getPGraphView(params, labels, textSizePx)); // 加入磷報表
			layout.addView(getCaPRatioGraphView(params, labels, textSizePx)); // 加入鈣磷比報表
		}
	}
	
	// getProteinGraphView
	public GraphView getProteinGraphView(LinearLayout.LayoutParams params, String[] labels, float textSizePx) {
		GraphView graphView = new LineGraphView(this, "粗蛋白 (%)");
		List<GraphViewData> proteinGraphViewDataList = helper
				.getProteinGraphViewDataList(pid);
		GraphViewSeries graphViewSeries = new GraphViewSeries(
				proteinGraphViewDataList
						.toArray(new GraphViewData[proteinGraphViewDataList
								.size()]));
		graphView.addSeries(graphViewSeries);
		graphView.setLayoutParams(params);
		graphView.setHorizontalLabels(labels);
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setNumHorizontalLabels(10);
		graphView.getGraphViewStyle().setNumVerticalLabels(10);
		graphView.getGraphViewStyle().setTextSize(textSizePx);
		return graphView;
	}
	
	// getFatGraphView
	public GraphView getFatGraphView(LinearLayout.LayoutParams params, String[] labels, float textSizePx) {
		GraphView graphView = new LineGraphView(this, "粗脂肪 (%)");
		List<GraphViewData> fatGraphViewDataList = helper
				.getFatGraphViewDataList(pid);
		GraphViewSeries graphViewSeries = new GraphViewSeries(
				fatGraphViewDataList
						.toArray(new GraphViewData[fatGraphViewDataList
								.size()]));
		graphView.addSeries(graphViewSeries);
		graphView.setLayoutParams(params);
		graphView.setHorizontalLabels(labels);
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setNumHorizontalLabels(10);
		graphView.getGraphViewStyle().setNumVerticalLabels(10);
		graphView.getGraphViewStyle().setTextSize(textSizePx);
		return graphView;
	}
	
	// getFabricGraphView
	public GraphView getFabricGraphView(LinearLayout.LayoutParams params, String[] labels, float textSizePx) {
		GraphView graphView = new LineGraphView(this, "粗纖維 (%)");
		List<GraphViewData> fabricGraphViewDataList = helper
				.getFabricGraphViewDataList(pid);
		GraphViewSeries graphViewSeries = new GraphViewSeries(
				fabricGraphViewDataList
						.toArray(new GraphViewData[fabricGraphViewDataList
								.size()]));
		graphView.addSeries(graphViewSeries);
		graphView.setLayoutParams(params);
		graphView.setHorizontalLabels(labels);
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setNumHorizontalLabels(10);
		graphView.getGraphViewStyle().setNumVerticalLabels(10);
		graphView.getGraphViewStyle().setTextSize(textSizePx);
		return graphView;
	}
	
	// getCaGraphView
	public GraphView getCaGraphView(LinearLayout.LayoutParams params, String[] labels, float textSizePx) {
		GraphView graphView = new LineGraphView(this, "鈣 (%)");
		List<GraphViewData> caGraphViewDataList = helper
				.getCaGraphViewDataList(pid);
		GraphViewSeries graphViewSeries = new GraphViewSeries(
				caGraphViewDataList
						.toArray(new GraphViewData[caGraphViewDataList
								.size()]));
		graphView.addSeries(graphViewSeries);
		graphView.setLayoutParams(params);
		graphView.setHorizontalLabels(labels);
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setNumHorizontalLabels(10);
		graphView.getGraphViewStyle().setNumVerticalLabels(10);
		graphView.getGraphViewStyle().setTextSize(textSizePx);
		return graphView;
	}
	
	// getPGraphView
	public GraphView getPGraphView(LinearLayout.LayoutParams params, String[] labels, float textSizePx) {
		GraphView graphView = new LineGraphView(this, "磷 (%)");
		List<GraphViewData> pGraphViewDataList = helper
				.getPGraphViewDataList(pid);
		GraphViewSeries graphViewSeries = new GraphViewSeries(
				pGraphViewDataList
						.toArray(new GraphViewData[pGraphViewDataList
								.size()]));
		graphView.addSeries(graphViewSeries);
		graphView.setLayoutParams(params);
		graphView.setHorizontalLabels(labels);
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setNumHorizontalLabels(10);
		graphView.getGraphViewStyle().setNumVerticalLabels(10);
		graphView.getGraphViewStyle().setTextSize(textSizePx);
		return graphView;
	}
	
	// getCaPRatioGraphView
	public GraphView getCaPRatioGraphView(LinearLayout.LayoutParams params, String[] labels, float textSizePx) {
		GraphView graphView = new LineGraphView(this, "鈣磷比 (比值)");
		List<GraphViewData> pCaRatioGraphViewDataList = helper
				.getCaPRatioGraphViewDataList(pid);
		GraphViewSeries graphViewSeries = new GraphViewSeries(
				pCaRatioGraphViewDataList
						.toArray(new GraphViewData[pCaRatioGraphViewDataList
								.size()]));
		graphView.addSeries(graphViewSeries);
		graphView.setLayoutParams(params);
		graphView.setHorizontalLabels(labels);
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setNumHorizontalLabels(10);
		graphView.getGraphViewStyle().setNumVerticalLabels(10);
		graphView.getGraphViewStyle().setTextSize(textSizePx);
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
			createNutritionReport();
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