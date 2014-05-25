package com.td.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Type {
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Type))
			return false;

		Type type = (Type) obj;
		return name.equals(type.name) && tid == type.tid;
	}

	@Override
	public String toString() {
		return name;
	}

	@DatabaseField(generatedId = true)
	private int tid;
	@DatabaseField
	private String name;
	@DatabaseField
	private String english;
	@DatabaseField
	private String scientificName;
	@DatabaseField
	private String place;
	@DatabaseField
	private String habitation;
	@DatabaseField
	private String length;
	@DatabaseField
	private String temprature;
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[] image;
	@DatabaseField
	private String description;
	@DatabaseField
	private int recommendFood1;
	@DatabaseField
	private int recommendFood2;
	@DatabaseField
	private int recommendFood3;

	public Type() {
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRecommendFood1() {
		return recommendFood1;
	}

	public void setRecommendFood1(int recommondFood1) {
		this.recommendFood1 = recommondFood1;
	}

	public int getRecommendFood2() {
		return recommendFood2;
	}

	public void setRecommendFood2(int recommondFood2) {
		this.recommendFood2 = recommondFood2;
	}

	public int getRecommendFood3() {
		return recommendFood3;
	}

	public void setRecommendFood3(int recommondFood3) {
		this.recommendFood3 = recommondFood3;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getHabitation() {
		return habitation;
	}

	public void setHabitation(String habitation) {
		this.habitation = habitation;
	}

	public String getTemprature() {
		return temprature;
	}

	public void setTemprature(String temprature) {
		this.temprature = temprature;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
}
