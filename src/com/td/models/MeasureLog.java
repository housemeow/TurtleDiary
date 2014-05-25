package com.td.models;

import java.io.Serializable;
import java.util.Date;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class MeasureLog implements Serializable {
	private static final long serialVersionUID = 8762657374300875526L;
	@DatabaseField(generatedId = true)
	private int mid;
	public static final String PID_FIELD_NAME = "pid";
	private static final String LENGTH_FIELD_NAME = "shellLength";
	private static final String WEIGHT_FIELD_NAME = "weight";
	@DatabaseField(columnName = PID_FIELD_NAME)
	private int pid;
	@DatabaseField
	private Date timeStamp;
	@DatabaseField(columnName = LENGTH_FIELD_NAME)
	private double shellLength;
	@DatabaseField(columnName = WEIGHT_FIELD_NAME)
	private double weight;

	public MeasureLog() {
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getShellLength() {
		return shellLength;
	}

	public void setShellLength(double shellLength) {
		this.shellLength = shellLength;
	}
}