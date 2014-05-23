package com.td.turtlediary;

import com.navdrawer.SimpleSideDrawer;
import com.td.models.Pet;
import com.td.models.TurtleDiaryDatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomePageActivity extends Activity {
	private TurtleDiaryDatabaseHelper helper = new TurtleDiaryDatabaseHelper(
			this);
	private SimpleSideDrawer mNav;
	private ViewPager viewPager;
	private TextView homePagePetNameEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);
		mNav = new SimpleSideDrawer(this);

		mNav.setRightBehindContentView(R.layout.activity_behind_right_simple);
		viewPager = (ViewPager) findViewById(R.id.homePagePetViewPager);
		homePagePetNameEditText = (TextView)findViewById(R.id.homePagePetNameEditText);
		homePagePetNameEditText.setText(helper.getPets().get(0).getName());
		viewPager.setOnPageChangeListener(getViewPagerPageChangeListener());

		findViewById(R.id.homePageMenuButton).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						mNav.toggleRightDrawer();
						// 報表
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
						// 環境設定
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
						// 餵食記錄
						TextView feedTextView = (TextView) findViewById(R.id.recordTextView);
						feedTextView.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(
										HomePageActivity.this,
										SelectFoodsActivity.class);
								Pet pet = helper.getPets().get(
										viewPager.getCurrentItem());
								intent.putExtra("pid", pet.getPid());
								startActivity(intent);
							}
						});
						// 測量
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
						// 健康紀錄
						TextView healthyLogTextView = (TextView) findViewById(R.id.healthyLogTextView);
						healthyLogTextView
								.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										Intent intent = new Intent(
												HomePageActivity.this,
												HealthyLogActivity.class);
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

	private OnPageChangeListener getViewPagerPageChangeListener() {
		return new OnPageChangeListener() {

			@Override
			public void onPageSelected(int which) {
				Pet pet = helper.getPets().get(which);
				homePagePetNameEditText.setText(pet.getName());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		};
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
