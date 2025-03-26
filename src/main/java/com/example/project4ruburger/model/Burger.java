package com.example.project4ruburger.model;

import java.util.ArrayList;

/**
 * @author Natalia Peguero, Olivia Kamau
 */
public class Burger extends Sandwich {
	private boolean doublePatty;

	/**
	 *
	 * @param bread
	 * @param addOns
	 * @param quantity
	 * @param doublePatty
	 */
	public Burger(Bread bread, ArrayList<AddOns> addOns, int quantity, boolean doublePatty) {
		super(quantity, bread, Protein.BEEF_PATTY, addOns);
		this.doublePatty = doublePatty;
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
