package com.td.turtlediary;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.td.models.Food;
import com.td.models.HealthyLog;
import com.td.models.Pet;
import com.td.models.TurtleDiaryDatabaseHelper;
import com.td.models.Type;

public class OptionActivity extends Activity {
	private TextView favoriteFoodTextView;
	private Button shitButton;
	final String[] unhealthyStrings = { "細長型糞便", "黏膜糞便", "水便", "果凍糞便", "排酸",
			"排石" };
	private TurtleDiaryDatabaseHelper turtleDiaryDatabaseHelper = new TurtleDiaryDatabaseHelper(this);
	private int pid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);
		favoriteFoodTextView = (TextView) findViewById(R.id.favoriteFoodTextView);
		shitButton = (Button) findViewById(R.id.optionList);
		Intent intent = getIntent();
		pid = intent.getIntExtra("pid", -1);
		Pet pet = turtleDiaryDatabaseHelper.getPet(pid);
		/*
		Type type = turtleDiaryDatabaseHelper.getType(pet.getTid());
		Food theFirstRecommendFood = turtleDiaryDatabaseHelper.getFood(type.getRecommendFood1());
		Food theSecondRecommendFood = turtleDiaryDatabaseHelper.getFood(type.getRecommendFood2());
		Food theThirdRecommendFood = turtleDiaryDatabaseHelper.getFood(type.getRecommendFood3());
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("建議食材：\n   1.").append(theFirstRecommendFood.getName()).append("\n   2.").append(theSecondRecommendFood.getName()).append("\n   3.").append(theThirdRecommendFood.getName());
		favoriteFoodTextView.setText(stringBuilder.toString());
		*/
		favoriteFoodTextView.setText("建議食材：\n   1.xxx\n   2.xxxx\n   3.xxxxxx");
		this.setTitle(pet.getName());
		shitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowAlertDialogAndList();
			}
		});
		((Button) findViewById(R.id.optionPetInfo))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(OptionActivity.this, PetActivity.class);
						Pet pet = turtleDiaryDatabaseHelper.getPet(pid);
						intent.putExtra("pet", pet);
						startActivity(intent);
					}
				});
				
	}

	private void ShowAlertDialogAndList() {
		Builder MyAlertDialog = new AlertDialog.Builder(this);
		MyAlertDialog.setTitle(R.string.healthyLog);
		// 建立選擇的事件
		DialogInterface.OnClickListener ListClick = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				HealthyLog healthyLog = new HealthyLog();
				Pet pet = turtleDiaryDatabaseHelper.getPet(pid);
				healthyLog.setPid(pet.getPid());
				healthyLog.setComment(unhealthyStrings[which]);
				TurtleDiaryDatabaseHelper helper = new TurtleDiaryDatabaseHelper(OptionActivity.this);
				healthyLog.setTimeStamp(new Date());
				helper.addHealthyLog(healthyLog);
			}
		};
		// 建立按下取消什麼事情都不做的事件
		DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		};
		MyAlertDialog.setItems(unhealthyStrings, ListClick);
		MyAlertDialog.setNeutralButton("取消", OkClick);
		MyAlertDialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.option, menu);
		return true;
	}

}