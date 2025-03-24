package com.example.project4ruburger.model;

import java.util.ArrayList;

public class Burger extends Sandwich {
	private boolean doublePatty;

	public Burger(Bread bread, Protein protein, ArrayList<AddOns> addOns, int quantity, boolean doublePatty) {
		super(quantity, bread, protein, addOns);
		this.doublePatty = doublePatty;
	}
}
