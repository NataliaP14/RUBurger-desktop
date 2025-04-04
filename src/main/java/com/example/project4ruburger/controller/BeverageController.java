package com.example.project4ruburger.controller;


import com.example.project4ruburger.model.Beverage;
import com.example.project4ruburger.model.Size;
import com.example.project4ruburger.model.Flavor;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The beverage controller handles all the actions of the beverage page, which creates the beverage
 * based off the user customization
 * @author Natalia Peguero, Olivia Kamau
 */
public class BeverageController {
	@FXML private ImageView beverageIcon;
	@FXML private ComboBox flavorComboBox;
	@FXML private ComboBox sizeComboBox;
	@FXML private Button minus;
	@FXML private ImageView minusIcon;
	@FXML private Label number;
	@FXML private Button plus;
	@FXML private ImageView plusIcon;
	@FXML private Label price;
	@FXML private Button addToOrder;
	@FXML private VBox mainBackground;
	@FXML private ImageView backIcon;
	@FXML private Button back;

	private int quantity;

	/**
	 * Loads a new scene based on the corresponding view
	 * @param file the view file to switch to
	 * @param title the title name to set based on the view
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
	 * Switches back to main menu
	 * @param actionEvent event handler
	 */
	@FXML
	private void backToMainMenu(ActionEvent actionEvent) {
		loadScene("Main-view.fxml", "RU Burger - Main Menu");
	}

	/**
	 * Switches to the currentOrder view
	 * @param actionEvent event handler
	 */
	@FXML
	private void goToCart(ActionEvent actionEvent) {
		loadScene("CurrentOrder-view.fxml", "RU Burger - Cart");
	}

	/**
	 * Switches to the PlacedOrder view
	 * @param actionEvent event handler
	 */
	@FXML
	private void goToOrders(ActionEvent actionEvent) {
		loadScene("PlacedOrder-view.fxml", "RU Burger - Orders");
	}

	/**
	 * Uploads the icons to the view
	 * @param view the view to upload to
	 * @param file the file of the icon
	 */
	@FXML
	private void uploadIcons(ImageView view, String file) {
		String imagePath = getClass().getResource("/image/" + file).toExternalForm();
		view.setImage(new Image(imagePath));
	}

	/**
	 * Sets up the icons in the xml file
	 */
	private void setUpIcons() {
		minus.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		plus.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

		uploadIcons(minusIcon, "Minus.png");
		uploadIcons(plusIcon, "Plus.png");
		uploadIcons(beverageIcon, "Beverages.png");
		uploadIcons(backIcon, "Left.png");
	}

	/**
	 * Sets up the events for all the buttons
	 */
	private void setUpButtons() {
		sizeComboBox.setOnAction(e->priceUpdater());
		plus.setOnAction(this::increase);
		minus.setOnAction(this::decrease);

		addToOrder.setOnAction(this::addBeverageToOrder);

		flavorComboBox.setOnMouseEntered(e -> {
			ListCell<?> buttonCell = flavorComboBox.getButtonCell();
			if (buttonCell != null) {
				buttonCell.setStyle("-fx-text-fill: white; -fx-background-color: #6e0512;");
			}
		});

		flavorComboBox.setOnMouseExited(e -> {
			ListCell<?> buttonCell = flavorComboBox.getButtonCell();
			if (buttonCell != null) {
				buttonCell.setStyle("-fx-text-fill: black; -fx-background-color: transparent;");
			}
		});

		sizeComboBox.setOnMouseEntered(e -> {
			ListCell<?> buttonCell = sizeComboBox.getButtonCell();
			if (buttonCell != null) {
				buttonCell.setStyle("-fx-text-fill: white; -fx-background-color: #6e0512;");
			}
		});

		sizeComboBox.setOnMouseExited(e -> {
			ListCell<?> buttonCell = sizeComboBox.getButtonCell();
			if (buttonCell != null) {
				buttonCell.setStyle("-fx-text-fill: black; -fx-background-color: transparent;");
			}
		});

	}

	/**
	 * Increases the quantity of the item
	 * @param actionEvent event handler
	 */
	private void increase(ActionEvent actionEvent) {
		quantity++;
		updateQuantity();
	}

	/**
	 * Decreases the quantity of the item
	 * @param actionEvent event handler
	 */
	private void decrease(ActionEvent actionEvent) {
		if (quantity > 1) {
			quantity--;
		}
		updateQuantity();
	}

	/**
	 * Dynamically updates the quantity of item
	 */
	private void updateQuantity() {
		number.setText(String.valueOf(quantity));
		priceUpdater();
	}

	/**
	 * Updates the price of the beverage and constructs a beverage object, that dynamically updates the price
	 */
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

	/**
	 * Adds the beverage to the order and sets the alert when a user clicks the add to order button
	 * @param event event handler
	 */
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

	/**
	 * initialization function that initializes functions and other data when the program runs
	 */
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

		String imagePath = getClass().getResource("/image/brownBackground.jpg").toExternalForm();
		mainBackground.setStyle("-fx-background-image: url('" + imagePath + "'); " +
				"-fx-background-size: cover; " +
				"-fx-background-position: center;");

	}
}
