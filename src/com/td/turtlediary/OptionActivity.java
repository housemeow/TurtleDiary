package com.td.turtlediary;

import com.td.models.Pet;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OptionActivity extends Activity {
	private TextView favoriteFoodTextView;
	private Button shitButton;
	final String[] shitListStrings = { "排便", "排酸", "...." };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);
		this.setTitle(R.string.petInformation);
		shitButton = (Button) findViewById(R.id.optionList);
		favoriteFoodTextView = (TextView) findViewById(R.id.favoriteFoodTextView);
		favoriteFoodTextView.setText("喜好食物：\n   1.xxx\n   2.xxxx\n   3.xxxxxx");
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
						Pet pet = (Pet) getIntent().getSerializableExtra("Pet");
						intent.putExtra("Pet", pet);
						startActivity(intent);
					}
				});
	}

	private void ShowAlertDialogAndList() {
		Builder MyAlertDialog = new AlertDialog.Builder(this);
		MyAlertDialog.setTitle(R.string.shit);
		// 建立選擇的事件
		DialogInterface.OnClickListener ListClick = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// ShowMsgDialog(ListStr[which]);
			}
		};
		// 建立按下取消什麼事情都不做的事件
		DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		};
		MyAlertDialog.setItems(shitListStrings, ListClick);
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