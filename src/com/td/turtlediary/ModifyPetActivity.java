package com.td.turtlediary;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.os.Build;

public class ModifyPetActivity extends ActionBarActivity {
	private MenuItem modifyItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_pet);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modify_pet, menu);
		modifyItem = menu.getItem(0);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		private View rootView;
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_modify_pet,
					container, false);
			

			Button modifyButton = (Button)rootView.findViewById(R.id.firstAddPetNextButton);
			modifyButton.setVisibility(View.INVISIBLE); //顯示
			modifyButton.setOnClickListener(getModifyOnClickListener());
			
			return rootView;
		}

		private OnClickListener getModifyOnClickListener() {
			OnClickListener listener = new OnClickListener(){

				@Override
				public void onClick(View v) {
					EditText petName = (EditText) rootView.findViewById(R.id.editTextName);
					petName.setEnabled(false);
					
					EditText petType = (EditText)  rootView.findViewById(R.id.editTextType);
					petType.setEnabled(false);

					Button modifyButton = (Button) rootView.findViewById(R.id.firstAddPetNextButton);
					modifyButton.setVisibility(View.INVISIBLE); //顯示
				}
			};
			return listener;
		}
	}

}
