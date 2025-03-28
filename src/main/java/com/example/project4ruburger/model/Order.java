package com.example.project4ruburger.model;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;


/**
 * @author Natalia Peguero, Olivia Kamau
 */
public class Order {
	private int number;		// unique # for each order
	private ArrayList<MenuItem> items;	// the list of food items in the order
	private double subPrice; 	// the total price of the order (without tax)

	private static final double NJ_TAX_RATE = 0.06625;
	private static int orderCount = 0;		// static global variable to update the number

	/**
	 *  Constructs an order object with a unique order number.
	 * @param number	The order number.
	 */
	public Order (int number) {
		this.number = number;
		this.items = new ArrayList<>();
		this.subPrice = 0.0;
	}

	/**
	 * Gets the unique order number for each order placed.
	 * @return	The unique order number.
	 */
	public int getOrderNumber() {
		return number;
	}

	/**
	 * Finalizes the current order and starts a new one.
	 * @return	A new order instance with the next order number.
	 */
	public Order startNewOrder() {
		orderCount++;
		return new Order(orderCount);
	}

	private void updatePrice() {
		subPrice = 0;
		for(MenuItem item : items) {
			subPrice += item.price();
		}
	}

	/**
	 * Adds the menu item to the order.
	 * @param item		The item to be added to the order.
	 */
	public void addItem(MenuItem item) {
		items.add(item);
		updatePrice();
	}

	/**
	 * Removes a menu item from the order by its index.
	 * @param index		The index of the item to be removed.
	 */
	public void removeItem(int index) {
		if(index >= 0 && index < items.size()) {
			items.remove(index);
			updatePrice();
		}
	}

	/**
	 * Gets the total price of the order (without tax)
	 * @return	Total price of all items in the order (without tax)
	 */
	public double getSubTotal() {
		return subPrice;
	}

	/**
	 * Gets the sales tax for the order based on the total price.
	 * @return	The sales tax amount for the order.
	 */
	public double getSalesTax() {
		return subPrice * NJ_TAX_RATE;
	}

	/**
	 * Calculates the final total price of the order (including sales tax)
	 * @return	Final total price of the order (+ sales tax)
	 */
	public double getTotalAmount() {
		return subPrice + getSalesTax();
	}

	/**
	 * Displays the order details for each order number.
	 * @return	A formatted string with the order details.
	 */
	public String displayOrderDetails() {
		StringBuilder orderDetails = new StringBuilder();

		for(MenuItem item : items) {
			orderDetails.append(item.toString()).append("\n");
		}

		return orderDetails.toString();
	}

	/**
	 * Exports the order details to a text file (using FileChooser)
	 * @param filename	The name of the file being written to.
	 */
	public void exportOrder(String filename) {

		try(FileWriter writer = new FileWriter(filename)){

			writer.write("Order #" + number + "\n");
			for(MenuItem item : items) {
				writer.write(item.toString() + "\n");
			}
			writer.write("Subtotal: $" + String.format("%.2f", getSubTotal()) + "\n");
			writer.write("Sales Tax: $" + String.format("%.2f", getSalesTax()) + "\n");
			writer.write("Total: $" + String.format("%.2f", getTotalAmount()) + "\n\n");

		} catch(IOException e) {
			e.printStackTrace();
		}

	}

	/* VERY IMPORTANT NOTE PLS DON'T FORGET PLS: FileWriter is for the model, FileChooser is
		for the controller, so don't use FileChooser here.
	 */

}
