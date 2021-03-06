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

import com.td.models.Food;
import com.td.models.HealthyLog;
import com.td.models.Pet;
import com.td.models.TurtleDiaryDatabaseHelper;
import com.td.models.Type;

public class OptionActivity extends Activity {
	private TurtleDiaryDatabaseHelper helper = new TurtleDiaryDatabaseHelper(
			this);
	private TextView favoriteFoodTextView;
	private Button healthyLogButton;
	private Pet pet;
	private int pid;
	final String[] unhealthyStrings = { "細長型糞便", "黏膜糞便", "水便", "果凍糞便", "排酸",
			"排石" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);

		pid = getIntent().getIntExtra("pid", -1);
		pet = helper.getPet(pid);
		this.setTitle(pet.getName());
		healthyLogButton = (Button) findViewById(R.id.optionList);
		favoriteFoodTextView = (TextView) findViewById(R.id.recommendFoodTextView);

		Type type = helper.getType(pet.getTid());
		Food theFirstRecommendFood = helper.getFood(type.getRecommendFood1());
		Food theSecondRecommendFood = helper.getFood(type.getRecommendFood2());
		Food theThirdRecommendFood = helper.getFood(type.getRecommendFood3());
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("建議食材：\n   1.")
				.append(theFirstRecommendFood.getName()).append("\n   2.")
				.append(theSecondRecommendFood.getName()).append("\n   3.")
				.append(theThirdRecommendFood.getName());
		favoriteFoodTextView.setText(stringBuilder.toString());

		healthyLogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShowAlertDialogAndList();
			}
		});
		((Button) findViewById(R.id.optionPetInfo))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(OptionActivity.this, PetActivity.class);
						intent.putExtra("pid", pid);
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
				healthyLog.setPid(pet.getPid());
				healthyLog.setComment(unhealthyStrings[which]);
				TurtleDiaryDatabaseHelper helper = new TurtleDiaryDatabaseHelper(
						OptionActivity.this);
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