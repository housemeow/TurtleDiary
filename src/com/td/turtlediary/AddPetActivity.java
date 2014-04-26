package com.td.turtlediary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AddPetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_pet);
		Button previousButton = (Button)findViewById(R.id.firstAddPetPreviousButton);
		previousButton.setOnClickListener(getPreviousButtonOnClickListener());
		//Intent intent = getIntent();
		

		Button nextButton = (Button)findViewById(R.id.firstAddPetNextButton);
		nextButton.setOnClickListener(getNextButtonOnClickListener());
	}
	

	private OnClickListener getNextButtonOnClickListener() {
		OnClickListener listener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddPetActivity.this, HomePageActivity.class);
				
				intent.putExtra("title", "環境新增");
				intent.putExtra("buttonName", "新增");
				startActivity(intent);
			}
		};
		return listener;
	}

	private OnClickListener getPreviousButtonOnClickListener() {
		OnClickListener listener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddPetActivity.this, EnvironmentSettingActivity.class);
				
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
        getMenuInflater().inflate(R.menu.environment_information, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        // Handle presses on the action bar items
        switch (item.getItemId()) 
        {
            case R.id.modifyActionButton:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
	
	
	
	
	
	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_add_pet);
//
//		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.add_pet, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//
//	/**
//	 * A placeholder fragment containing a simple view.
//	 */
//	public static class PlaceholderFragment extends Fragment {
//		private View rootView;
//
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			rootView = inflater.inflate(R.layout.fragment_add_pet,
//					container, false);
//			
//
//
//			Button previousButton = (Button)rootView.findViewById(R.id.firstAddPetPreviousButton);
//			previousButton.setOnClickListener(getPreviousButtonOnClickListener());
//			//Intent intent = getIntent();
//			
//
//			Button nextButton = (Button)rootView.findViewById(R.id.firstAddPetNextButton);
//			nextButton.setOnClickListener(getNextButtonOnClickListener());
//			return rootView;
//		}
//		
//
//		private OnClickListener getNextButtonOnClickListener() {
//			OnClickListener listener = new OnClickListener(){
//
//				@Override
//				public void onClick(View v) {
//					Intent intent = new Intent(getActivity(), HomePageActivity.class);
//					
//					intent.putExtra("title", "環境新增");
//					intent.putExtra("buttonName", "新增");
//					startActivity(intent);
//				}
//			};
//			return listener;
//		}
//
//		private OnClickListener getPreviousButtonOnClickListener() {
//			OnClickListener listener = new OnClickListener(){
//
//				@Override
//				public void onClick(View v) {
//					Intent intent = new Intent(getActivity(), EnvironmentSettingActivity.class);
//					
//					intent.putExtra("title", "環境新增");
//					intent.putExtra("buttonName", "新增");
//					startActivity(intent);
//				}
//			};
//			return listener;
//		}
//	}

}
