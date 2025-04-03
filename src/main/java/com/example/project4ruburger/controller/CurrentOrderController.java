package com.example.project4ruburger.controller;

import com.example.project4ruburger.model.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * @author Natalia Peguero, Olivia Kamau
 */
public class CurrentOrderController {
	public Button cart;
	public Button orders;
	public TextArea placedOrderTextArea;
	@FXML
	private Button back;
	@FXML private Button cancelOrder, placedOrder;
	@FXML private Label subtotal, salesTax, totalAmount;

	private Order currentOrder;


	@FXML
	public void initialize() {
		currentOrder = new Order(1);
		updateOrderDisplay();
	}

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

	public void selectOrder(MouseEvent mouseEvent) {
	}

	public void cancelOrder(ActionEvent actionEvent) {
	}

	public void placeOrder(ActionEvent actionEvent) {
		currentOrder = new Order(currentOrder.getOrderNumber() + 1);
		updateOrderDisplay();
	}

	private void updateOrderDisplay() {
		placedOrderTextArea.setText(currentOrder.displayOrderDetails());
		subtotal.setText(String.format("Subtotal: $%.2f", currentOrder.getSubTotal()));
		salesTax.setText(String.format("Sales Tax: $%.2f", currentOrder.getSalesTax()));
		totalAmount.setText(String.format("Total: $%.2f", currentOrder.getTotalAmount()));
	}

}