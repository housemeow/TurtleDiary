package com.td.turtlediary;

import com.td.models.Environment;
import com.td.models.TurtleDiaryDatabaseHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class EnvironmentListActivity extends Activity {
	private ListView environmentListActivityEnvironmentListView;
	private Button environmentListActivityNewEnvironmentButton;
	private TurtleDiaryDatabaseHelper turtleDiaryDatabaseHelper = new TurtleDiaryDatabaseHelper(
			this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_environment_list);
	}

	private Button.OnClickListener clickEnvironmentListActivityNewEnvironmentButton = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(EnvironmentListActivity.this,
					EnvironmentActivity.class);
			intent.putExtra("state", "add");
			startActivity(intent);
		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// set title
		this.setTitle("環境清單");
		// GET list view
		environmentListActivityEnvironmentListView = (ListView) findViewById(R.id.environmentListActivityEnvironmentListView);
		environmentListActivityNewEnvironmentButton = (Button) findViewById(R.id.environmentListActivityNewEnvironmentButton);
		ArrayAdapter<Environment> adapterEnvironments = new ArrayAdapter<Environment>(
				this, android.R.layout.simple_list_item_1,
				turtleDiaryDatabaseHelper.getEnvironments());
		environmentListActivityEnvironmentListView
				.setAdapter(adapterEnvironments);
		// set listener
		environmentListActivityEnvironmentListView
				.setOnItemClickListener(clickItemOnListView);
		environmentListActivityNewEnvironmentButton
				.setOnClickListener(clickEnvironmentListActivityNewEnvironmentButton);
	}

	private ListView.OnItemClickListener clickItemOnListView = new ListView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long arg3) {
			Environment environment = (Environment) parent
					.getItemAtPosition(position);
			Intent intent = new Intent();
			intent.setClass(EnvironmentListActivity.this,
					EnvironmentActivity.class);
			intent.putExtra("state", "view");
			intent.putExtra("environmentEid", environment.getEid());
			startActivity(intent);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.environment_list, menu);
		return true;
	}

}