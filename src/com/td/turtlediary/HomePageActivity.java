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
	private ViewPager viewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		mNav = new SimpleSideDrawer(this);

		mNav.setRightBehindContentView(R.layout.activity_behind_right_simple);
		viewPager = (ViewPager) findViewById(R.id.homePagePetViewPager);

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
										Intent intent = new Intent(
												HomePageActivity.this,
												ReportActivity.class);
										Pet pet = helper.getPets().get(
												viewPager.getCurrentItem());
										intent.putExtra("pet", pet);
										startActivity(intent);
									}
								});

						TextView environmentTextView = (TextView) findViewById(R.id.settingTextView);
						environmentTextView
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										startActivity(new Intent(
												HomePageActivity.this,
												EnvironmentListActivity.class));
									}
								});

						TextView feedTextView = (TextView) findViewById(R.id.recordTextView);
						feedTextView.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(
										HomePageActivity.this,
										SelectFoodsActivity.class);
								Pet pet = helper.getPets().get(
										viewPager.getCurrentItem());
								intent.putExtra("pet", pet);
								startActivity(intent);
							}
						});

						TextView measureTextView = (TextView) findViewById(R.id.measureTextView);
						measureTextView
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										Intent intent = new Intent(
												HomePageActivity.this,
												MeasureActivity.class);
										Pet pet = helper.getPets().get(
												viewPager.getCurrentItem());
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

	@Override
	protected void onStart() {
		super.onStart();
		int currentItemIndex = viewPager.getCurrentItem();
		ImageAdapter adapter = new ImageAdapter(this);
		adapter.setPets(helper.getPets());
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(currentItemIndex);
	}
}
