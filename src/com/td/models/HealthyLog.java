package com.td.models;
import java.io.Serializable;
import java.util.Date;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class HealthyLog implements Serializable
{
	private static final long serialVersionUID = 7353764067633844383L;
	@DatabaseField(generatedId = true)
	private int hid;
	@DatabaseField
	private int pid;
	@DatabaseField
	private Date timeStamp;
	@DatabaseField
	private String comment;
	
	public HealthyLog()
	{
	}

	public int getHid() 
	{
		return hid;
	}

	public void setHid(int hid) 
	{
		this.hid = hid;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) 
	{
		this.comment = comment;
	}
}