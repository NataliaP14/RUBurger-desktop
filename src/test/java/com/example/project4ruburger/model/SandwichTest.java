package com.example.project4ruburger.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SandwichTest {

	@Test
	public void roastBeefSandwichTest() {
		Bread bread = Bread.WHEAT_BREAD;

		Protein protein = Protein.ROAST_BEEF;

		ArrayList<AddOns> addOns = new ArrayList<>();
		addOns.add(AddOns.CHEESE);
		addOns.add(AddOns.TOMATOES);

		int quantity = 3;

		Sandwich sandwich = new Sandwich(quantity, bread, protein, addOns);

		double expectedPrice = protein.getPrice();

		for (AddOns addOn : addOns) {
			expectedPrice += addOn.getPrice();
		}

		expectedPrice = expectedPrice * quantity;
		assertEquals(expectedPrice, sandwich.price(), 0.01);
	}

	@Test
	public void salmonSandwichTest() {
		Bread bread = Bread.BAGEL;

		Protein protein = Protein.SALMON;

		ArrayList<AddOns> addOns = new ArrayList<>();
		addOns.add(AddOns.TOMATOES);
		addOns.add(AddOns.ONIONS);

		int quantity = 1;

		Sandwich sandwich = new Sandwich(quantity, bread, protein, addOns);

		double expectedPrice = protein.getPrice();

		for (AddOns addOn : addOns) {
			expectedPrice += addOn.getPrice();
		}

		expectedPrice = expectedPrice * quantity;
		assertEquals(expectedPrice, sandwich.price(), 0.01);
	}

	@Test
	public void chickenSandwichTest() {
		Bread bread = Bread.SOURDOUGH;

		Protein protein = Protein.CHICKEN;

		ArrayList<AddOns> addOns = new ArrayList<>();
		addOns.add(AddOns.AVOCADO);
		addOns.add(AddOns.CHEESE);
		addOns.add(AddOns.LETTUCE);

		int quantity = 5;

		Sandwich sandwich = new Sandwich(quantity, bread, protein, addOns);

		double expectedPrice = protein.getPrice();

		for (AddOns addOn : addOns) {
			expectedPrice += addOn.getPrice();
		}

		expectedPrice = expectedPrice * quantity;
		assertEquals(expectedPrice, sandwich.price(), 0.01);
	}
}