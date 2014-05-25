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
import au.com.bytecode.opencsv.CSVReader;

public class TurtleDiaryCsvReader {

	private static final int FOOD_TYPE_INDEX = 2;
	private static final int FOOD_NAME = 3;
	private static final int FOOD_WATER = 5;
	private static final int FOOD_PROTEIN = 6;
	private static final int FOOD_FAT = 7;
	private static final int FOOD_FIBRIC = 9;
	private static final int FOOD_P = 24;
	private static final int FOOD_CA = 27;

	private static final int TYPE_NAME = 0;
	private static final int TYPE_ENGLISH_NAME = 1;
	private static final int TYPE_SCIENTIFIC_NAME = 2;
	private static final int TYPE_PLACE = 3;
	private static final int TYPE_HABITATION = 4;
	private static final int TYPE_LENGTH = 5;
	private static final int TYPE_TEMPERATURE = 6;
	private static final int TYPE_DESCRIPTION = 7;

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
		InputStream inputStream = context.getResources().openRawResource(
				raw.food);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

		CSVReader csvReader = new CSVReader(inputStreamReader);
		try {
			List<String[]> rows = null;
			rows = csvReader.readAll();
			rows.remove(0);
			for (String[] row : rows) {
				foods.add(getFood(row));
			}
			csvReader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return foods;
	}

	public List<Type> getTypesFromCsv(Context context) {
		List<Type> types = new ArrayList<Type>();
		InputStream inputStream = context.getResources().openRawResource(
				raw.type);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

		CSVReader csvReader = new CSVReader(inputStreamReader);
		try {
			List<String[]> rows = null;
			rows = csvReader.readAll();
			rows.remove(0);
			for (String[] row : rows) {
				types.add(getType(row));
			}
			csvReader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return types;
	}

	private Food getFood(String[] row) {
		Food food = new Food();
		food.setFoodType(row[FOOD_TYPE_INDEX]);
		food.setName(row[FOOD_NAME]);
		food.setWater(getDoubleFromString(row[FOOD_WATER]));
		food.setProtein(getDoubleFromString(row[FOOD_PROTEIN]));
		food.setFat(getDoubleFromString(row[FOOD_FAT]));
		food.setFabric(getDoubleFromString(row[FOOD_FIBRIC]));
		food.setCa(getDoubleFromString(row[FOOD_CA]));
		food.setP(getDoubleFromString(row[FOOD_P]));
		return food;
	}

	private Type getType(String[] row) {
		Type type = new Type();
		type.setName(row[TYPE_NAME]);
		type.setEnglish(row[TYPE_ENGLISH_NAME]);
		type.setScientificName(row[TYPE_SCIENTIFIC_NAME]);
		type.setPlace(row[TYPE_PLACE]);
		type.setHabitation(row[TYPE_HABITATION]);
		type.setLength(row[TYPE_LENGTH]);
		type.setTemprature(row[TYPE_TEMPERATURE]);
		type.setDescription(row[TYPE_DESCRIPTION]);
		return type;
	}
}
