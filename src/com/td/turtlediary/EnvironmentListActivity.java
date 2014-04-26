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
	private ListView environmentListView;
	private Button newEnvironmentButton;
	String[] environments = new String[] {"環境1", "環境2"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_environment_list);
		//set title
		this.setTitle("環境清單");
		// GET list view
		environmentListView = (ListView)findViewById(R.id.environmentListView);
		newEnvironmentButton = (Button)findViewById(R.id.newEnvironmentButton);
		//
		ArrayAdapter<String> adapterEnvironments = new ArrayAdapter<String>
		(this, android.R.layout.simple_list_item_1, environments);
		environmentListView.setAdapter(adapterEnvironments);
		// set listener
		environmentListView.setOnItemClickListener(clickItemOnListView);
		newEnvironmentButton.setOnClickListener(clickNewEnvironmentButton);
	}
	
	private Button.OnClickListener clickNewEnvironmentButton = new Button.OnClickListener()
    {
		@Override
		public void onClick(View v) 
		{
			Intent intent = new Intent();
			intent.setClass(EnvironmentListActivity.this, EnvironmentModifyActivity.class);
			intent.putExtra("title", "環境新增");
			intent.putExtra("buttonName", "新增");
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