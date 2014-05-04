package com.td.turtlediary;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EnvironmentSettingActivity extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_environment_setting);
		// set title
        this.setTitle("環境設定");
        
        Button environmentSettingActivityNextButton = (Button)findViewById(R.id.environmentSettingActivityNextButton);
        environmentSettingActivityNextButton.setOnClickListener(getEnvironmentSettingActivityNextButtonOnClickListener());
	}

	private OnClickListener getEnvironmentSettingActivityNextButtonOnClickListener() {
		OnClickListener listener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(EnvironmentSettingActivity.this, AddPetActivity.class);
				intent.putExtra("title", "環境新增");
				intent.putExtra("buttonName", "新增");
				startActivity(intent);
			}
		};
		return listener;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.environment_setting, menu);
		return true;
	}

}