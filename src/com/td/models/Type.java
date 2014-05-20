package com.td.models;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Type 
{
	@DatabaseField(generatedId = true)
	private int tid;
	@DatabaseField
	private String name;
	@DatabaseField
	private byte[] image;
	@DatabaseField
	private String description;
	@DatabaseField
	private int recommendFood1;
	@DatabaseField
	private int recommendFood2;
	@DatabaseField
	private int recommendFood3;

	public Type()
	{
	}
	
	public int getTid() 
	{
		return tid;
	}

	public void setTid(int tid) 
	{
		this.tid = tid;
	}

	public byte[] getImage() 
	{
		return image;
	}

	public void setImage(byte[] image) 
	{
		this.image = image;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public int getRecommondFood1() 
	{
		return recommendFood1;
	}

	public void setRecommondFood1(int recommondFood1) 
	{
		this.recommendFood1 = recommondFood1;
	}

	public int getRecommondFood2() 
	{
		return recommendFood2;
	}

	public void setRecommondFood2(int recommondFood2) 
	{
		this.recommendFood2 = recommondFood2;
	}

	public int getRecommondFood3() 
	{
		return recommendFood3;
	}

	public void setRecommondFood3(int recommondFood3) 
	{
		this.recommendFood3 = recommondFood3;
	}
}
