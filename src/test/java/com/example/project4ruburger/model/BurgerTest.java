package com.example.project4ruburger.model;

import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.*;

public class BurgerTest {

	@Test
	public void singlePattyBurgerTest() {
		Bread bread = Bread.BRIOCHE;
		ArrayList<AddOns> addOns = new ArrayList<>();
		addOns.add(AddOns.CHEESE);
		addOns.add(AddOns.ONIONS);

		int quantity = 1;

		boolean isDouble = false;

		Burger burger = new Burger(bread, addOns, quantity, isDouble);

		double expectedPrice = 0.0;
		if (isDouble) {
			expectedPrice = Protein.BEEF_PATTY.getPrice() + Burger.DOUBLE_PATTY_PRICE;
		} else {
			expectedPrice = Protein.BEEF_PATTY.getPrice();
		}

		for (AddOns addOn : addOns) {
			expectedPrice += addOn.getPrice();
		}

		expectedPrice = expectedPrice * quantity;
		assertEquals(expectedPrice, burger.price(), 0.01);
	}

	@Test
	public void doublePattyBurgerTest() {
		Bread bread = Bread.PRETZEL;
		ArrayList<AddOns> addOns = new ArrayList<>();
		addOns.add(AddOns.CHEESE);
		addOns.add(AddOns.TOMATOES);
		addOns.add(AddOns.AVOCADO);
		addOns.add(AddOns.ONIONS);

		int quantity = 2;

		boolean isDouble = true;

		Burger burger = new Burger(bread, addOns, quantity, isDouble);

		double expectedPrice = 0.0;
		if (isDouble) {
			expectedPrice = Protein.BEEF_PATTY.getPrice() + Burger.DOUBLE_PATTY_PRICE;
		} else {
			expectedPrice = Protein.BEEF_PATTY.getPrice();
		}

		for (AddOns addOn : addOns) {
			expectedPrice += addOn.getPrice();
		}

		expectedPrice = expectedPrice * quantity;


		assertEquals(expectedPrice, burger.price(), 0.01);
	}
}