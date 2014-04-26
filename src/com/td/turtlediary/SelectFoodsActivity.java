package com.td.turtlediary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectFoodsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_select_foods);
		this.setTitle("選擇食物");

		Button feedButton = (Button)findViewById(R.id.selectFoodsFeedButton);
		feedButton.setOnClickListener(getFeedFoodsButtonOnClickListener());
	}

	private OnClickListener getFeedFoodsButtonOnClickListener() {
		OnClickListener listener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SelectFoodsActivity.this, AllocateFoodActivity.class);
				
				intent.putExtra("title", "環境新增");
				intent.putExtra("buttonName", "新增");
				startActivity(intent);
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}