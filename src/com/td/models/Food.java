package com.td.models;
import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Food implements Serializable
{
	private static final long serialVersionUID = -594085769965941949L;
	@DatabaseField(generatedId = true)
	private int fid;
	@DatabaseField
	private String name;
	@DatabaseField
	private int kcal;
	@DatabaseField
	private int water;
	@DatabaseField
	private double fat;
	@DatabaseField
	private double fabric;
	@DatabaseField
	private int ca;
	@DatabaseField
	private int p;
	
	public Food()
	{
	}

	public int getFid() 
	{
		return fid;
	}

	public void setFid(int fid) 
	{
		this.fid = fid;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public int getKcal() 
	{
		return kcal;
	}

	public void setKcal(int kcal) 
	{
		this.kcal = kcal;
	}

	public int getWater() 
	{
		return water;
	}

	public void setWater(int water) 
	{
		this.water = water;
	}

	public double getFat() 
	{
		return fat;
	}

	public void setFat(double fat) 
	{
		this.fat = fat;
	}

	public double getFabric() 
	{
		return fabric;
	}

	public void setFabric(double fabric) 
	{
		this.fabric = fabric;
	}

	public int getCa() 
	{
		return ca;
	}

	public void setCa(int ca) 
	{
		this.ca = ca;
	}

	public int getP() 
	{
		return p;
	}

	public void setP(int p) 
	{
		this.p = p;
	}
}