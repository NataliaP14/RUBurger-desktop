package com.example.project4ruburger.model;

import java.util.ArrayList;

/**
 * @author Natalia Peguero, Olivia Kamau
 */
public class Burger extends Sandwich {
	private boolean doublePatty;
	private static final double DOUBLE_PATTY_PRICE = 2.50;

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
	 * Calculates the total price for the burger depending on the user's selections.
	 * @return   The total price for the burger.
	 */
	@Override
	public double price() {

		double totalPrice = super.price();

		if(doublePatty) {
			totalPrice += DOUBLE_PATTY_PRICE;
		}

		return totalPrice;

	}

	/**
	 *  Returns a string representation of the user's burger order.
	 *  @return   String with the user's burger details.
	 */
	@Override
	public String toString() {

		String addOnsString;

		if (addOns.isEmpty()) {
			addOnsString = "None";
		} else {
			addOnsString = "";
			for (int i = 0; i < addOns.size(); i++) {
				addOnsString += addOns.get(i).name();
				if (i < addOns.size() - 1) {
					addOnsString += ", ";
				}
			}
		}

		String pattyType = doublePatty ? "double" : "single";

		return String.format("Burger, %s [%s] [%s] [%.2f] [%d]", pattyType, bread, addOnsString, price(), quantity);
	}

}
