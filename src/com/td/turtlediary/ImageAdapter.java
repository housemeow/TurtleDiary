package com.td.turtlediary;

import java.util.List;

import com.td.models.Pet;
import com.td.models.TurtleDiaryDatabaseHelper;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageAdapter extends PagerAdapter {
	TurtleDiaryDatabaseHelper helper;
	
	Context context;
    private int[] GalImages = new int[] {
        R.drawable.angonoka,
        R.drawable.indian_star_tortoise,
        R.drawable.radiated_tortoise
    };
    
    ImageAdapter(Context context){
    	this.context=context;
    	helper = new TurtleDiaryDatabaseHelper(context);
    }
    
    @Override
    public int getCount() {
      return GalImages.length;
    }

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		// return false;
		return view == ((ImageView) object);
	}

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      ImageView imageView = new ImageView(context);
      int padding = context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
      imageView.setPadding(padding, padding, padding, padding);
      imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
      imageView.setImageResource(GalImages[position]);
      ((ViewPager) container).addView(imageView, 0);
      
      
      imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(context, OptionActivity.class);
				List<Pet> pets =  helper.getPets(); 
				Pet pet = pets.get(0);
				intent.putExtra("pet", pet);
				intent.putExtra("showPrevious", "true");

				context.startActivity(intent);
			}
		});
      
      
      return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      ((ViewPager) container).removeView((ImageView) object);
    }
}
