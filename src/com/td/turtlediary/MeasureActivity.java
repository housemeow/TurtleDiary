package com.td.turtlediary;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.td.models.MeasureLog;
import com.td.models.Pet;
import com.td.models.TurtleDiaryDatabaseHelper;

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
				EditText weightEditText = (EditText) findViewById(R.id.measureActivityWeightEditText);
				MeasureLog measureLog = new MeasureLog();
				if (shellLengthEditText.getText().toString().equals("")
						|| weightEditText.getText().toString().equals("")) {
					Toast.makeText(MeasureActivity.this, "尚有未輸入欄位",
							Toast.LENGTH_LONG).show();
					return;
				} 
				else {
					if(!shellLengthEditText.getText().toString().equals("")
							&& !weightEditText.getText().toString().equals(""))
					{
						double shellLength = Double.parseDouble(shellLengthEditText.getText().toString());
						double weight = Double.parseDouble(weightEditText.getText().toString());
						if (shellLength == 0 || weight == 0)
						{
							Toast.makeText(MeasureActivity.this, "測量記錄不可為0",
									Toast.LENGTH_LONG).show();
							return;
						}
					}
					measureLog.setShellLength(Double
							.parseDouble(shellLengthEditText.getText()
									.toString()));
					measureLog.setWeight(Double.parseDouble(weightEditText
							.getText().toString()));
				}
				measureLog.setPid(((Pet) getIntent()
						.getSerializableExtra("pet")).getPid());
				measureLog.setTimeStamp(new Date());
				TurtleDiaryDatabaseHelper helper = new TurtleDiaryDatabaseHelper(
						MeasureActivity.this);
				helper.addMeasureLog(measureLog);
				Log.i("measureLog", measureLog.getMid() + "");
				finish();
			}
		};
	}
}
