package com.example.project4ruburger.controller;

import com.example.project4ruburger.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Natalia Peguero, Olivia Kamau
 */
public class SideController {
	public Button cart;
	public Button orders;
	public ImageView sideIcon;
	public ComboBox sideComboBox;
	public ComboBox sizeComboBox;
	public Button minus;
	public ImageView minusIcon;
	public Label number;
	public Button plus;
	public ImageView plusIcon;
	public Label price;
	public Button addToOrder;
	@FXML private Button back;

	private int quantity;


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
		loadScene("PlacedOrder-view.fxml", "RU Burger - Orders");
	}

	@FXML
	private void uploadIcons(ImageView view, String file) {
		String imagePath = getClass().getResource("/image/" + file).toExternalForm();
		view.setImage(new Image(imagePath));
	}

	private void setUpIcons() {
		minus.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		plus.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

		uploadIcons(minusIcon, "Minus.png");
		uploadIcons(plusIcon, "Plus.png");
		uploadIcons(sideIcon, "Fries.png");
	}

	private void setUpButtons() {
		sizeComboBox.setOnAction(e->priceUpdater());
		sideComboBox.setOnAction(e->priceUpdater());
		plus.setOnAction(this::increase);
		minus.setOnAction(this::decrease);

		addToOrder.setOnAction(this::addSideToOrder);
	}

	private void increase(ActionEvent actionEvent) {
		quantity++;
		updateQuantity();
	}

	private void decrease(ActionEvent actionEvent) {
		if (quantity > 1) {
			quantity--;
		}
		updateQuantity();
	}

	private void updateQuantity() {
		number.setText(String.valueOf(quantity));
		priceUpdater();
	}

	private void priceUpdater() {
		Size size = (Size) sizeComboBox.getSelectionModel().getSelectedItem();
		Side side = (Side) sideComboBox.getSelectionModel().getSelectedItem();

		if (size == null || side == null) { return; }

		Sides sides = new Sides(quantity, side, size);
		double total = sides.price();

		price.setText(String.format("Price: $%.2f", total));

	}

	@FXML
	private void addSideToOrder(ActionEvent event) {
		Side side = (Side) sideComboBox.getValue();
		Size size = (Size) sizeComboBox.getValue();

		Sides sides = new Sides(quantity, side, size);
		CurrentOrderController.getCurrentOrder().addItem(sides);

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Item Added");
		alert.setHeaderText(null);
		alert.setContentText("Side added to your order!");
		alert.showAndWait();
	}

	@FXML
	public void initialize() {
		setUpIcons();
		setUpButtons();

		quantity = 1;
		updateQuantity();

		ObservableList<Size> sizes = FXCollections.observableArrayList(Size.values());
		sizeComboBox.setItems(sizes);
		sizeComboBox.getSelectionModel().selectFirst();

		ObservableList<Side> side = FXCollections.observableArrayList(Side.values());
		sideComboBox.setItems(side);
		sideComboBox.getSelectionModel().selectFirst();

		priceUpdater();

	}
}
