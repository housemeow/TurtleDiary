package com.td.turtlediary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnvironmentActivity extends Activity 
{
	private String state = new String("");
	private String SET = new String("set");
	private String ADD = new String("add");
	private String VIEW = new String("view");
	private String EDIT = new String("edit");
	private Button environmentActivityNextButton, environmentActivityAddButton, environmentActivityRecoverButton, environmentActivityEditButton;
	private EditText environmentActivityEnvironmentNameEditText, environmentActivityLengthEditText, environmentActivityWidthEditText, environmentActivityHeightEditText, environmentActivityEnvironmentHotPointEditText, environmentActivityEnvironmentLowPointEditText, environmentActivityMaxHumidityEditText, environmentActivityMinHumidityEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_environment);
		// get edit text id
				environmentActivityEnvironmentNameEditText = (EditText)findViewById(R.id.environmentActivityEnvironmentNameEditText);
				environmentActivityLengthEditText = (EditText)findViewById(R.id.environmentActivityLengthEditText);
				environmentActivityWidthEditText = (EditText)findViewById(R.id.environmentActivityWidthEditText);
				environmentActivityHeightEditText = (EditText)findViewById(R.id.environmentActivityHeightEditText);
				environmentActivityEnvironmentHotPointEditText = (EditText)findViewById(R.id.environmentActivityEnvironmentHotPointEditText);
				environmentActivityEnvironmentLowPointEditText = (EditText)findViewById(R.id.environmentActivityEnvironmentLowPointEditText);
				environmentActivityMaxHumidityEditText = (EditText)findViewById(R.id.environmentActivityMaxHumidityEditText);
				environmentActivityMinHumidityEditText = (EditText)findViewById(R.id.environmentActivityMinHumidityEditText);
				// get button component id
				environmentActivityNextButton = (Button)findViewById(R.id.environmentActivityNextButton);
				environmentActivityAddButton = (Button)findViewById(R.id.environmentActivityAddButton);
				environmentActivityRecoverButton = (Button)findViewById(R.id.environmentActivityRecoverButton);
				environmentActivityEditButton = (Button)findViewById(R.id.environmentActivityEditButton);
				// set listener
				environmentActivityNextButton.setOnClickListener(clickEnvironmentActivityNextButton);
				environmentActivityAddButton.setOnClickListener(clickEnvironmentActivityAddButton);
				environmentActivityRecoverButton.setOnClickListener(clickEnvironmentActivityRecoverButton);
				environmentActivityEditButton.setOnClickListener(clickEnvironmentActivityEditButton);
				
				// get intent
				Intent intent = getIntent();
				if (intent.hasExtra("state"))
				{
					state = intent.getStringExtra("state");
			        if (state.equals(VIEW))
			        {
			        	String environmentName = intent.getStringExtra("environmentName");
			        	environmentActivityEnvironmentNameEditText.setText(environmentName);
			        }
				}
				else 
				{
					state = SET;
				}
		        RefreshView();
	}
	
	private void RefreshView()
	{
		invalidateOptionsMenu(); // refresh menu
		if (state.equals(ADD))
        {
        	this.setTitle("·s¼WÀô¹Ò");
			environmentActivityAddButton.setVisibility(View.VISIBLE);
			environmentActivityNextButton.setVisibility(View.INVISIBLE);
			environmentActivityRecoverButton.setVisibility(View.INVISIBLE);
			environmentActivityEditButton.setVisibility(View.INVISIBLE);
			SetEditTextEnable(true);
        }
        else if (state.equals(VIEW)) 
        {
        	this.setTitle("Àô¹Ò¸ê°T");
        	environmentActivityAddButton.setVisibility(View.INVISIBLE);
			environmentActivityNextButton.setVisibility(View.INVISIBLE);
			environmentActivityRecoverButton.setVisibility(View.INVISIBLE);
			environmentActivityEditButton.setVisibility(View.INVISIBLE);
			SetEditTextEnable(false);
		}
        else if (state.equals(EDIT)) 
        {
        	this.setTitle("­×§ïÀô¹Ò");
        	environmentActivityAddButton.setVisibility(View.INVISIBLE);
			environmentActivityNextButton.setVisibility(View.INVISIBLE);
			environmentActivityRecoverButton.setVisibility(View.VISIBLE);
			environmentActivityEditButton.setVisibility(View.VISIBLE);
			SetEditTextEnable(true);
		}
        else if(state.equals(SET))
        {
        	this.setTitle("³]©wÀô¹Ò");
        	environmentActivityAddButton.setVisibility(View.INVISIBLE);
			environmentActivityNextButton.setVisibility(View.VISIBLE);
			environmentActivityRecoverButton.setVisibility(View.INVISIBLE);
			environmentActivityEditButton.setVisibility(View.INVISIBLE);
			SetEditTextEnable(true);
		}
	}
	
	private void SetEditTextEnable(Boolean isEnable)
	{
		environmentActivityEnvironmentNameEditText.setEnabled(isEnable);
		environmentActivityLengthEditText.setEnabled(isEnable);
		environmentActivityWidthEditText.setEnabled(isEnable);
		environmentActivityHeightEditText.setEnabled(isEnable);
		environmentActivityEnvironmentHotPointEditText.setEnabled(isEnable);
		environmentActivityEnvironmentLowPointEditText.setEnabled(isEnable);
		environmentActivityMaxHumidityEditText.setEnabled(isEnable);
		environmentActivityMinHumidityEditText.setEnabled(isEnable);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.environment, menu);
		MenuItem menuItem = menu.findItem(R.id.environmentActivityEditActionButton);
		if (state.equals(VIEW))
		{
			menuItem.setVisible(true);
		}
		else 
		{
			menuItem.setVisible(false);
		}
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        // Handle presses on the action bar items
        switch (item.getItemId()) 
        {
            case R.id.environmentActivityEditActionButton:
            	//Intent intent = new Intent();
    			//intent.setClass(EnvironmentInformationActivity.this, EnvironmentModifyActivity.class);
    			//intent.putExtra("title", "Àô¹Ò­×§ï");
    			//intent.putExtra("buttonName", "­×§ï");
    			//startActivity(intent);
            	state = EDIT;
            	RefreshView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
	// next
	private Button.OnClickListener clickEnvironmentActivityNextButton = new Button.OnClickListener()
    {
		@Override
		public void onClick(View view) 
		{
			//Intent intent = new Intent();
			//intent.setClass(EnvironmentSettingActivity.this, AddPetActivity.class);
			//startActivity(intent);
			Intent intent = new Intent();
			intent.setClass(EnvironmentActivity.this, EnvironmentListActivity.class);
			startActivity(intent);
		}
    };
    
    // add
    private Button.OnClickListener clickEnvironmentActivityAddButton = new Button.OnClickListener()
    {
		@Override
		public void onClick(View view) 
		{
			Intent intent = new Intent();
			intent.setClass(EnvironmentActivity.this, EnvironmentListActivity.class);
			startActivity(intent);
		}
    };
    
    // recover
    private Button.OnClickListener clickEnvironmentActivityRecoverButton = new Button.OnClickListener()
    {
		@Override
		public void onClick(View view) 
		{
			// call GetEnvironment() API
		}
    };
    
    // edit
    private Button.OnClickListener clickEnvironmentActivityEditButton = new Button.OnClickListener()
    {
		@Override
		public void onClick(View view) 
		{
			Intent intent = new Intent();
			intent.setClass(EnvironmentActivity.this, EnvironmentListActivity.class);
			startActivity(intent);
		}
    };

}
