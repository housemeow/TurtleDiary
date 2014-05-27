package com.td.turtlediary;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.td.models.FeedLog;
import com.td.models.FeedLogContainFood;
import com.td.models.Food;
import com.td.models.Nutrition;
import com.td.models.TurtleDiaryDatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SelectFoodsActivity extends Activity {

	private TurtleDiaryDatabaseHelper helper = new TurtleDiaryDatabaseHelper(
			this);
	private int pid;
	private TableLayout selectedFoodsTableLayout;
	private static List<FeedLogContainFood> feedLogContainFoods = new ArrayList<FeedLogContainFood>();
	private AutoCompleteTextView foodAutoCompleteTextView;
	private EditText weightEditText;

	private TextView dryWeightEditText;
	private TextView proteinEditText;
	private TextView fabricEditText;
	private TextView fatEditText;
	private TextView caEditText;
	private TextView pEditText;
	private TextView caPRatioEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_foods);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		this.setTitle("選擇食物");
		selectedFoodsTableLayout = (TableLayout) findViewById(R.id.selectFoodActivityFoodsTableLayout);
		foodAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.selectFoodActivityFoodsAutoCompleteTextView);
		weightEditText = (EditText) findViewById(R.id.selectFoodWeightTextEdit);

		dryWeightEditText = (TextView) findViewById(R.id.selectFoodsActivityDryWeightEditText);
		proteinEditText = (TextView) findViewById(R.id.selectFoodActivityProteinEditText);
		fabricEditText = (TextView) findViewById(R.id.selectFoodsActivityFabricEditText);
		fatEditText = (TextView) findViewById(R.id.selectFoodsActivityFatEditText);
		caEditText = (TextView) findViewById(R.id.selectFoodsActivityCaEditText);
		pEditText = (TextView) findViewById(R.id.selectFoodsActivityPEditText);
		caPRatioEditText = (TextView) findViewById(R.id.selectFoodsActivityCaPRatioEditText);

		List<Food> foods = helper.getFoods();
		ArrayAdapter<Food> adapter = new ArrayAdapter<Food>(this,
				android.R.layout.simple_dropdown_item_1line, foods);

		foodAutoCompleteTextView.setAdapter(adapter);

		Intent intent = getIntent();
		pid = intent.getIntExtra("pid", -1);
		Button feedButton = (Button) findViewById(R.id.selectFoodsFeedButton);
		feedButton.setOnClickListener(getFeedFoodsButtonOnClickListener());

		Button nextFoodButton = (Button) findViewById(R.id.selectFoodActivityNextFoodButton);
		nextFoodButton.setOnClickListener(getNextButtonOnClickListener());

		FeedLog lastFeedLog = helper.getLastFeedLog(pid);
		if (lastFeedLog != null) {
			// double weight =
			// turtleDiaryDatabaseHelper.getWeightFromSameFeedLog(lastFeedLog.getFlid());
			// Toast.makeText(this, "上次餵食記錄日期：\n" + lastFeedLog.getTimeStamp() +
			// "\n共餵食" + weight + "g", Toast.LENGTH_LONG).show();
			double weight = helper.getFoodsWeight(lastFeedLog.getFlid());
			Toast.makeText(this, "上次餵食總重量為" + weight, Toast.LENGTH_LONG).show();
		} else {
			// Toast.makeText(this, "lastFeedLog null",
			// Toast.LENGTH_LONG).show();
		}

		if (savedInstanceState != null) {
			dryWeightEditText
					.setText(savedInstanceState.getString("dryWeight"));
			proteinEditText.setText(savedInstanceState.getString("protein"));
			fabricEditText.setText(savedInstanceState.getString("fabric"));
			fatEditText.setText(savedInstanceState.getString("fat"));
			caEditText.setText(savedInstanceState.getString("ca"));
			pEditText.setText(savedInstanceState.getString("p"));
			caPRatioEditText.setText(savedInstanceState.getString("caPRatio"));
			for (FeedLogContainFood flcf : feedLogContainFoods) {
				Food food = helper.getFood(flcf.getFid());
				TableRow row = getRaw(food, flcf.getWeight());
				selectedFoodsTableLayout.addView(row);

			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("dryWeight", dryWeightEditText.getText().toString());
		outState.putString("protein", proteinEditText.getText().toString());
		outState.putString("fabric", fabricEditText.getText().toString());
		outState.putString("fat", fatEditText.getText().toString());
		outState.putString("ca", caEditText.getText().toString());
		outState.putString("p", pEditText.getText().toString());
		outState.putString("caPRatio", caPRatioEditText.getText().toString());
	}

	// 加入食物清單
	private OnClickListener getNextButtonOnClickListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				Food food = helper.getFood(foodAutoCompleteTextView.getText()
						.toString());
				if (food == null) {
					Toast.makeText(SelectFoodsActivity.this, "請輸入完整食物名稱",
							Toast.LENGTH_LONG).show();
					return;
				}

				double weight = 0;
				try {
					weight = Double.parseDouble(weightEditText.getText()
							.toString());
					if (weight == 0)
					{
						Toast.makeText(SelectFoodsActivity.this, "重量欄位不可為0",
								Toast.LENGTH_LONG).show();
						weightEditText.setText("");
						return;
					}
				} catch (NumberFormatException e) {
					Toast.makeText(SelectFoodsActivity.this, "重量欄位請輸入數字",
							Toast.LENGTH_LONG).show();

					e.printStackTrace();
					return;
				}

				foodAutoCompleteTextView.setText("");
				weightEditText.setText("");

				FeedLogContainFood feedLogContainFood = new FeedLogContainFood();
				feedLogContainFood.setFid(food.getFid());
				feedLogContainFood.setWeight(weight);
				feedLogContainFoods.add(feedLogContainFood);

				TableRow row = getRaw(food, weight);
				selectedFoodsTableLayout.addView(row);

				Nutrition nutrition = helper
						.getFeedLogContainFoodsNutrition(feedLogContainFoods);
				NumberFormat nf = NumberFormat.getInstance();
				nf.setMaximumFractionDigits(5);
				double dryWeight = Double.parseDouble(nf.format(nutrition.getDryWeight()));
				double protein = Double.parseDouble(nf.format(nutrition.getProteinPercentage()));
				double fabric =  Double.parseDouble(nf.format(nutrition.getFabricPercentage()));
				double fat =  Double.parseDouble(nf.format(nutrition.getFatPercentage()));
				double ca =  Double.parseDouble(nf.format(nutrition.getCaPercentage()));
				double p =  Double.parseDouble(nf.format(nutrition.getPPercentage()));
				double caPRatio =  Double.parseDouble(nf.format(nutrition.getCaPRatio()));
				dryWeightEditText.setText(dryWeight + "");
				proteinEditText.setText(protein + "");
				fabricEditText.setText(fabric + "");
				fatEditText.setText(fat + "");
				caEditText.setText(ca + "");
				pEditText.setText(p + "");
				caPRatioEditText.setText(caPRatio + "");

			}

		};
	}

	private TableRow getRaw(Food food, double weight) {
		TableRow row = new TableRow(SelectFoodsActivity.this);
		TextView textView = new TextView(SelectFoodsActivity.this);
		textView.setText(food.getName() + weight + "克");
		float size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10,
				getResources().getDisplayMetrics());
		textView.setTextSize(size);
		row.addView(textView);
		return row;
	}

	// 餵食
	private OnClickListener getFeedFoodsButtonOnClickListener() {
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				int foodListCount = feedLogContainFoods.size();
				if(foodListCount > 0)
				{
					FeedLog feedLog = new FeedLog();
					feedLog.setPid(pid);
					feedLog.setTimeStamp(new Date());
					helper.addFeedLog(feedLog);
					for (FeedLogContainFood flcf : feedLogContainFoods) {
						flcf.setFlid(feedLog.getFlid());
						helper.addFeedLogContainFood(flcf);
					}
					feedLogContainFoods = new ArrayList<FeedLogContainFood>();
					finish();
				}
				else
				{
					FeedLogContainFood feedLogContainFood = getFeedLogContainFood();
					if (feedLogContainFood != null) {
						Toast.makeText(SelectFoodsActivity.this, "請按加入食物清單顯示營養比例",
								Toast.LENGTH_LONG).show();
					}
				}
			}

			private FeedLogContainFood getFeedLogContainFood() {
				Food food = helper.getFood(foodAutoCompleteTextView.getText()
						.toString());
				if (food == null) {
					Toast.makeText(SelectFoodsActivity.this, "請輸入完整食物名稱",
							Toast.LENGTH_LONG).show();
					return null;
				}

				double weight = 0;
				try {
					weight = Double.parseDouble(weightEditText.getText()
							.toString());
				} catch (NumberFormatException e) {
					Toast.makeText(SelectFoodsActivity.this, "重量欄位請輸入數字",
							Toast.LENGTH_LONG).show();

					e.printStackTrace();
					return null;
				}
				FeedLogContainFood feedLogContainFood = new FeedLogContainFood();
				feedLogContainFood.setFid(food.getFid());
				feedLogContainFood.setWeight(weight);
				return feedLogContainFood;
			}
		};
		return listener;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_foods, menu);
		return true;
	}
}