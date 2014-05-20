package com.td.turtlediary;

import java.util.Date;

import com.navdrawer.SimpleSideDrawer;
import com.td.models.Pet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomePageActivity extends Activity {

	private SimpleSideDrawer mNav;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		mNav = new SimpleSideDrawer(this);

		mNav.setRightBehindContentView(R.layout.activity_behind_right_simple);

		findViewById(R.id.mainPageMenuButton).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						mNav.toggleRightDrawer();
						TextView reportTextView = (TextView) findViewById(R.id.pointTextView);
						reportTextView
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										Intent intent = new Intent();
										intent.setClass(HomePageActivity.this,
												ReportActivity.class);
										startActivityForResult(intent, 1);
									}
								});

						TextView environmentTextView = (TextView) findViewById(R.id.settingTextView);
						environmentTextView
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										Intent intent = new Intent();
										intent.setClass(HomePageActivity.this,
												EnvironmentListActivity.class);
										startActivityForResult(intent, 1);
									}
								});

						// /這邊要加
						TextView feedTextView = (TextView) findViewById(R.id.recordTextView);
						feedTextView.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent();
								intent.setClass(HomePageActivity.this,
										SelectFoodsActivity.class);
								startActivityForResult(intent, 1);
							}
						});


						// /這邊要加
						TextView measureTextView = (TextView) findViewById(R.id.measureTextView);
						measureTextView.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent();
								intent.setClass(HomePageActivity.this,
										MeasureActivity.class);
								startActivityForResult(intent, 1);
							}
						});
					}
				});

		ImageButton petButton = (ImageButton) findViewById(R.id.mainPagePetImageButton);
		petButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(HomePageActivity.this, OptionActivity.class);
				Pet pet = new Pet();
				pet.setName("Turtle");
				pet.setGender("公");
				pet.setBirthday(new Date());
				intent.putExtra("Pet", pet);
				intent.putExtra("showPrevious", "true");

				startActivityForResult(intent, 1);
			}
		});

		ImageButton addPetImageButton = (ImageButton) findViewById(R.id.mainPageAddPetButton);
		addPetImageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomePageActivity.this,
						PetActivity.class);
				startActivity(intent);
			}
		});
	}
}
