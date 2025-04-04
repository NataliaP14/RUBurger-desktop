package com.example.project4ruburger.controller;

import com.example.project4ruburger.model.MenuItem;
import com.example.project4ruburger.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

import java.util.ArrayList;



/**
 * This controller class manages the viewing, cancelling and exporting placed orders.
 * It displays a list of all the placed orders, lets the user cancel them, export them and
 * updates their order selection.
 * @author Natalia Peguero, Olivia Kamau
 */
public class PlacedOrderController {
	public Button cart;
	public Button orders;
	public ImageView backIcon;
	@FXML private Button back, cancelOrder, exportOrder;
	@FXML private Label totalAmount;
	@FXML private ComboBox<Integer> orderDropdown;
	@FXML private ListView<MenuItem> orderDetails;

	private static ArrayList<Order> placedOrders = new ArrayList<>();


	/**
	 * Loads a new scene in the current stage
	 * @param file	the FXML file name
	 * @param title		the title for the scene window
	 */
	private void loadScene(String file, String title) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project4ruburger/" + file));
			Parent root = loader.load();
			Stage stage = (Stage) back.getScene().getWindow();
			stage.setScene(new Scene(root, 950, 800));
			stage.setTitle(title);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Navigates back to the main menu view
	 * @param actionEvent	the action event
	 */
	@FXML
	private void backToMainMenu(ActionEvent actionEvent) {
		loadScene("Main-view.fxml", "RU Burger - Main Menu");
	}

	/**
	 * Navigates to the cart view
	 * @param actionEvent	the action event
	 */
	@FXML
	private void goToCart(ActionEvent actionEvent) {
		loadScene("CurrentOrder-view.fxml", "RU Burger - Cart");
	}

	/**
	 * Navigates to the placed orders view
	 * @param actionEvent	the action event
	 */
	@FXML
	private void goToOrders(ActionEvent actionEvent) {
		loadScene("PlacedOrder-view.fxml", "RU Burger - Orders");
	}


	/**
	 * Adds an order to the placed orders list
	 * @param order		the order to be added to the list.
	 */
	@FXML
	public static void addPlacedOrder(Order order) {
		placedOrders.add(order);
	}


	/**
	 * Updates the order dropdown with the available order numbers
	 */
	@FXML
	private void updateOrderDropdown() {
		ObservableList<Integer> orderNumbers = FXCollections.observableArrayList();

		for(Order order : placedOrders) {
			orderNumbers.add(order.getOrderNumber());
		}
		orderDropdown.setItems(orderNumbers);

		if(!orderNumbers.isEmpty()) {
			orderDropdown.setValue(orderNumbers.get(0));
			displaySelectedOrder();
		} else {
			orderDetails.setItems(FXCollections.observableArrayList());
			totalAmount.setText("Total: $0.00");
		}
	}


	/**
	 * Displays the currently selected order + the items ordered.
	 */
	@FXML
	private void displaySelectedOrder() {
		Integer selectedOrderNumber = orderDropdown.getValue();
		if (selectedOrderNumber != null) {
			Order selectedOrder = findOrderByNumber(selectedOrderNumber);

			if (selectedOrder != null) {
				ObservableList<MenuItem> items = FXCollections.observableArrayList(selectedOrder.getItems());
				orderDetails.setItems(items);
				totalAmount.setText(String.format("Total: $%.2f", selectedOrder.getTotalAmount()));
			}
		}
	}


	/**
	 * Finds an order by its respective order number
	 * @param orderNumber	the order that that you are searching for
	 * @return		Returns the order number if it's found,
	 * 				otherwise returns null.
	 */
	@FXML
	private Order findOrderByNumber(int orderNumber) {
		for(Order order : placedOrders) {
			if (order.getOrderNumber() == orderNumber) {
				return order;
			}
		}
		return null;
	}


	/**
	 * Cancels the order when the user click the "Cancel Order" button
	 * @param event		the action event
	 */
	@FXML
	private void handleCancelOrder(ActionEvent event) {
		Integer selectedOrderNumber = orderDropdown.getValue();
		if (selectedOrderNumber != null) {

			for(int i = 0; i < placedOrders.size(); i++) {
				if (placedOrders.get(i).getOrderNumber() == selectedOrderNumber) {
					placedOrders.remove(i);
					break;
				}
			}
			updateOrderDropdown();

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Order Cancelled");
			alert.setHeaderText(null);
			alert.setContentText("Your order has been cancelled!");
			alert.showAndWait();
		}
	}


	/**
	 * Exports the order when the user clicks the "Export Order" button.
	 * @param event		the action event.
	 */
	@FXML
	private void handleExportOrder(ActionEvent event) {
		if(placedOrders.isEmpty()) {
			return;
		}

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save All Orders");
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("Text Files", "*.txt")
		);
		fileChooser.setInitialFileName("RU_Burger_Orders.txt");


		Stage stage = (Stage) exportOrder.getScene().getWindow();
		File file = fileChooser.showSaveDialog(stage);

		if (file != null) {

			try (FileWriter writer = new FileWriter(file)) {

				writer.write("RU Burger Orders: \n");
				writer.write("-------------------\n");

				for(Order order : placedOrders) {
					writer.write("Order #" + order.getOrderNumber() + "\n");
					writer.write("\tItems:\n");

					for(MenuItem item : order.getItems()) {
						writer.write("\t- " + item.toString() + "\n");
					}

					writer.write("\n\tSubtotal: $" + String.format("%.2f", order.getSubTotal()));
					writer.write("\n\tSales Tax: $" + String.format("%.2f", order.getSalesTax()));
					writer.write("\n\tTotal: $" + String.format("%.2f", order.getTotalAmount()));
					writer.write("\n\n\n");

				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Orders Exported");
			alert.setHeaderText(null);
			alert.setContentText("All orders have been exported!");
			alert.showAndWait();

		}
	}

	/**
	 * Initializes the controller
	 */
	@FXML
	public void initialize() {
		updateOrderDropdown();

		orderDropdown.setOnAction(event -> {displaySelectedOrder();});

		cancelOrder.setOnAction(this::handleCancelOrder);
		exportOrder.setOnAction(this::handleExportOrder);
	}

}