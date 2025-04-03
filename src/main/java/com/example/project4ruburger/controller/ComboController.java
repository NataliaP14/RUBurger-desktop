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
public class ComboController {
	public Button cart;
	public Button orders;
	public ComboBox sideComboBox;
	public ImageView sideIcon;
	public ComboBox drinkComboBox;
	public ImageView drinkIcon;
	public Button minus;
	public ImageView minusIcon;
	public Label number;
	public Button plus;
	public ImageView plusIcon;
	public Label price;
	public Button addToOrder;
	public ImageView comboIcon;
	public Label sandwichDetails;
	@FXML private Button back;

	private int quantity;
	private Combo combo;
	private static final Size MEDIUM_DRINK = Size.MEDIUM;

	public void setCombo(Combo combo) {
		this.combo = combo;
		//initialize();
		priceUpdater();
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
		uploadIcons(drinkIcon, "Cola.png");
		uploadIcons(sideIcon, "Chips.png");
		uploadIcons(comboIcon, "Combo.png");

	}

	private void changeIcons() {
		Flavor flavor = (Flavor) drinkComboBox.getSelectionModel().getSelectedItem();
		Side side = (Side) sideComboBox.getSelectionModel().getSelectedItem();

		switch(flavor) {
			case COLA: uploadIcons(drinkIcon, "Cola.png"); break;
			case JUICE: uploadIcons(drinkIcon, "Juice.png"); break;
			case TEA: uploadIcons(drinkIcon, "Tea.png"); break;
		}

		switch(side) {
			case CHIPS: uploadIcons(sideIcon, "Chips.png"); break;
			case APPLE_SLICES: uploadIcons(sideIcon, "Apple.png"); break;
		}
	}


	private void setUpButtons() {
		drinkComboBox.setOnAction(e->{
			priceUpdater();
			changeIcons();
		});

		sideComboBox.setOnAction(e->{
			priceUpdater();
			changeIcons();
		});
		plus.setOnAction(this::increase);
		minus.setOnAction(this::decrease);

		addToOrder.setOnAction(this::addComboToOrder);
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
		Flavor flavor = (Flavor) drinkComboBox.getSelectionModel().getSelectedItem();
		Side side = (Side) sideComboBox.getSelectionModel().getSelectedItem();

		if (flavor == null || side == null || this.combo == null || this.combo.getSandwich() == null) { return; }

		Beverage drink = new Beverage(1, MEDIUM_DRINK, flavor);
		Combo combo = new Combo(quantity, this.combo.getSandwich(), drink, side);


		double total = combo.price();

		price.setText(String.format("Price: $%.2f", total));

	}

	@FXML
	private void addComboToOrder(ActionEvent event) {
		Sandwich sandwich = combo.getSandwich();
		Side side = (Side) sideComboBox.getValue();
		Flavor flavor = (Flavor) drinkComboBox.getValue();
		Beverage drink = new Beverage(1, MEDIUM_DRINK, flavor);

		Combo combo = new Combo(quantity, sandwich, drink, side);
		CurrentOrderController.getCurrentOrder().addItem(combo);

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

		priceUpdater();

		ObservableList<Flavor> drinks = FXCollections.observableArrayList(Flavor.COLA, Flavor.TEA, Flavor.JUICE);
		drinkComboBox.setItems(drinks);
		drinkComboBox.getSelectionModel().select(Flavor.COLA);

		ObservableList<Side> side = FXCollections.observableArrayList(Side.CHIPS, Side.APPLE_SLICES);
		sideComboBox.setItems(side);
		sideComboBox.getSelectionModel().select(Side.CHIPS);



	}


}
