package com.example.project4ruburger.controller;

import com.example.project4ruburger.model.Beverage;
import com.example.project4ruburger.model.Side;
import com.example.project4ruburger.model.Sandwich;
import com.example.project4ruburger.model.Combo;
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

/**
 *  The combo controller handles all the actions of the combo page, which creates the combo
 *  * based off the user customization
 * @author Natalia Peguero, Olivia Kamau
 */
public class ComboController {
	@FXML private ComboBox sidesComboBox;
	@FXML private ImageView sideIcon;
	@FXML private ComboBox drinkComboBox;
	@FXML private ImageView drinkIcon;
	@FXML private Button minus;
	@FXML private ImageView minusIcon;
	@FXML private Label number;
	@FXML private Button plus;
	@FXML private ImageView plusIcon;
	@FXML private Label price;
	@FXML private Button addToOrder;
	@FXML private ImageView comboIcon;
	@FXML private VBox mainBackground;
	@FXML private ImageView backIcon;
	@FXML private Button back;


	private int quantity;
	private Combo combo;
	private static final Size MEDIUM_DRINK = Size.MEDIUM;

	public Label sandwichDetails;

	/**
	 * Sets the combo to the corresponding combo based off sandwich or burger
	 * @param combo the combo to set
	 */
	public void setCombo(Combo combo) {
		this.combo = combo;
		priceUpdater();
	}

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
	 * Sets up the icons to the view and sets the style of the minus and plus button
	 */
	private void setUpIcons() {
		minus.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		plus.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

		uploadIcons(minusIcon, "Minus.png");
		uploadIcons(plusIcon, "Plus.png");
		uploadIcons(drinkIcon, "Cola.png");
		uploadIcons(sideIcon, "Chips.png");
		uploadIcons(comboIcon, "Combo.png");
		uploadIcons(backIcon, "Left.png");

	}

	/**
	 * Dynamically changes the icons of the flavor and side based off user selection
	 */
	private void changeIcons() {
		Flavor flavor = (Flavor) drinkComboBox.getSelectionModel().getSelectedItem();
		Side side = (Side) sidesComboBox.getSelectionModel().getSelectedItem();

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

	/**
	 * Sets up the buttons by setting the action
	 */
	private void setUpButtons() {
		drinkComboBox.setOnAction(e->{
			priceUpdater();
			changeIcons();
		});

		sidesComboBox.setOnAction(e->{
			priceUpdater();
			changeIcons();
		});
		plus.setOnAction(this::increase);
		minus.setOnAction(this::decrease);

		addToOrder.setOnAction(this::addComboToOrder);

		drinkComboBox.setOnMouseEntered(e -> {
			ListCell<?> buttonCell = drinkComboBox.getButtonCell();
			if (buttonCell != null) {
				buttonCell.setStyle("-fx-text-fill: white; -fx-background-color: #6e0512;");
			}
		});

		drinkComboBox.setOnMouseExited(e -> {
			ListCell<?> buttonCell = drinkComboBox.getButtonCell();
			if (buttonCell != null) {
				buttonCell.setStyle("-fx-text-fill: black; -fx-background-color: transparent;");
			}
		});

		sidesComboBox.setOnMouseEntered(e -> {
			ListCell<?> buttonCell = sidesComboBox.getButtonCell();
			if (buttonCell != null) {
				buttonCell.setStyle("-fx-text-fill: white; -fx-background-color: #6e0512;");
			}
		});

		sidesComboBox.setOnMouseExited(e -> {
			ListCell<?> buttonCell = sidesComboBox.getButtonCell();
			if (buttonCell != null) {
				buttonCell.setStyle("-fx-text-fill: black; -fx-background-color: transparent;");
			}
		});
	}

	/**
	 * Increase the quantity of item
	 * @param actionEvent event handler
	 */
	private void increase(ActionEvent actionEvent) {
		quantity++;
		updateQuantity();
	}

	/**
	 * Decrease the quantity of the item
	 * @param actionEvent event handler
	 */
	private void decrease(ActionEvent actionEvent) {
		if (quantity > 1) {
			quantity--;
		}
		updateQuantity();
	}

	/**
	 * Dynamically updates the quantity of the item
	 */
	private void updateQuantity() {
		number.setText(String.valueOf(quantity));
		priceUpdater();
	}

	/**
	 * Creates the combo object to dynamically update the price of the combo
	 */
	private void priceUpdater() {
		Flavor flavor = (Flavor) drinkComboBox.getSelectionModel().getSelectedItem();
		Side side = (Side) sidesComboBox.getSelectionModel().getSelectedItem();

		if (flavor == null || side == null || this.combo == null || this.combo.getSandwich() == null) { return; }

		Beverage drink = new Beverage(1, MEDIUM_DRINK, flavor);
		Combo combo = new Combo(quantity, this.combo.getSandwich(), drink, side);


		double total = combo.price();

		price.setText(String.format("Price: $%.2f", total));

	}

	/**
	 * Adds the combo to current order and sets the alert when user clicks place order button
	 * @param event event handler
	 */
	@FXML
	private void addComboToOrder(ActionEvent event) {
		Sandwich sandwich = combo.getSandwich();
		Side side = (Side) sidesComboBox.getValue();
		Flavor flavor = (Flavor) drinkComboBox.getValue();
		Beverage drink = new Beverage(1, MEDIUM_DRINK, flavor);

		Combo combo = new Combo(quantity, sandwich, drink, side);
		CurrentOrderController.getCurrentOrder().addItem(combo);

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Item Added");
		alert.setHeaderText(null);
		alert.setContentText("Combo added to your order!");
		alert.showAndWait();
	}

	/**
	 * initialization function that sets up the data and functions when the program runs
	 */
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
		sidesComboBox.setItems(side);
		sidesComboBox.getSelectionModel().select(Side.CHIPS);

		String imagePath = getClass().getResource("/image/brownBackground.jpg").toExternalForm();
		mainBackground.setStyle("-fx-background-image: url('" + imagePath + "'); " +
				"-fx-background-size: cover; " +
				"-fx-background-position: center;");

	}

}
