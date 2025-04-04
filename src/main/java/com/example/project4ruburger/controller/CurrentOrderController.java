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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;



/**
 * This controller class manages the Current Order view in the application.
 * It displays the current order, lets the user delete order items and allows
 * the user to place their order.
 * @author Natalia Peguero, Olivia Kamau
 */
public class CurrentOrderController {
	public Button cart;
	public Button orders;
	public ImageView backIcon;
	public VBox mainBackground;
	@FXML private Button back;
	@FXML private Button removeOrderItem, placeOrder;
	@FXML private Label subtotal, salesTax, totalAmount;
	@FXML private ListView<MenuItem> orderItemsListView;

	private static Order currentOrder = null;


	/**
	 * Static method to get or create the current order
	 * @return	the current order
	 */
	public static Order getCurrentOrder() {
		if (currentOrder == null) {
			currentOrder = new Order(1);
		}
		return currentOrder;
	}

	/**
	 * Static method to set the current order
	 * @param order	the current order
	 */
	public static void setCurrentOrder(Order order) {
		currentOrder = order;
	}



	/**
	 * Loads a new scene in the current stage
	 * @param file	the FXML file name
	 * @param title	the title of the scene window
	 */
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

	/**
	 * Method to return to the main menu
	 * @param actionEvent	the action event
	 */
	@FXML
	private void backToMainMenu(ActionEvent actionEvent) {
		loadScene("Main-view.fxml", "RU Burger - Main Menu");
	}

	/**
	 * Method to go to the cart view
	 * @param actionEvent	the action event
	 */
	@FXML
	private void goToCart(ActionEvent actionEvent) {
		loadScene("CurrentOrder-view.fxml", "RU Burger - Cart");
	}

	/**
	 * Method to go to the orders view
	 * @param actionEvent	the action event
	 */
	@FXML
	private void goToOrders(ActionEvent actionEvent) {
		loadScene("PlacedOrder-view.fxml", "RU Burger - Orders");
	}



	@FXML
	private void uploadIcons(ImageView view, String file) {
		String imagePath = getClass().getResource("/image/" + file).toExternalForm();
		view.setImage(new Image(imagePath));
	}

	private void setUpIcons() {

		uploadIcons(backIcon, "Left.png");

	}


	/**
	 * Updates the current order display and the totals in the ListView.
	 */
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

		subtotal.setStyle("-fx-text-fill:white; -fx-font-family: 'Impact'; -fx-font-size: 16px;");
		salesTax.setStyle("-fx-text-fill:white; -fx-font-family: 'Impact'; -fx-font-size: 16px;");
		totalAmount.setStyle("-fx-text-fill:white; -fx-font-family: 'Impact'; -fx-font-size: 16px;");

	}



	/**
	 * Removes the selected item from the order
	 * @param actionEvent	the action event
	 */
	public void removeOrderItem(ActionEvent actionEvent) {
		MenuItem selectedItem = orderItemsListView.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			int selectedIndex = orderItemsListView.getSelectionModel().getSelectedIndex();
			currentOrder.removeItem(selectedIndex);
			updateOrderDisplay();
		}
	}

	/**
	 * Places the users current order
	 * @param actionEvent	the action event
	 */
	public void placeOrder(ActionEvent actionEvent) {


		if(currentOrder != null && !currentOrder.getItems().isEmpty()) {
			PlacedOrderController.addPlacedOrder(currentOrder);

			currentOrder = new Order(currentOrder.getOrderNumber() + 1);
			updateOrderDisplay();

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Order Placed");
			alert.setHeaderText(null);
			alert.setContentText("Your order has been placed!");
			alert.showAndWait();
		}
	}



	/**
	 * Initializes the controller
	 */
	@FXML
	public void initialize() {
		setUpIcons();
		if (currentOrder == null) {
			currentOrder = new Order(1);
		}
		updateOrderDisplay();

		String imagePath = getClass().getResource("/image/brownBackground.jpg").toExternalForm();
		mainBackground.setStyle("-fx-background-image: url('" + imagePath + "'); " +
				"-fx-background-size: cover; " +
				"-fx-background-position: center;");

	}



}