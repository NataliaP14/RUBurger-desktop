package com.example.project4ruburger.model;

import java.util.ArrayList;

public class Order {
	private int number;
	private ArrayList<MenuItem> items;

	public Order (int number, ArrayList<MenuItem> items) {
		this.number = number;
		this.items = items;
	}
}
