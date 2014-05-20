package com.td.models;
import java.io.Serializable;
import java.util.Date;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Pet implements Serializable 
{
	@Override
	public String toString() {
		return name;
	}
	
	private static final long serialVersionUID = -8068643934876264258L;
	@DatabaseField(generatedId = true)
	private int pid;
	@DatabaseField
	private String name;
	@DatabaseField
	private int tid;
	@DatabaseField
	private Date birthday;
	//@DatabaseField
	private byte[] image;
	@DatabaseField
	private int eid;
	@DatabaseField
	private String gender;

	public Pet() 
	{
	}
	
	public Pet(String name) 
	{
		this.name = name;
	}
	
	public int getPid() 
	{
		return pid;
	}

	public void setPid(int pid) 
	{
		this.pid = pid;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public Date getBirthday() 
	{
		return birthday;
	}

	public void setBirthday(Date birthday) 
	{
		this.birthday = birthday;
	}
	public int getEid() 
	{
		return eid;
	}
	
	public byte[] getImage() 
	{
		return image;
	}

	public void setImage(byte[] image) 
	{
		this.image = image;
	}

	public void setEid(int eid) 
	{
		this.eid = eid;
	}

	public int getTid() 
	{
		return tid;
	}

	public void setTid(int tid) 
	{
		this.tid = tid;
	}

	public String getGender() 
	{
		return gender;
	}

	public void setGender(String gender) 
	{
		this.gender = gender;
	}
}
