package com.td.turtlediary;

import java.util.List;

import com.td.models.Pet;
import com.td.models.TurtleDiaryDatabaseHelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageAdapter extends PagerAdapter {
	TurtleDiaryDatabaseHelper helper;
	Context context;

	private List<Pet> pets;

	ImageAdapter(Context context) {
		this.context = context;
		helper = new TurtleDiaryDatabaseHelper(context);
		setPets(helper.getPets());
	}

	@Override
	public int getCount() {
		return getPets().size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		// return false;
		return view == ((ImageView) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		List<Pet> pets = helper.getPets();
		final Pet pet = pets.get(position);
		ImageView imageView = new ImageView(context);
		int padding = context.getResources().getDimensionPixelSize(
				R.dimen.activity_horizontal_margin);
		imageView.setPadding(padding, padding, padding, padding);
		imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

		if (pet.getImage() != null) {
			Drawable image = new BitmapDrawable(BitmapFactory.decodeByteArray(
					pet.getImage(), 0, pet.getImage().length));
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			
			imageView.setImageDrawable(image);
		} else {
			imageView.setImageResource(R.drawable.angonoka);
		}
		((ViewPager) container).addView(imageView, 0);

		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, OptionActivity.class);
				intent.putExtra("pid", pet.getPid());
				context.startActivity(intent);
			}
		});

		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((ImageView) object);
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}
}
