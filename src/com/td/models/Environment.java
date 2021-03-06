package com.td.models;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Environment implements Serializable {
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Environment))
			return false;

		Environment environment = (Environment) obj;
		return eid == environment.eid && name.equals(environment.name)
				&& length == environment.length && width == environment.width
				&& height == environment.height
				&& hotPoint == environment.hotPoint
				&& lowPoint == environment.lowPoint
				&& maxHumidity == environment.maxHumidity
				&& minHumidity == environment.minHumidity;
	}

	@Override
	public String toString() {
		return name;
	}

	private static final long serialVersionUID = 4827056782735085782L;
	@DatabaseField(generatedId = true)
	private int eid;
	@DatabaseField
	private String name;
	@DatabaseField
	private int length;
	@DatabaseField
	private int width;
	@DatabaseField
	private int height;
	@DatabaseField
	private double hotPoint;
	@DatabaseField
	private double lowPoint;
	@DatabaseField
	private int maxHumidity;
	@DatabaseField
	private int minHumidity;

	public Environment() {
	}

	public Environment(String name) {
		this.name = name;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public int getEid() {
		return eid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getHotPoint() {
		return hotPoint;
	}

	public void setHotPoint(double hotPoint) {
		this.hotPoint = hotPoint;
	}

	public double getLowPoint() {
		return lowPoint;
	}

	public void setLowPoint(double lowPoint) {
		this.lowPoint = lowPoint;
	}

	public int getMaxHumidity() {
		return maxHumidity;
	}

	public void setMaxHumidity(int maxHumidity) {
		this.maxHumidity = maxHumidity;
	}

	public int getMinHumidity() {
		return minHumidity;
	}

	public void setMinHumidity(int minHumidity) {
		this.minHumidity = minHumidity;
	}
}