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
	 *
	 * @return
	 */
	@Override
	public double price() {
		return 0;
	}
}
