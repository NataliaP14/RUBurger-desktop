package com.example.project4ruburger.model;

/**
 * @author Natalia Peguero, Olivia Kamau
 */
public enum AddOns {
	LETTUCE (0.30),
	TOMATOES (0.30),
	ONIONS (0.30),
	AVOCADO (0.50),
	CHEESE (1.00);

	private double price;

	/**
	 *
	 * @param price
	 */
	AddOns(double price) {
		this.price = price;
	}

	/**
	 *
	 * @return
	 */
	public double getPrice() {
		return price;
	}
}
