package com.example.project4ruburger.model;

/**
 * @author Natalia Peguero, Olivia Kamau
 */
public enum Protein {
	BEEF_PATTY(6.99),   // price for a single patty burger (default option)
	ROAST_BEEF(10.99),
	SALMON(9.99),
	CHICKEN(8.99);

	private double price;

	Protein(double price) {
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

}
