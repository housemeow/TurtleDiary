package com.td.turtlediary;

import com.td.models.FeedLog;
import com.td.models.TurtleDiaryDatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SelectFoodsActivity extends Activity {

	private TurtleDiaryDatabaseHelper turtleDiaryDatabaseHelper = new TurtleDiaryDatabaseHelper(this);
	
	private int pid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_foods);
		this.setTitle("選擇食物");
		Intent intent = getIntent();
		intent.getIntExtra("pid", -1);
		Button feedButton = (Button) findViewById(R.id.selectFoodsFeedButton);
		feedButton.setOnClickListener(getFeedFoodsButtonOnClickListener());
		FeedLog lastFeedLog = turtleDiaryDatabaseHelper.getLastFeedLog(pid);
		if (lastFeedLog != null)
		{
			// double weight = turtleDiaryDatabaseHelper.getWeightFromSameFeedLog(lastFeedLog.getFlid());
			// Toast.makeText(this, "上次餵食記錄日期：\n" + lastFeedLog.getTimeStamp() + "\n共餵食" + weight + "g", Toast.LENGTH_LONG).show();
			Toast.makeText(this, "lastFeedLog not null", Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(this, "lastFeedLog null", Toast.LENGTH_LONG).show();
		}
	}

	private OnClickListener getFeedFoodsButtonOnClickListener() {
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Add Feed food API
				finish();
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