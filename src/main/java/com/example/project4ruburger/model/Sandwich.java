package com.example.project4ruburger.model;

import java.util.ArrayList;

public class Sandwich extends MenuItem {
	protected Bread bread;
	protected Protein protein;
	protected ArrayList<AddOns> addOns;


	public Sandwich(int quantity, Bread bread, Protein protein, ArrayList<AddOns> addOns) {
		super(quantity);
		this.bread = bread;
		this.protein = protein;
		this.addOns = addOns;
	}


	@Override
	public double price() {
		return 0;
	}
}
