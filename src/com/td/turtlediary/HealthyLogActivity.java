package com.td.turtlediary;

import com.td.models.HealthyLog;
import com.td.models.Pet;
import com.td.models.TurtleDiaryDatabaseHelper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HealthyLogActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_healthy_log);
		this.setTitle("健康紀錄");
		Pet pet = (Pet) getIntent().getSerializableExtra("pet");
		TurtleDiaryDatabaseHelper turtleDiaryDatabaseHelper = new TurtleDiaryDatabaseHelper(
				this);

		ListView healthyLogListView = (ListView) findViewById(R.id.healthyLogActivityHealthyLogListView);
		ArrayAdapter<HealthyLog> adapterHealthyLogs = new ArrayAdapter<HealthyLog>(
				this, android.R.layout.simple_list_item_1,
				turtleDiaryDatabaseHelper.getHealthyLogs(pet.getPid()));
		healthyLogListView.setAdapter(adapterHealthyLogs);
	}
}
