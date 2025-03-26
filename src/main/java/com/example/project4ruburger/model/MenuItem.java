package com.example.project4ruburger.model;

/**
 * @author Natalia Peguero, Olivia Kamau
 */
public abstract class MenuItem {
	protected int quantity;
	public abstract double price();

	/**
	 *
	 * @param quantity
	 */
	public MenuItem(int quantity) {
		this.quantity = quantity;
	}
}
