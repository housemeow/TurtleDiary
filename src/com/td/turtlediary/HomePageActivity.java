package com.td.turtlediary;

import com.navdrawer.SimpleSideDrawer;
import com.td.models.Pet;
import com.td.models.TurtleDiaryDatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomePageActivity extends Activity {
	private TurtleDiaryDatabaseHelper helper = new TurtleDiaryDatabaseHelper(
			this);
	private SimpleSideDrawer mNav;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		mNav = new SimpleSideDrawer(this);

		mNav.setRightBehindContentView(R.layout.activity_behind_right_simple);

		ViewPager viewPager = (ViewPager) findViewById(R.id.homePagePetViewPager);
		ImageAdapter adapter = new ImageAdapter(this);

		viewPager.setAdapter(adapter);

		findViewById(R.id.homePageMenuButton).setOnClickListener(
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
										startActivity(intent);
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
										startActivity(intent);
									}
								});

						TextView feedTextView = (TextView) findViewById(R.id.recordTextView);
						feedTextView.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent();
								intent.setClass(HomePageActivity.this,
										SelectFoodsActivity.class);
								startActivity(intent);
							}
						});

						TextView measureTextView = (TextView) findViewById(R.id.measureTextView);
						measureTextView
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										Intent intent = new Intent();
										intent.setClass(HomePageActivity.this,
												MeasureActivity.class);
										Pet pet = helper.getPets().get(0);
										intent.putExtra("pet", pet);
										startActivity(intent);
									}
								});
					}
				});

		ImageButton addPetImageButton = (ImageButton) findViewById(R.id.homePageAddPetButton);
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
