package com.example.project4ruburger.model;

import java.util.ArrayList;

/**
 * @author Natalia Peguero, Olivia Kamau
 */
public class Order {
	private int number;
	private ArrayList<MenuItem> items;

	public Order (int number) {
		this.number = number;
		this.items = new ArrayList<>();

	}

	/*public int testMethod() {
		for(MenuItem item: items) {
			total += item.price();
		}
		return 0;
	} */
}
