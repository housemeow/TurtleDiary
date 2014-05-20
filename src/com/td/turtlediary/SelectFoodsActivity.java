package com.td.turtlediary;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectFoodsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_foods);
		this.setTitle("選擇食物");

		Button feedButton = (Button) findViewById(R.id.selectFoodsFeedButton);
		feedButton.setOnClickListener(getFeedFoodsButtonOnClickListener());
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