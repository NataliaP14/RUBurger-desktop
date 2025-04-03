package com.example.project4ruburger.controller;

import com.example.project4ruburger.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Natalia Peguero, Olivia Kamau
 */
public class BeverageController {
	public Button cart;
	public Button orders;
	public ImageView beverageIcon;
	public ComboBox flavorComboBox;
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
		uploadIcons(beverageIcon, "Cola.png");
	}

	private void setUpButtons() {
		sizeComboBox.setOnAction(e->priceUpdater());
		plus.setOnAction(this::increase);
		minus.setOnAction(this::decrease);

		addToOrder.setOnAction(this::addBeverageToOrder);
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

		Flavor flavor = (Flavor) flavorComboBox.getSelectionModel().getSelectedItem();

		if (size == null || flavor == null) {
			return;
		}

		Beverage beverage = new Beverage(quantity, size, flavor);
		double total = beverage.price();

		price.setText(String.format("Price: $%.2f", total));

	}

	@FXML
	private void addBeverageToOrder(ActionEvent event) {
		Flavor flavor = (Flavor) flavorComboBox.getValue();
		Size size = (Size) sizeComboBox.getValue();

		Beverage beverage = new Beverage(quantity, size, flavor);
		CurrentOrderController.getCurrentOrder().addItem(beverage);

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Item Added");
		alert.setHeaderText(null);
		alert.setContentText("Beverage added to your order!");
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
		sizeComboBox.getSelectionModel().select(Size.MEDIUM);



		ObservableList<Flavor> flavors = FXCollections.observableArrayList(Flavor.values());
		flavorComboBox.setItems(flavors);
		flavorComboBox.getSelectionModel().selectFirst();

		priceUpdater();

	}
}
