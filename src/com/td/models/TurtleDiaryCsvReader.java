package com.td.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.td.turtlediary.R.raw;

import android.content.Context;
import android.util.Log;

public class TurtleDiaryCsvReader {

	private static final int FOOD_TYPE_INDEX = 1;
	private static final int FOOD_NAME = 2;
	private static final int FOOD_WATER = 4;
	private static final int FOOD_PROTEIN = 5;
	private static final int FOOD_FAT = 6;
	private static final int FOOD_FIBRIC = 8;
	private static final int FOOD_CA = 22;
	private static final int FOOD_P = 24;

	public double getDoubleFromString(String string) {
		double value = 0;
		try {
			value = Double.parseDouble(string);
		} catch (NumberFormatException e) {
		}
		return value;
	}

	public List<Food> getFoodsFromCsv(Context context) {
		List<Food> foods = new ArrayList<Food>();
		InputStream inputStream = context
				.getResources().openRawResource(raw.food);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(inputStreamReader);
		try {
			String line = reader.readLine();
			String[] header = line.split(",");
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(",");
				Food food = new Food();
				food.setFoodType(values[FOOD_TYPE_INDEX]);
				food.setName(values[FOOD_NAME]);
				food.setWater(getDoubleFromString(values[FOOD_WATER]));
				food.setProtein(getDoubleFromString(values[FOOD_PROTEIN]));
				food.setFat(getDoubleFromString(values[FOOD_FAT]));
				food.setFabric(getDoubleFromString(values[FOOD_FIBRIC]));
				food.setCa(getDoubleFromString(values[FOOD_CA]));
				food.setP(getDoubleFromString(values[FOOD_P]));
				foods.add(food);

				StringBuilder sb = new StringBuilder();
				for(int i=0;i<header.length;i++){
					sb.append(header[i]).append("=").append(values[i]).append(" ");
				}
				String output = sb.toString();
				Log.d("Tag", output);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return foods;
	}
}
