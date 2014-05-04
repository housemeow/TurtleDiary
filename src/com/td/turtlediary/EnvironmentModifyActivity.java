package com.td.turtlediary;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class EnvironmentModifyActivity extends Activity 
{
	private Button environmentModifyActivityCheckButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_environment_modify);
		//get 
		environmentModifyActivityCheckButton = (Button)findViewById(R.id.environmentModifyActivityCheckButton);
		// get intent
		Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String buttonName = intent.getStringExtra("buttonName");
        // set title
        this.setTitle(title);
        // set button name
        environmentModifyActivityCheckButton.setText(buttonName);
        // set listener
        environmentModifyActivityCheckButton.setOnClickListener(clickEnvironmentModifyActivityCheckButton);
	}
	
	private Button.OnClickListener clickEnvironmentModifyActivityCheckButton = new Button.OnClickListener()
    {
		@Override
		public void onClick(View arg0) 
		{
			Intent intent = new Intent();
			intent.setClass(EnvironmentModifyActivity.this, EnvironmentListActivity.class);
			startActivity(intent);
		}
    };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.environment_modify, menu);
		return true;
	}

}