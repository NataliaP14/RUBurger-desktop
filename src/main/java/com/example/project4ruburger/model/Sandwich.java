package com.example.project4ruburger.model;

import java.util.ArrayList;

/**
 * @author Natalia Peguero, Olivia Kamau
 */
public class Sandwich extends MenuItem {
	protected Bread bread;
	protected Protein protein;
	protected ArrayList<AddOns> addOns;


	/**
	 *
	 * @param quantity
	 * @param bread
	 * @param protein
	 * @param addOns
	 */
	public Sandwich(int quantity, Bread bread, Protein protein, ArrayList<AddOns> addOns) {
		super(quantity);
		this.bread = bread;
		this.protein = protein;
		this.addOns = addOns;
	}

	/**
	 *  Calculates the total price for the sandwich with the user's selections.
	 *  @return 	The total price of the sandwich.
	 */
	@Override
	public double price() {

		double totalPrice = protein.getPrice();
		for(AddOns addOn : addOns) { totalPrice += addOn.getPrice(); }
		totalPrice *= quantity;

		return totalPrice;
	}


	public Bread getBread() {
		return bread;
	}

	public Protein getProtein() {
		return protein;
	}

	public ArrayList<AddOns> getAddOns() {
		return addOns;
	}

	/**
	 *  Returns a string representation of the user's sandwich order.
	 *  @return   String with the user's sandwich details.
	 */
	public String toString() {
		String addOnsString;

		if(addOns.isEmpty()) {
			addOnsString = "None";
		} else {
			addOnsString = "";
			for(int i = 0; i < addOns.size(); i++) {
				addOnsString += addOns.get(i).name();
				if(i < addOns.size() -1) {
					addOnsString += ", ";
				}
			}
		}

		return String.format("Sandwich [%s %s] [%s] [%.2f] [%d]", bread, protein, addOnsString, price(), quantity);
	}

}
