package com.td.models;
import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class FeedLogContainFood implements Serializable
{
	private static final long serialVersionUID = 6065328707258243173L;
	@DatabaseField(generatedId = true)
	private int flid;
	@DatabaseField
	private int fid;
	@DatabaseField
	private int weight;
	
	public FeedLogContainFood()
	{
	}

	public int getFlid() 
	{
		return flid;
	}

	public void setFlid(int flid) 
	{
		this.flid = flid;
	}

	public int getFid() 
	{
		return fid;
	}

	public void setFid(int fid) 
	{
		this.fid = fid;
	}

	public int getWeight() 
	{
		return weight;
	}

	public void setWeight(int weight) 
	{
		this.weight = weight;
	}
}