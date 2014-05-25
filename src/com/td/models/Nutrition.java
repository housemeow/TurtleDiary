package com.td.models;

public class Nutrition
{
	private double dryWeight; // 乾重 (g)
	private double protein; // 蛋白質 (g)
	private double fat; // 脂肪 (g)
	private double fabric; // 纖維 (g)
	private double ca; // 鈣 (g)
	private double p; // 磷 (g)
	
	public Nutrition()
	{
	}

	public double getDryWeight() {
		return dryWeight;
	}

	public void setDryWeight(double dryWeight) {
		this.dryWeight = dryWeight;
	}

	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public double getFat() {
		return fat;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public double getFabric() {
		return fabric;
	}

	public void setFabric(double fabric) {
		this.fabric = fabric;
	}

	public double getCa() {
		return ca;
	}

	public void setCa(double ca) {
		this.ca = ca;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}
	
	// getProteinPercentage
	public double getProteinPercentage() {
		return protein / dryWeight * 100;
	}
	
	// getFatPercentage
	public double getFatPercentage() {
		return fat / dryWeight * 100;
	}
	
	// getFabricPercentage
	public double getFabricPercentage() {
		return fabric / dryWeight * 100;
	}
	
	// getCaPercentage
	public double getCaPercentage() {
		return ca / dryWeight * 100;
	}
	
	// getPPercentage
	public double getPPercentage() {
		return p / dryWeight * 100;
	}
	
	// getCaPRatio
	public double getCaPRatio() {
		return ca / p;
	}
}