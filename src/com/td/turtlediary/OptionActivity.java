package com.td.turtlediary;

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
	private Button button1;
	final String[] ListStr = {"排便", "排酸", "...."};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);
		this.setTitle("寵物選項");
		button1 = (Button)findViewById(R.id.optionList);
		favoriteFoodTextView = (TextView)findViewById(R.id.favoriteFoodTextView);
		favoriteFoodTextView.setText("喜好食物：\n   1.xxx\n   2.xxxx\n   3.xxxxxx");
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowAlertDialogAndList();
			}
		});
		
		

		 ((Button)findViewById(R.id.optionPetInfo)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(OptionActivity.this, ModifyPetActivity.class);
				intent.putExtra("title", "環境新增");
				intent.putExtra("buttonName", "新增");
				startActivity(intent);
			}
		});
	}
	
	private void ShowAlertDialogAndList()
	{
	Builder MyAlertDialog = new AlertDialog.Builder(this);
	MyAlertDialog.setTitle("排泄選單");
	//建立選擇的事件
	DialogInterface.OnClickListener ListClick = new DialogInterface.OnClickListener(){
	public void onClick(DialogInterface dialog, int which) {
	//ShowMsgDialog(ListStr[which]);
	}};
	//建立按下取消什麼事情都不做的事件
	DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener(){
	public void onClick(DialogInterface dialog, int which) {}}; 
	MyAlertDialog.setItems(ListStr, ListClick);
	MyAlertDialog.setNeutralButton("取消",OkClick );
	MyAlertDialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.option, menu);
		return true;
	}

}