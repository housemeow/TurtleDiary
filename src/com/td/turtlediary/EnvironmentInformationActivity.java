package com.td.turtlediary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class EnvironmentInformationActivity extends Activity 
{
	private TextView environmentInformationActivityEnvironmentNameTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment_information);
        // set title
        this.setTitle("環境資訊");
        // find
        environmentInformationActivityEnvironmentNameTextView = (TextView)findViewById(R.id.environmentInformationActivityEnvironmentNameTextView);
        //
        Intent intent = getIntent();
        String environmentName = intent.getStringExtra("environmentName");
        environmentInformationActivityEnvironmentNameTextView.setText(environmentName);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.environment_information, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        // Handle presses on the action bar items
        switch (item.getItemId()) 
        {
            case R.id.environmentInformationActivityModifyActionButton:
            	Intent intent = new Intent();
    			intent.setClass(EnvironmentInformationActivity.this, EnvironmentModifyActivity.class);
    			intent.putExtra("title", "環境修改");
    			intent.putExtra("buttonName", "修改");
    			startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
}