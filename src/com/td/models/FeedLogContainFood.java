package com.td.models;
import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class FeedLogContainFood implements Serializable
{
	private static final long serialVersionUID = 6065328707258243173L;
	public static final String FLID_FIELD_NAME = "flid";
	@DatabaseField(generatedId = true, columnName = FLID_FIELD_NAME)
	private int flid;
	@DatabaseField
	private int fid;
	public static final String WEIGHT_FIELD_NAME = "weight";
	@DatabaseField(columnName = WEIGHT_FIELD_NAME)
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