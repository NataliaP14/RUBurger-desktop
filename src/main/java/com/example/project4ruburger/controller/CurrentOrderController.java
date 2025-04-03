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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Natalia Peguero, Olivia Kamau
 */
public class CurrentOrderController {
	public Button cart;
	public Button orders;
	@FXML private Button back;
	@FXML private Button removeOrderItem, placedOrder;
	@FXML private Label subtotal, salesTax, totalAmount;
	@FXML private ListView<MenuItem> orderItemsListView;

	private static Order currentOrder = null;


	public static Order getCurrentOrder() {
		if (currentOrder == null) {
			currentOrder = new Order(1);
		}
		return currentOrder;
	}

	public static void setCurrentOrder(Order order) {
		currentOrder = order;
	}


	@FXML
	public void initialize() {
		if (currentOrder == null) {
			currentOrder = new Order(1);
		}
		updateOrderDisplay();
	}

	private void loadScene(String file, String title) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project4ruburger/" + file));
			Parent root = loader.load();

			Object controller = loader.getController();
			if (controller instanceof CurrentOrderController) { }

			Stage stage = (Stage) back.getScene().getWindow();
			stage.setScene(new Scene(root, 950, 800));
			stage.setTitle(title);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void backToMainMenu(ActionEvent actionEvent) {
		loadScene("Main-view.fxml", "RU Burger - Main Menu");
	}

	@FXML
	private void goToCart(ActionEvent actionEvent) {
		loadScene("CurrentOrder-view.fxml", "RU Burger - Cart");
	}

	@FXML
	private void goToOrders(ActionEvent actionEvent) {
		loadScene("CurrentOrder-view.fxml", "RU Burger - Orders");
	}

	/*public void selectOrder(MouseEvent mouseEvent) {
	} */

	/*public void cancelOrder(ActionEvent actionEvent) {
		currentOrder = new Order(currentOrder.getOrderNumber());
		updateOrderDisplay();
	}*/

	public void removeOrderItem(ActionEvent actionEvent) {
		MenuItem selectedItem = orderItemsListView.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			int selectedIndex = orderItemsListView.getSelectionModel().getSelectedIndex();
			currentOrder.removeItem(selectedIndex);
			updateOrderDisplay();
		}
	}

	public void placeOrder(ActionEvent actionEvent) {
		currentOrder = new Order(currentOrder.getOrderNumber() + 1);
		updateOrderDisplay();
	}

	private void updateOrderDisplay() {
		if(currentOrder != null) {
			ObservableList<MenuItem> itemsList = FXCollections.observableArrayList(currentOrder.getItems());
			orderItemsListView.setItems(itemsList);

			subtotal.setText(String.format("Subtotal: $%.2f", currentOrder.getSubTotal()));
			salesTax.setText(String.format("Sales Tax: $%.2f", currentOrder.getSalesTax()));
			totalAmount.setText(String.format("Total: $%.2f", currentOrder.getTotalAmount()));
		} else {
			orderItemsListView.setItems(FXCollections.observableArrayList());
			subtotal.setText("Subtotal: $0.00");
			salesTax.setText("Sales Tax: $0.00");
			totalAmount.setText("Total: $0.00");
		}

	}



}