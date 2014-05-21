package com.td.turtlediary;

import java.util.Date;

import com.td.models.MeasureLog;
import com.td.models.Pet;
import com.td.models.TurtleDiaryDatabaseHelper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MeasureActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("測量記錄");
		setContentView(R.layout.activity_measure);
		Button addButton = (Button) findViewById(R.id.measureActivityAddButton);
		addButton.setOnClickListener(getAddButtonClickListener());
	}

	private OnClickListener getAddButtonClickListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Add add measure API
				EditText shellLengthEditText = (EditText) findViewById(R.id.measureActivityShellLengthEditText);
				EditText weightEditText = (EditText)findViewById(R.id.measureActivityWeightEditText);
				MeasureLog measureLog = new MeasureLog();
				measureLog.setLength(Double.parseDouble(shellLengthEditText.getText().toString()));
				measureLog.setWeight(Double.parseDouble(weightEditText.getText().toString()));
				measureLog.setPid(((Pet)getIntent().getSerializableExtra("pet")).getPid());
				measureLog.setTimeStamp(new Date());
				TurtleDiaryDatabaseHelper helper = new TurtleDiaryDatabaseHelper(MeasureActivity.this);
				helper.addMeasureLog(measureLog);
				Log.i("measureLog", measureLog.getMid()+"");
				finish();
			}
		};
	}
}
