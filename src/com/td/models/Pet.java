package com.td.models;

import java.io.Serializable;
import java.util.Date;

public class Pet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8068643934876264258L;
	private int pid;
	private String name;
	private int tid;
	private Date birthday;
	private String privateimage;
	private int eid;
	private String gender;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPrivateimage() {
		return privateimage;
	}

	public void setPrivateimage(String privateimage) {
		this.privateimage = privateimage;
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
