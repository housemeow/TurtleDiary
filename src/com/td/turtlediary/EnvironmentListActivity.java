package com.td.turtlediary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class EnvironmentListActivity extends Activity 
{
	private ListView environmentListActivityEnvironmentListView;
	private Button environmentListActivityNewEnvironmentButton;
	String[] environments = new String[] {"環境1", "環境2"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_environment_list);
		//set title
		this.setTitle("環境清單");
		// GET list view
		environmentListActivityEnvironmentListView = (ListView)findViewById(R.id.environmentListActivityEnvironmentListView);
		environmentListActivityNewEnvironmentButton = (Button)findViewById(R.id.environmentListActivityNewEnvironmentButton);
		//
		ArrayAdapter<String> adapterEnvironments = new ArrayAdapter<String>
		(this, android.R.layout.simple_list_item_1, environments);
		environmentListActivityEnvironmentListView.setAdapter(adapterEnvironments);
		// set listener
		environmentListActivityEnvironmentListView.setOnItemClickListener(clickItemOnListView);
		environmentListActivityNewEnvironmentButton.setOnClickListener(clickEnvironmentListActivityNewEnvironmentButton);
	}
	
	private Button.OnClickListener clickEnvironmentListActivityNewEnvironmentButton = new Button.OnClickListener()
    {
		@Override
		public void onClick(View v) 
		{
			Intent intent = new Intent();
			intent.setClass(EnvironmentListActivity.this, EnvironmentModifyActivity.class);
			intent.putExtra("title", "環境新增");
			intent.putExtra("buttonName", R.string.addEnvironment);
			startActivity(intent);
		}
    };

	private ListView.OnItemClickListener clickItemOnListView = new ListView.OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position, long arg3) 
		{
			Intent intent = new Intent();
			intent.setClass(EnvironmentListActivity.this, EnvironmentInformationActivity.class);
			intent.putExtra("environmentName", parent.getItemAtPosition(position).toString());
			startActivity(intent);
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.environment_list, menu);
		return true;
	}

}