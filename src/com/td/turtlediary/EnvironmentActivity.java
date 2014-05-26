package com.td.turtlediary;

import com.td.models.Environment;
import com.td.models.TurtleDiaryDatabaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnvironmentActivity extends Activity {
	private static EnvironmentActivity activity;

	private String state = new String("");
	private String SET = new String("set");
	private String ADD = new String("add");
	private String VIEW = new String("view");
	private String EDIT = new String("edit");
	private int eid;
	private TurtleDiaryDatabaseHelper turtleDiaryDatabaseHelper;
	private Button environmentActivityNextButton, environmentActivityAddButton,
			environmentActivityRecoverButton, environmentActivityEditButton;
	private EditText environmentActivityEnvironmentNameEditText,
			environmentActivityLengthEditText,
			environmentActivityWidthEditText,
			environmentActivityHeightEditText,
			environmentActivityHotPointEditText,
			environmentActivityLowPointEditText,
			environmentActivityMaxHumidityEditText,
			environmentActivityMinHumidityEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = this;
		turtleDiaryDatabaseHelper = new TurtleDiaryDatabaseHelper(this);

		setContentView(R.layout.activity_environment);
		// get edit text id
		environmentActivityEnvironmentNameEditText = (EditText) findViewById(R.id.environmentActivityEnvironmentNameEditText);
		environmentActivityLengthEditText = (EditText) findViewById(R.id.environmentActivityLengthEditText);
		environmentActivityWidthEditText = (EditText) findViewById(R.id.environmentActivityWidthEditText);
		environmentActivityHeightEditText = (EditText) findViewById(R.id.environmentActivityHeightEditText);
		environmentActivityHotPointEditText = (EditText) findViewById(R.id.environmentActivityHotPointEditText);
		environmentActivityLowPointEditText = (EditText) findViewById(R.id.environmentActivityLowPointEditText);
		environmentActivityMaxHumidityEditText = (EditText) findViewById(R.id.environmentActivityMaxHumidityEditText);
		environmentActivityMinHumidityEditText = (EditText) findViewById(R.id.environmentActivityMinHumidityEditText);
		// get button component id
		environmentActivityNextButton = (Button) findViewById(R.id.environmentActivityNextButton);
		environmentActivityAddButton = (Button) findViewById(R.id.selectFoodsActivityFatEditText);
		environmentActivityRecoverButton = (Button) findViewById(R.id.environmentActivityRestoreButton);
		environmentActivityEditButton = (Button) findViewById(R.id.selectFoodsActivityCaEditText);
		// set listener
		environmentActivityNextButton
				.setOnClickListener(clickEnvironmentActivityNextButton);
		environmentActivityAddButton
				.setOnClickListener(clickEnvironmentActivityAddButton);
		environmentActivityRecoverButton
				.setOnClickListener(clickEnvironmentActivityRecoverButton);
		environmentActivityEditButton
				.setOnClickListener(clickEnvironmentActivityEditButton);
		// get intent
		eid = -1;
		Intent intent = getIntent();
		if (intent.hasExtra("state")) {
			state = intent.getStringExtra("state");
			if (state.equals(VIEW)) {
				eid = intent.getIntExtra("environmentEid", -1);
			}
		} else if (turtleDiaryDatabaseHelper.getEnvironmentsCount() == 0) {
			// call GetEenvironmentsCount() API
			state = SET;
		} else {
			intent = new Intent();
			intent.setClass(EnvironmentActivity.this, HomePageActivity.class);
			startActivity(intent);
			finish();
		}
		RefreshView();
	}

	private void RefreshView() {
		invalidateOptionsMenu(); // refresh menu
		if (state.equals(ADD)) {
			this.setTitle("新增環境");
			environmentActivityAddButton.setVisibility(View.VISIBLE);
			environmentActivityNextButton.setVisibility(View.INVISIBLE);
			environmentActivityRecoverButton.setVisibility(View.INVISIBLE);
			environmentActivityEditButton.setVisibility(View.INVISIBLE);
			SetEditTextDefaultValue();
			SetEditTextEnable(true);
		} else if (state.equals(VIEW)) {
			this.setTitle("環境資訊");
			environmentActivityAddButton.setVisibility(View.INVISIBLE);
			environmentActivityNextButton.setVisibility(View.INVISIBLE);
			environmentActivityRecoverButton.setVisibility(View.INVISIBLE);
			environmentActivityEditButton.setVisibility(View.INVISIBLE);
			SetEditTextContentFromDB();
			SetEditTextEnable(false);
		} else if (state.equals(EDIT)) {
			this.setTitle("修改環境");
			environmentActivityAddButton.setVisibility(View.INVISIBLE);
			environmentActivityNextButton.setVisibility(View.INVISIBLE);
			environmentActivityRecoverButton.setVisibility(View.VISIBLE);
			environmentActivityEditButton.setVisibility(View.VISIBLE);
			SetEditTextContentFromDB();
			SetEditTextEnable(true);
		} else if (state.equals(SET)) {
			this.setTitle("設定環境");
			environmentActivityAddButton.setVisibility(View.INVISIBLE);
			environmentActivityNextButton.setVisibility(View.VISIBLE);
			environmentActivityRecoverButton.setVisibility(View.INVISIBLE);
			environmentActivityEditButton.setVisibility(View.INVISIBLE);
			SetEditTextDefaultValue();
			SetEditTextEnable(true);
		}
	}

	private void SetEditTextEnable(Boolean isEnable) {
		environmentActivityEnvironmentNameEditText.setEnabled(isEnable);
		environmentActivityLengthEditText.setEnabled(isEnable);
		environmentActivityWidthEditText.setEnabled(isEnable);
		environmentActivityHeightEditText.setEnabled(isEnable);
		environmentActivityHotPointEditText.setEnabled(isEnable);
		environmentActivityLowPointEditText.setEnabled(isEnable);
		environmentActivityMaxHumidityEditText.setEnabled(isEnable);
		environmentActivityMinHumidityEditText.setEnabled(isEnable);
	}

	private void SetEditTextDefaultValue() {
		environmentActivityEnvironmentNameEditText.setText("");
		environmentActivityLengthEditText.setText("60");
		environmentActivityWidthEditText.setText("45");
		environmentActivityHeightEditText.setText("45");
		environmentActivityHotPointEditText.setText("32");
		environmentActivityLowPointEditText.setText("28");
		environmentActivityMaxHumidityEditText.setText("80");
		environmentActivityMinHumidityEditText.setText("60");
	}

	private void SetEditTextContentFromDB() {
		if (eid > 0) {
			Environment environment = turtleDiaryDatabaseHelper
					.getEnvironment(eid);
			environmentActivityEnvironmentNameEditText.setText(environment
					.getName());
			environmentActivityLengthEditText.setText(environment.getLength()
					+ "");
			environmentActivityWidthEditText.setText(environment.getWidth()
					+ "");
			environmentActivityHeightEditText.setText(environment.getHeight()
					+ "");
			environmentActivityHotPointEditText.setText(environment
					.getHotPoint() + "");
			environmentActivityLowPointEditText.setText(environment
					.getLowPoint() + "");
			environmentActivityMaxHumidityEditText.setText(environment
					.getMaxHumidity() + "");
			environmentActivityMinHumidityEditText.setText(environment
					.getMinHumidity() + "");
		}
	}

	public Environment GetEnvironmentFromEditText() {
		Environment environment = new Environment();
		environment.setEid(eid);
		environment.setName(environmentActivityEnvironmentNameEditText
				.getText().toString());
		if (environmentActivityLengthEditText.getText().toString().equals("")) {
			environment.setLength(0);
		} else {
			environment.setLength(Integer
					.parseInt(environmentActivityLengthEditText.getText()
							.toString()));
		}
		if (environmentActivityWidthEditText.getText().toString().equals("")) {
			environment.setWidth(0);
		} else {
			environment.setWidth(Integer
					.parseInt(environmentActivityWidthEditText.getText()
							.toString()));
		}

		if (environmentActivityHeightEditText.getText().toString().equals("")) {
			environment.setHeight(0);
		} else {
			environment.setHeight(Integer
					.parseInt(environmentActivityHeightEditText.getText()
							.toString()));
		}

		if (environmentActivityHotPointEditText.getText().toString().equals("")) {
			environment.setHotPoint(0);
		} else {
			environment.setHotPoint(Double
					.parseDouble(environmentActivityHotPointEditText.getText()
							.toString()));
		}

		if (environmentActivityLowPointEditText.getText().toString().equals("")) {
			environment.setLowPoint(0);
		} else {
			environment.setLowPoint(Double
					.parseDouble(environmentActivityLowPointEditText.getText()
							.toString()));
		}

		if (environmentActivityMaxHumidityEditText.getText().toString()
				.equals("")) {
			environment.setMaxHumidity(0);
		} else {
			environment.setMaxHumidity(Integer
					.parseInt(environmentActivityMaxHumidityEditText.getText()
							.toString()));
		}

		if (environmentActivityMinHumidityEditText.getText().toString()
				.equals("")) {
			environment.setMinHumidity(0);
		} else {
			environment.setMinHumidity(Integer
					.parseInt(environmentActivityMinHumidityEditText.getText()
							.toString()));
		}
		return environment;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.environment, menu);
		MenuItem menuItem = menu
				.findItem(R.id.selectFoodsActivityCaPRatioEditText);
		if (state.equals(VIEW)) {
			menuItem.setVisible(true);
		} else {
			menuItem.setVisible(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.selectFoodsActivityCaPRatioEditText:
			state = EDIT;
			RefreshView();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// next
	private Button.OnClickListener clickEnvironmentActivityNextButton = new Button.OnClickListener() {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent();
			intent.putExtra("environment", GetEnvironmentFromEditText());
			intent.setClass(EnvironmentActivity.this, PetActivity.class);
			startActivity(intent);
		}
	};

	// add
	private Button.OnClickListener clickEnvironmentActivityAddButton = new Button.OnClickListener() {
		@Override
		public void onClick(View view) {
			turtleDiaryDatabaseHelper
					.addEnvironment(GetEnvironmentFromEditText());
			finish();
		}
	};

	// recover
	private Button.OnClickListener clickEnvironmentActivityRecoverButton = new Button.OnClickListener() {
		@Override
		public void onClick(View view) {
			state = VIEW;
			RefreshView();
		}
	};

	// edit
	private Button.OnClickListener clickEnvironmentActivityEditButton = new Button.OnClickListener() {
		@Override
		public void onClick(View view) {
			// call EditEnvironment(environment) API
			turtleDiaryDatabaseHelper
					.updateEnvironment(GetEnvironmentFromEditText());
			state = VIEW;
			RefreshView();
		}
	};

	public static void finishByOtherActivity() {
		if (activity != null) {
			activity.finish();
			activity = null;
		}
	}
}
