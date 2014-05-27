package com.td.turtlediary;

import java.text.NumberFormat;
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
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
import com.td.models.MeasureLog;
import com.td.models.Nutrition;
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
		Log.i("pid is ", pid + "");
		this.setTitle("營養");
		// init example series data
		layout = (LinearLayout) findViewById(R.id.selectFoodActivityProteinEditText);
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
		int marginPx = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 20, r.getDisplayMetrics());
		float textSizePx = TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 8, r.getDisplayMetrics());
		params.setMargins(marginPx, marginPx, marginPx, marginPx);
		int size = helper.getProteinGraphViewDataList(pid).size();
		Nutrition nutrition = helper.getPetNutrition(pid);
		double proteinAvg = nutrition.getProteinPercentage();
		double fatAvg = nutrition.getFatPercentage();
		double fabricAvg = nutrition.getFabricPercentage();
		double caAvg = nutrition.getCaPercentage();
		double pAvg = nutrition.getPPercentage();
		double caPRationAvg = nutrition.getCaPRatio();
		if (size == 0)
		{
			TextView showReportTxt = new TextView(this);
			showReportTxt.setTextSize(textSizePx);
			showReportTxt.setText("尚無餵食紀錄");
			layout.addView(showReportTxt);
		}
		else if (size == 1)
		{
			TextView showReportTxt = new TextView(this);
			StringBuilder sBuilder = new StringBuilder();
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(5);
			proteinAvg = Double.parseDouble(nf.format(proteinAvg));
			fatAvg = Double.parseDouble(nf.format(fatAvg));
			fabricAvg = Double.parseDouble(nf.format(fabricAvg));
			caAvg = Double.parseDouble(nf.format(caAvg));
			pAvg = Double.parseDouble(nf.format(pAvg));
			caPRationAvg = Double.parseDouble(nf.format(caPRationAvg));
			sBuilder.append("------目前只有一筆餵食紀錄------\n")
					.append("日期：").append(helper.getFeedLogDateString(pid, true)).append("\n")
					.append("粗蛋白： ").append(proteinAvg).append("%\n")
					.append("粗脂肪： ").append(fatAvg).append("%\n")
					.append("粗纖維： ").append(fabricAvg).append("%\n")
					.append("鈣     ： ").append(caAvg).append("%\n")
					.append("磷     ： ").append(pAvg).append("%\n")
					.append("鈣磷比： ").append(caPRationAvg).append("%\n\n")
					.append("------建議數據如下------\n")
					.append("粗蛋白最大值： ").append(20).append("%\n")
					.append("粗脂肪最大值： ").append(10).append("%\n")
					.append("粗纖維最小/最大值： ").append(12 + "%/" + 25).append("%\n")
					.append("鈣最小值     ： ").append(1.5).append("%\n")
					.append("磷建議值     ： ").append(0.8).append("%\n")
					.append("鈣磷比最小值： ").append(2).append("%\n");
			showReportTxt.setText(sBuilder.toString());
			showReportTxt.setTextSize(textSizePx);
			layout.addView(showReportTxt);
		}
		else {
			String[] labels = new String[size];
			for (int i = 1; i < size - 1; i++) {
				labels[i] = "";
			}
			labels[0] = helper.getFeedLogDateString(pid, true);// firstLabel;
			labels[size - 1] = helper.getFeedLogDateString(pid, false);// lastLabel;
			layout.addView(getProteinGraphView(proteinAvg)); // 加入粗蛋白報表
			layout.addView(getFatGraphView(fatAvg)); // 加入粗脂肪報表
			layout.addView(getFabricGraphView(fabricAvg)); // 加入粗纖維報表
			layout.addView(getCaGraphView(caAvg)); // 加入鈣報表
			layout.addView(getPGraphView(pAvg)); // 加入磷報表
			layout.addView(getCaPRatioGraphView(caPRationAvg)); // 加入鈣磷比報表
		}
	}
	
	private GraphView getReportLinearGraphView()
	{
		GraphView graphView = new LineGraphView(this, "");
		int width = getWindowManager().getDefaultDisplay().getWidth();
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, width);
		Resources r = this.getResources(); // 取得手機資源
		int marginPx = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 20, r.getDisplayMetrics());
		float textSizePx = TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 8, r.getDisplayMetrics());
		params.setMargins(marginPx, marginPx, marginPx, marginPx);
		int size = helper.getProteinGraphViewDataList(pid).size();
		String[] labels = new String[size];
		for (int i = 1; i < size - 1; i++) {
			labels[i] = "";
		}
		labels[0] = helper.getFeedLogDateString(pid, true);// firstLabel;
		labels[size - 1] = helper.getFeedLogDateString(pid, false);// lastLabel;
		graphView.setHorizontalLabels(labels);
		graphView.setShowLegend(true);
		graphView.setLayoutParams(params);
		graphView.getGraphViewStyle().setGridColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		graphView.getGraphViewStyle().setNumHorizontalLabels(10);
		graphView.getGraphViewStyle().setNumVerticalLabels(10);
		graphView.getGraphViewStyle().setTextSize(textSizePx);
		return graphView;
	}

	// getProteinGraphView
	public GraphView getProteinGraphView(double proteinAvg) {
		GraphView graphView = getReportLinearGraphView();
		graphView.setTitle("粗蛋白 (%)");
		List<GraphViewData> proteinGraphViewDataList = helper
				.getProteinGraphViewDataList(pid);
		int size = proteinGraphViewDataList.size();
		GraphViewData[] proteingGraphViewDatas = proteinGraphViewDataList
				.toArray(new GraphViewData[proteinGraphViewDataList.size()]);
		GraphViewSeries graphViewSeries = new GraphViewSeries("粗蛋白",
				new GraphViewSeriesStyle(Color.BLACK, 5),
				proteingGraphViewDatas);
		graphView.addSeries(graphViewSeries);
		GraphViewData[] graphViewDatas = new GraphViewData[size];
		GraphViewData[] maxProteinGraphViewDatas = new GraphViewData[size];
		double maxProtein = 20.0;
		for (int i = 0; i < size; i++) {
			graphViewDatas[i] = new GraphViewData(i + 1, proteinAvg);
			maxProteinGraphViewDatas[i] = new GraphViewData(i + 1, maxProtein);
		}
		GraphViewSeries proteinAvgGraphViewSeries = new GraphViewSeries(
				"AVG", new GraphViewSeriesStyle(Color.GREEN, 0),
				graphViewDatas);
		GraphViewSeries maxProteinAvgGraphViewSeries = new GraphViewSeries(
				"MAX", new GraphViewSeriesStyle(Color.RED, 0),
				maxProteinGraphViewDatas);
		graphView.addSeries(proteinAvgGraphViewSeries);
		graphView.addSeries(maxProteinAvgGraphViewSeries);
		return graphView;
	}

	// getFatGraphView
	public GraphView getFatGraphView(double fatAvg) {
		GraphView graphView = getReportLinearGraphView();
		graphView.setTitle("粗脂肪 (%)");
		List<GraphViewData> fatGraphViewDataList = helper
				.getFatGraphViewDataList(pid);
		int size = fatGraphViewDataList.size();
		GraphViewData[] proteingGraphViewDatas = fatGraphViewDataList
				.toArray(new GraphViewData[fatGraphViewDataList.size()]);
		GraphViewSeries graphViewSeries = new GraphViewSeries("粗脂肪",
				new GraphViewSeriesStyle(Color.BLACK, 5),
				proteingGraphViewDatas);
		graphView.addSeries(graphViewSeries);
		GraphViewData[] graphViewDatas = new GraphViewData[size];
		GraphViewData[] maxFatGraphViewDatas = new GraphViewData[size];
		double maxFat = 10.0;
		for (int i = 0; i < size; i++) {
			graphViewDatas[i] = new GraphViewData(i + 1, fatAvg);
			maxFatGraphViewDatas[i] = new GraphViewData(i + 1, maxFat);
		}
		GraphViewSeries fatAvgGraphViewSeries = new GraphViewSeries(
				"AVG", new GraphViewSeriesStyle(Color.GREEN, 0),
				graphViewDatas);
		GraphViewSeries maxFatGraphViewSeries = new GraphViewSeries(
				"MAX", new GraphViewSeriesStyle(Color.RED, 0),
				maxFatGraphViewDatas);
		graphView.addSeries(fatAvgGraphViewSeries);
		graphView.addSeries(maxFatGraphViewSeries);
		return graphView;
	}

	// getFabricGraphView
	public GraphView getFabricGraphView(double fabricAvg) {
		GraphView graphView = getReportLinearGraphView();
		graphView.setTitle("粗纖維 (%)");
		List<GraphViewData> fabricGraphViewDataList = helper
				.getFabricGraphViewDataList(pid);
		int size = fabricGraphViewDataList.size();
		GraphViewData[] proteingGraphViewDatas = fabricGraphViewDataList
				.toArray(new GraphViewData[fabricGraphViewDataList.size()]);
		GraphViewSeries graphViewSeries = new GraphViewSeries("粗纖維",
				new GraphViewSeriesStyle(Color.BLACK, 5),
				proteingGraphViewDatas);
		graphView.addSeries(graphViewSeries);
		GraphViewData[] graphViewDatas = new GraphViewData[size];
		GraphViewData[] maxFabricgraphViewDatas = new GraphViewData[size];
		GraphViewData[] minFabricgraphViewDatas = new GraphViewData[size];
		double maxFabric = 25.0;
		double minFabric = 12.0;
		for (int i = 0; i < size; i++) {
			graphViewDatas[i] = new GraphViewData(i + 1, fabricAvg);
			maxFabricgraphViewDatas[i] = new GraphViewData(i + 1, maxFabric);
			minFabricgraphViewDatas[i] = new GraphViewData(i + 1, minFabric);
		}
		GraphViewSeries fatAvgGraphViewSeries = new GraphViewSeries(
				"AVG", new GraphViewSeriesStyle(Color.GREEN, 0),
				graphViewDatas);
		GraphViewSeries maxFabricGraphViewSeries = new GraphViewSeries(
				"MAX", new GraphViewSeriesStyle(Color.RED, 0),
				maxFabricgraphViewDatas);
		GraphViewSeries minFabricGraphViewSeries = new GraphViewSeries(
				"MIN", new GraphViewSeriesStyle(Color.BLUE, 0),
				minFabricgraphViewDatas);
		graphView.addSeries(fatAvgGraphViewSeries);
		graphView.addSeries(maxFabricGraphViewSeries);
		graphView.addSeries(minFabricGraphViewSeries);
		return graphView;
	}

	// getCaGraphView
	public GraphView getCaGraphView(double caAvg) {
		GraphView graphView = getReportLinearGraphView();
		graphView.setTitle("鈣 (%)");
		List<GraphViewData> caGraphViewDataList = helper
				.getCaGraphViewDataList(pid);
		int size = caGraphViewDataList.size();
		GraphViewData[] proteingGraphViewDatas = caGraphViewDataList
				.toArray(new GraphViewData[caGraphViewDataList.size()]);
		GraphViewSeries graphViewSeries = new GraphViewSeries("鈣",
				new GraphViewSeriesStyle(Color.BLACK, 5),
				proteingGraphViewDatas);
		graphView.addSeries(graphViewSeries);
		GraphViewData[] graphViewDatas = new GraphViewData[size];
		GraphViewData[] caAvgGraphViewDatas = new GraphViewData[size];
		double minCa = 1.5;
		for (int i = 0; i < size; i++) {
			graphViewDatas[i] = new GraphViewData(i + 1, caAvg);
			caAvgGraphViewDatas[i] = new GraphViewData(i + 1, minCa);
		}
		GraphViewSeries caAvgGraphViewSeries = new GraphViewSeries(
				"AVG", new GraphViewSeriesStyle(Color.GREEN, 0),
				graphViewDatas);
		GraphViewSeries minCaGraphViewSeries = new GraphViewSeries(
				"MIN", new GraphViewSeriesStyle(Color.BLUE, 0),
				caAvgGraphViewDatas);
		graphView.addSeries(caAvgGraphViewSeries);
		graphView.addSeries(minCaGraphViewSeries);
		return graphView;
	}

	// getPGraphView
	public GraphView getPGraphView(double pAvg) {
		GraphView graphView = getReportLinearGraphView();
		graphView.setTitle("磷 (%)");
		List<GraphViewData> pGraphViewDataList = helper
				.getPGraphViewDataList(pid);
		int size = pGraphViewDataList.size();
		GraphViewData[] proteingGraphViewDatas = pGraphViewDataList
				.toArray(new GraphViewData[pGraphViewDataList.size()]);
		GraphViewSeries graphViewSeries = new GraphViewSeries("磷",
				new GraphViewSeriesStyle(Color.BLACK, 5),
				proteingGraphViewDatas);
		graphView.addSeries(graphViewSeries);
		GraphViewData[] graphViewDatas = new GraphViewData[size];
		GraphViewData[] suggestPGraphViewDatas = new GraphViewData[size];
		double suggestP = 0.8;
		for (int i = 0; i < size; i++) {
			graphViewDatas[i] = new GraphViewData(i + 1, pAvg);
			suggestPGraphViewDatas[i] = new GraphViewData(i + 1, suggestP);
		}
		GraphViewSeries pAvgGraphViewSeries = new GraphViewSeries(
				"AVG", new GraphViewSeriesStyle(Color.GREEN, 0),
				graphViewDatas);
		GraphViewSeries suggestPGraphViewSeries = new GraphViewSeries(
				"建議", new GraphViewSeriesStyle(Color.MAGENTA, 0),
				suggestPGraphViewDatas);
		graphView.addSeries(pAvgGraphViewSeries);
		graphView.addSeries(suggestPGraphViewSeries);
		return graphView;
	}

	// getCaPRatioGraphView
	public GraphView getCaPRatioGraphView(double caPRationAvg) {
		GraphView graphView = new LineGraphView(this, "鈣磷比 (%)");
		graphView.setTitle("鈣磷比 (%)");
		List<GraphViewData> caPRatioGraphViewDataList = helper
				.getCaPRatioGraphViewDataList(pid);
		int size = caPRatioGraphViewDataList.size();
		GraphViewData[] proteingGraphViewDatas = caPRatioGraphViewDataList
				.toArray(new GraphViewData[caPRatioGraphViewDataList.size()]);
		GraphViewSeries graphViewSeries = new GraphViewSeries("鈣磷比",
				new GraphViewSeriesStyle(Color.BLACK, 5),
				proteingGraphViewDatas);
		graphView.addSeries(graphViewSeries);
		GraphViewData[] graphViewDatas = new GraphViewData[size];
		GraphViewData[] minCaPRatioGraphViewDatas = new GraphViewData[size];
		double minCaPRatio = 2.0;
		for (int i = 0; i < size; i++) {
			graphViewDatas[i] = new GraphViewData(i + 1, caPRationAvg);
			minCaPRatioGraphViewDatas[i] = new GraphViewData(i + 1, minCaPRatio);
		}
		GraphViewSeries caPRatioAvgGraphViewSeries = new GraphViewSeries(
				"AVG", new GraphViewSeriesStyle(Color.GREEN, 0),
				graphViewDatas);
		GraphViewSeries minCaPRatioAvgGraphViewSeries = new GraphViewSeries(
				"MIN", new GraphViewSeriesStyle(Color.BLUE, 0),
				minCaPRatioGraphViewDatas);
		graphView.addSeries(caPRatioAvgGraphViewSeries);
		graphView.addSeries(minCaPRatioAvgGraphViewSeries);
		return graphView;
	}

	// 甲長
	public void createShellLengthChart() {
		layout.removeAllViews();
		List<MeasureLog> measureLogList = helper.getMeasureLogs(pid);
		int measureLogListCount = measureLogList.size();
		Resources r = this.getResources(); // 取得手機資源
		float textSizePx = TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 8, r.getDisplayMetrics());
		TextView showReportTxt = new TextView(this);
		showReportTxt.setTextSize(textSizePx);
		if (measureLogListCount == 0)
		{
			showReportTxt.setText("尚無測量紀錄");
			layout.addView(showReportTxt);
		}
		else if (measureLogListCount == 1) {
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("------目前只有一筆測量紀錄------\n")
					.append("日期：").append(helper.getMeasureLogDateString(pid, true)).append("\n")
					.append("甲長：").append(measureLogList.get(0).getShellLength()).append(" 公分");
			showReportTxt.setText(sBuilder.toString());
			layout.addView(showReportTxt);
		}
		else {
			List<GraphViewData> graphViewDataList = helper
					.getShellLengthGraphViewDataList(pid);
			createMeasureLogGraphView(graphViewDataList);
		}
	}

	// 體重
	public void createWeightChart() {
		layout.removeAllViews();
		List<MeasureLog> measureLogList = helper.getMeasureLogs(pid);
		int measureLogListCount = measureLogList.size();
		Resources r = this.getResources(); // 取得手機資源
		float textSizePx = TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 8, r.getDisplayMetrics());
		TextView showReportTxt = new TextView(this);
		showReportTxt.setTextSize(textSizePx);
		if (measureLogListCount == 0)
		{
			showReportTxt.setText("尚無測量紀錄");
			layout.addView(showReportTxt);
		}
		else if (measureLogListCount == 1)
		{
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("------目前只有一筆測量紀錄------\n")
					.append("日期：").append(helper.getMeasureLogDateString(pid, true)).append("\n")
					.append("體重：").append(measureLogList.get(0).getWeight()).append(" 克");
			showReportTxt.setText(sBuilder.toString());
			layout.addView(showReportTxt);
		}
		else {
			List<GraphViewData> graphViewDataList = helper
					.getWeightGraphViewDataList(pid);
			createMeasureLogGraphView(graphViewDataList);
		}
	}

	// 傑克森量表
	public void createJacksonRatioChart() {
		layout.removeAllViews();
		List<MeasureLog> measureLogList = helper.getMeasureLogs(pid);
		int measureLogListCount = measureLogList.size();
		Resources r = this.getResources(); // 取得手機資源
		float textSizePx = TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 8, r.getDisplayMetrics());
		TextView showReportTxt = new TextView(this);
		showReportTxt.setTextSize(textSizePx);
		if (measureLogListCount == 0)
		{
			showReportTxt.setText("尚無測量紀錄");
			layout.addView(showReportTxt);
		}
		else if (measureLogListCount == 1)
		{
			StringBuilder sBuilder = new StringBuilder();
			double shellLength = measureLogList.get(0).getShellLength();
			double weight = measureLogList.get(0).getWeight();
			double jacksonRatio;
			if (shellLength == 0)
			{
				jacksonRatio = 0;
			}
			else {
				jacksonRatio = weight / (shellLength * shellLength * shellLength);
			}
			sBuilder.append("------目前只有一筆測量紀錄------\n")
					.append("日期：").append(helper.getMeasureLogDateString(pid, true)).append("\n")
					.append("甲長：").append(shellLength).append(" 公分\n")
					.append("體重：").append(weight).append(" 克\n")
					.append("傑克森值：").append(jacksonRatio);
			showReportTxt.setText(sBuilder.toString());
			layout.addView(showReportTxt);
		}
		else {
			List<GraphViewData> graphViewDataList = helper
					.getJacksonGraphViewDataList(pid);
			createMeasureLogGraphView(graphViewDataList);
		}
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
}