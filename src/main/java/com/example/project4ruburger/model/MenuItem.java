package com.example.project4ruburger.model;

public abstract class MenuItem {
	protected int quantity;

	public MenuItem(int quantity) {
		this.quantity = quantity;
	}

	public abstract double price();
}
