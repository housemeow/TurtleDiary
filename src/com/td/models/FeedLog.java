package com.td.models;
import java.io.Serializable;
import java.util.Date;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class FeedLog implements Serializable
{
	private static final long serialVersionUID = -9026069960428391509L;
	public static final String FLID_FIELD_NAME = "flid";
	@DatabaseField(generatedId = true, columnName = FLID_FIELD_NAME)
	private int flid;
	public static final String PID_FIELD_NAME = "pid";
	@DatabaseField(columnName = PID_FIELD_NAME)
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