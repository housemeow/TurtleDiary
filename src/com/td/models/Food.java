package com.td.models;
import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Food implements Serializable
{
	@Override
	public String toString() {
		return getName();
	}
	
	private static final long serialVersionUID = -594085769965941949L;
	@DatabaseField(generatedId = true)
	private int fid;
	@DatabaseField
	private String foodType;
	@DatabaseField
	private String name;
	@DatabaseField
	private double water; // 水分 (g)
	@DatabaseField
	private double protein; // 蛋白質 (g)
	@DatabaseField
	private double fat; // 脂肪 (g)
	@DatabaseField
	private double fabric; // 纖維 (g)
	@DatabaseField
	private double ca; // 鈣 (mg)
	@DatabaseField
	private double p; // 磷 (mg)
	
	public Food()
	{
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWater() {
		return water;
	}

	public void setWater(double water) {
		this.water = water;
	}

	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public double getFat() {
		return fat;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public double getFabric() {
		return fabric;
	}

	public void setFabric(double fabric) {
		this.fabric = fabric;
	}

	public double getCa() {
		return ca;
	}

	public void setCa(double ca) {
		this.ca = ca;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	public String getFoodType() {
		return foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}

	
}