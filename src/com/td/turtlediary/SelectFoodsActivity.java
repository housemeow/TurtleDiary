package com.td.turtlediary;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class SelectFoodsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_foods);
		this.setTitle("選擇食物");

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_select_foods,
					container, false);
			
			Button feedButton = (Button)rootView.findViewById(R.id.selectFoodsFeedButton);
			feedButton.setOnClickListener(getFeedFoodsButtonOnClickListener());
			return rootView;
		}

		private OnClickListener getFeedFoodsButtonOnClickListener() {
			OnClickListener listener = new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(), AllocateFoodActivity.class);
					
					intent.putExtra("title", "環境新增");
					intent.putExtra("buttonName", "新增");
					startActivity(intent);
				}
			};
			return listener;
		}
	}

}