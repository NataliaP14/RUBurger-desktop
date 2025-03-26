package com.example.project4ruburger.model;

/**
 * @author Natalia Peguero, Olivia Kamau
 */
public class Beverage extends MenuItem {
	private Size size;
	private Flavor flavor;

	/**
	 * @param quantity
	 */
	public Beverage(int quantity) {
		super(quantity);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public double price() {
		return 0;
	}

}
