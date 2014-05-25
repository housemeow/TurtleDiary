package com.td.turtlediary;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
import com.td.models.MeasureLog;
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
		createNutritionChart();
	}

	// 營養
	public void createNutritionChart() {
		layout.removeAllViews();
		ArrayList<GraphViewData> tgd2 = new ArrayList<GraphView.GraphViewData>();
		GraphViewData viewData1 = new GraphViewData(1, 80d);
		GraphViewData viewData2 = new GraphViewData(2, 0d);
		GraphViewData viewData3 = new GraphViewData(3, 100d);
		GraphViewData viewData4 = new GraphViewData(4, 92d);
		tgd2.add(viewData1);
		tgd2.add(viewData2);
		tgd2.add(viewData3);
		tgd2.add(viewData4);
		ArrayList<String> tgds = new ArrayList<String>();
		tgds.add(new String(""));
		tgds.add(new String(""));
		tgds.add(new String(""));
		tgds.add(new String(""));
		tgds.set(0, new String("123"));
		tgds.set(tgds.size() - 1, new String("456"));
		/*
		 * GraphViewSeries NSeries = new GraphViewSeries( new
		 * GraphView.GraphViewData[] { new GraphViewData(1, 80d), new
		 * GraphViewData(2, 57d), new GraphViewData(3, 99d), new
		 * GraphViewData(4, 92d) });
		 */
		// Graph 1
		GraphView graphView1 = new LineGraphView(this, "粗蛋白 (%)");
		GraphViewData[] maxProteinData = new GraphViewData[2];
		maxProteinData[0] = new GraphViewData(1, 20d);
		maxProteinData[1] = new GraphViewData(4, 20d);
		GraphViewSeries maxProteinSeries = new GraphViewSeries("Max",
				new GraphViewSeriesStyle(Color.RED, 1), FData);
		graphView1.addSeries(maxProteinSeries);

		graphView1.addSeries(helper.getProteinGraphViewSeries(pid));
		graphView1.addSeries(new GraphViewSeries(tgd2
				.toArray(new GraphViewData[tgd2.size()]))); // data
		// graphView
		// .setHorizontalLabels(new String[] { "xxx", "xxx", "xxx", "xxx" });
		int width = getWindowManager().getDefaultDisplay().getWidth();
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, width);
		Resources r = this.getResources(); // 取得手機資源
		int px2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				20, r.getDisplayMetrics());
		params.setMargins(px2, px2, px2, px2);
		graphView1.setLayoutParams(params);
		graphView1.setHorizontalLabels(tgds.toArray(new String[tgds.size()]));
		graphView1.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView1.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView1.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView1.getGraphViewStyle().setNumHorizontalLabels(4);
		graphView1.getGraphViewStyle().setNumVerticalLabels(10);
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20,
				r.getDisplayMetrics());
		graphView1.getGraphViewStyle().setTextSize(px);

		// Graph 2
		GraphView graphView2 = new LineGraphView(this, "粗脂肪 (%)");
		// graphView.addSeries(NSeries); // data
		graphView2.addSeries(new GraphViewSeries(tgd2
				.toArray(new GraphViewData[tgd2.size()]))); // data
		// graphView
		// .setHorizontalLabels(new String[] { "xxx", "xxx", "xxx", "xxx" });
		graphView2.setLayoutParams(params);
		graphView2.setHorizontalLabels(tgds.toArray(new String[tgds.size()]));
		graphView2.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView2.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView2.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView2.getGraphViewStyle().setNumHorizontalLabels(4);
		graphView2.getGraphViewStyle().setNumVerticalLabels(10);
		graphView2.getGraphViewStyle().setTextSize(px);

		// Graph 3
		GraphView graphView3 = new LineGraphView(this, "粗纖維 (%)");
		// graphView.addSeries(NSeries); // data
		graphView3.addSeries(new GraphViewSeries(tgd2
				.toArray(new GraphViewData[tgd2.size()]))); // data
		// graphView
		// .setHorizontalLabels(new String[] { "xxx", "xxx", "xxx", "xxx" });
		graphView3.setLayoutParams(params);
		graphView3.setHorizontalLabels(tgds.toArray(new String[tgds.size()]));
		graphView3.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView3.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView3.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView3.getGraphViewStyle().setNumHorizontalLabels(4);
		graphView3.getGraphViewStyle().setNumVerticalLabels(10);
		graphView3.getGraphViewStyle().setTextSize(px);

		// Graph 4
		GraphView graphView4 = new LineGraphView(this, "鈣 (%)");
		// graphView.addSeries(NSeries); // data
		graphView4.addSeries(new GraphViewSeries(tgd2
				.toArray(new GraphViewData[tgd2.size()]))); // data
		// graphView
		// .setHorizontalLabels(new String[] { "xxx", "xxx", "xxx", "xxx" });
		graphView4.setLayoutParams(params);
		graphView4.setHorizontalLabels(tgds.toArray(new String[tgds.size()]));
		graphView4.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView4.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView4.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView4.getGraphViewStyle().setNumHorizontalLabels(4);
		graphView4.getGraphViewStyle().setNumVerticalLabels(10);
		graphView4.getGraphViewStyle().setTextSize(px);

		// Graph 5
		GraphView graphView5 = new LineGraphView(this, "磷 (%)");
		// graphView.addSeries(NSeries); // data
		graphView5.addSeries(new GraphViewSeries(tgd2
				.toArray(new GraphViewData[tgd2.size()]))); // data
		// graphView
		// .setHorizontalLabels(new String[] { "xxx", "xxx", "xxx", "xxx" });
		graphView5.setLayoutParams(params);
		graphView5.setHorizontalLabels(tgds.toArray(new String[tgds.size()]));
		graphView5.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView5.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView5.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView5.getGraphViewStyle().setNumHorizontalLabels(4);
		graphView5.getGraphViewStyle().setNumVerticalLabels(10);
		graphView5.getGraphViewStyle().setTextSize(px);

		// Graph 5
		GraphView graphView6 = new LineGraphView(this, "鈣:磷 (比值)");
		// graphView.addSeries(NSeries); // data
		graphView6.addSeries(new GraphViewSeries(tgd2
				.toArray(new GraphViewData[tgd2.size()]))); // data
		// graphView
		// .setHorizontalLabels(new String[] { "xxx", "xxx", "xxx", "xxx" });
		graphView6.setLayoutParams(params);
		graphView6.setHorizontalLabels(tgds.toArray(new String[tgds.size()]));
		graphView6.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView6.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView6.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView6.getGraphViewStyle().setNumHorizontalLabels(4);
		graphView6.getGraphViewStyle().setNumVerticalLabels(10);
		graphView6.getGraphViewStyle().setTextSize(px);
		layout.addView(graphView1);
		layout.addView(graphView2);
		layout.addView(graphView3);
		layout.addView(graphView4);
		layout.addView(graphView5);
		layout.addView(graphView6);
	}

	// 甲長
	public void createShellLengthChart() {
		layout.removeAllViews();

		List<GraphViewData> graphViewDataList = helper
				.getShellLengthGraphViewDataList(pid);
		int size = graphViewDataList.size();
		GraphViewSeries shellLengthSeries = new GraphViewSeries(
				graphViewDataList.toArray(new GraphViewData[size]));

		GraphView graphView = new LineGraphView(this, "");
		int width = getWindowManager().getDefaultDisplay().getWidth();
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, width);
		Resources r = this.getResources(); // 取得手機資源
		int px2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				20, r.getDisplayMetrics());
		params.setMargins(px2, px2, px2, px2);
		graphView.setLayoutParams(params);
		graphView.addSeries(shellLengthSeries); // data
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		int measureLogCount = (int) helper.getMeasureLogCount(pid);
		graphView.getGraphViewStyle().setNumHorizontalLabels(measureLogCount);

		if (size > 0) {
			String[] labels = new String[size];
			for(int i=1;i<size-1;i++){
				labels[i]="";
			}
			labels[0] = helper.getFirstMeasureLogDateString(pid);
			labels[size-1] = helper.getLastMeasureLogDateString(pid);
			graphView.setHorizontalLabels(labels);
		}

		graphView.getGraphViewStyle().setNumVerticalLabels(8);
		layout.addView(graphView);
	}

	// 體重
	public void createWeightChart() {
		layout.removeAllViews();

		List<GraphViewData> graphViewDataList = helper
				.getWeightGraphViewDataList(pid);
		int size = graphViewDataList.size();
		GraphViewSeries shellLengthSeries = new GraphViewSeries(
				graphViewDataList.toArray(new GraphViewData[size]));

		GraphView graphView = new LineGraphView(this, "");
		int width = getWindowManager().getDefaultDisplay().getWidth();
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, width);
		Resources r = this.getResources(); // 取得手機資源
		int px2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				20, r.getDisplayMetrics());
		params.setMargins(px2, px2, px2, px2);
		graphView.setLayoutParams(params);
		graphView.addSeries(shellLengthSeries); // data
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		int measureLogCount = (int) helper.getMeasureLogCount(pid);
		graphView.getGraphViewStyle().setNumHorizontalLabels(measureLogCount);

		if (size > 0) {
			String[] labels = new String[size];
			for(int i=1;i<size-1;i++){
				labels[i]="";
			}
			labels[0] = helper.getFirstMeasureLogDateString(pid);
			labels[size-1] = helper.getLastMeasureLogDateString(pid);
			graphView.setHorizontalLabels(labels);
		}

		graphView.getGraphViewStyle().setNumVerticalLabels(8);
		layout.addView(graphView);
	}

	// 傑克森量表
	public void createJacksonRatioChart() {
		layout.removeAllViews();

		List<GraphViewData> graphViewDataList = helper
				.getJacksonGraphViewDataList(pid);
		int size = graphViewDataList.size();
		GraphViewSeries shellLengthSeries = new GraphViewSeries(
				graphViewDataList.toArray(new GraphViewData[size]));

		GraphView graphView = new LineGraphView(this, "");
		int width = getWindowManager().getDefaultDisplay().getWidth();
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, width);
		Resources r = this.getResources(); // 取得手機資源
		int px2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				20, r.getDisplayMetrics());
		params.setMargins(px2, px2, px2, px2);
		graphView.setLayoutParams(params);
		graphView.addSeries(shellLengthSeries); // data
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		int measureLogCount = (int) helper.getMeasureLogCount(pid);
		graphView.getGraphViewStyle().setNumHorizontalLabels(measureLogCount);

		if (size > 0) {
			String[] labels = new String[size];
			for(int i=1;i<size-1;i++){
				labels[i]="";
			}
			labels[0] = helper.getFirstMeasureLogDateString(pid);
			labels[size-1] = helper.getLastMeasureLogDateString(pid);
			graphView.setHorizontalLabels(labels);
		}

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