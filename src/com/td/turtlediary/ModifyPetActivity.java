package com.td.turtlediary;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ModifyPetActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_pet);

		Button modifyButton = (Button)findViewById(R.id.firstAddPetNextButton);
		modifyButton.setVisibility(View.INVISIBLE); //不顯示
		modifyButton.setOnClickListener(getModifyOnClickListener());
		
	}
	
	private OnClickListener getModifyOnClickListener() {
		OnClickListener listener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				EditText petName = (EditText) findViewById(R.id.editTextName);
				petName.setEnabled(false);
				
				EditText petType = (EditText)findViewById(R.id.editTextType);
				petType.setEnabled(false);

				Button modifyButton = (Button) findViewById(R.id.firstAddPetNextButton);
				modifyButton.setVisibility(View.INVISIBLE); //顯示
			}
		};
		return listener;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modify_pet, menu);
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.modify_pet) {
			onClickModify();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void onClickModify() {
		EditText petName = (EditText) findViewById(R.id.editTextName);
		petName.setText("Golden");
		petName.setEnabled(true);
		
		EditText petType = (EditText) findViewById(R.id.editTextType);
		petType.setText ("Indian Star Tortoise");
		petType.setEnabled(true);

		Button modifyButton = (Button)findViewById(R.id.firstAddPetNextButton);
		modifyButton.setVisibility(View.VISIBLE); //顯示

		//modifyItem.setEnabled(false);

	}

}
