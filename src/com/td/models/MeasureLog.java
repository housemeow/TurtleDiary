package com.td.models;
import java.io.Serializable;
import java.util.Date;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class MeasureLog implements Serializable
{
	private static final long serialVersionUID = 8762657374300875526L;
	@DatabaseField(generatedId = true)
	private int mid;
	@DatabaseField
	private int pid;
	@DatabaseField
	private Date timeStamp;
	@DatabaseField
	private double length;
	@DatabaseField
	private double weight;
	
	public MeasureLog()
	{
	}

	public int getMid() 
	{
		return mid;
	}

	public void setMid(int mid) 
	{
		this.mid = mid;
	}

	public int getPid() 
	{
		return pid;
	}

	public void setPid(int pid) 
	{
		this.pid = pid;
	}

	public Date getTimeStamp() 
	{
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) 
	{
		this.timeStamp = timeStamp;
	}

	public double getLength() 
	{
		return length;
	}

	public void setLength(double length) 
	{
		this.length = length;
	}

	public double getWeight() 
	{
		return weight;
	}

	public void setWeight(double weight) 
	{
		this.weight = weight;
	}
}