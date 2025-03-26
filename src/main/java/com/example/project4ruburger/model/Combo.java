package com.example.project4ruburger.model;

/**
 * @author Natalia Peguero, Olivia Kamau
 */
public class Combo extends MenuItem {
	private Sandwich sandwich;
	private Beverage drink;
	private Side side;

	public Combo(int quantity, Sandwich sandwich, Beverage drink, Side side) {
		super(quantity);
		this.sandwich = sandwich;
		this.drink = drink;
		this.side = side;
	}

	@Override
	public double price() {
		return 0;
	}
}
