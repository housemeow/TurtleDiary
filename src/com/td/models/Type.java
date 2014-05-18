package com.td.models;

public class Type {
	private int tid;
	private String name;
	private String image;
	private String description;
	private int recommendFood1;
	private int recommendFood2;
	private int recommendFood3;

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
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

	public int getRecommondFood1() {
		return recommendFood1;
	}

	public void setRecommondFood1(int recommondFood1) {
		this.recommendFood1 = recommondFood1;
	}

	public int getRecommondFood2() {
		return recommendFood2;
	}

	public void setRecommondFood2(int recommondFood2) {
		this.recommendFood2 = recommondFood2;
	}

	public int getRecommondFood3() {
		return recommendFood3;
	}

	public void setRecommondFood3(int recommondFood3) {
		this.recommendFood3 = recommondFood3;
	}
}
