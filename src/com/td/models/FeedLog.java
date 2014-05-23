package com.td.models;
import java.io.Serializable;
import java.util.Date;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class FeedLog implements Serializable
{
	private static final long serialVersionUID = -9026069960428391509L;
	@DatabaseField(generatedId = true)
	private int flid;
	@DatabaseField
	private int pid;
	@DatabaseField
	private Date timeStamp;
	
	public FeedLog()
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
}