package com.example.project4ruburger.controller;

import com.example.project4ruburger.model.Bread;
import com.example.project4ruburger.model.Burger;
import com.example.project4ruburger.model.Beverage;
import com.example.project4ruburger.model.Side;
import com.example.project4ruburger.model.Sandwich;
import com.example.project4ruburger.model.Combo;
import com.example.project4ruburger.model.AddOns;
import com.example.project4ruburger.model.Size;
import com.example.project4ruburger.model.Flavor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image;


import java.io.IOException;
import java.util.ArrayList;

/**
 * The burger controller handles all the actions of the burger page, which creates the burger
 * based off the user customization, users additionally have the option to change it to a combo
 * @author Natalia Peguero, Olivia Kamau
 */
public class BurgerController {
	@FXML private CheckBox lettuce;
	@FXML private CheckBox tomato;
	@FXML private CheckBox onion;
	@FXML private CheckBox avocado;
	@FXML private CheckBox cheese;
	@FXML private VBox mainBackground;
	@FXML private ImageView backIcon;
	@FXML private RadioButton singlePatty;
	@FXML private RadioButton doublePatty;
	@FXML private RadioButton briocheBread;
	@FXML private RadioButton wheatBread;
	@FXML private RadioButton pretzelBread;
	@FXML private ImageView burgerIcon;
	@FXML private Button addToOrder;
	@FXML private Label price;
	@FXML private Label number;
	@FXML private Button plus;
	@FXML private ImageView plusIcon;
	@FXML private Button minus;
	@FXML private ImageView minusIcon;
	@FXML private Button back;

	private ToggleGroup patty = new ToggleGroup();
	private ToggleGroup bread = new ToggleGroup();
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
	 * Loads the new scene and switches to the combo view
	 * @param combo the combo that was created to switch to the view
	 */
	private void loadComboScene(Combo combo) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project4ruburger/Combo-view.fxml"));
			Parent root = loader.load();

			ComboController controller = loader.getController();
			controller.setCombo(combo);
			Sandwich burger = combo.getSandwich();
			if (burger != null) { controller.sandwichDetails.setText(burger.toString());}
			Stage stage = (Stage) back.getScene().getWindow();
			stage.setScene(new Scene(root, 950, 800));
			stage.setTitle("RU Burger - Combo");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new combo object and calls the method to load into the combo view
	 * @param actionEvent event handler
	 */
	@FXML
	private void goToCombo(ActionEvent actionEvent) {
		boolean isDouble = doublePatty.isSelected();

		Bread bread = breadSelect();

		ArrayList<AddOns> addOns = addOnsSelect();

		Burger burger = new Burger(bread, addOns, quantity, isDouble);

		Beverage drink = new Beverage(1, Size.MEDIUM, Flavor.COLA);

		Side side = Side.CHIPS;

		Combo combo = new Combo(quantity, burger, drink, side);
		loadComboScene(combo);
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
		uploadIcons(burgerIcon, "Burger.png");
		uploadIcons(backIcon, "Left.png");
	}

	/**
	 * Sets up the toggles from the xml file
	 */
	private void setUpToggle() {
		singlePatty.setToggleGroup(patty);
		doublePatty.setToggleGroup(patty);

		briocheBread.setToggleGroup(bread);
		wheatBread.setToggleGroup(bread);
		pretzelBread.setToggleGroup(bread);
	}

	/**
	 * Sets up the events for all the buttons
	 */
	private void setUpButtons() {
		singlePatty.setOnAction(e -> priceUpdater());
		doublePatty.setOnAction(e -> priceUpdater());

		lettuce.setOnAction(e -> priceUpdater());
		tomato.setOnAction(e -> priceUpdater());
		onion.setOnAction(e -> priceUpdater());
		avocado.setOnAction(e -> priceUpdater());
		cheese.setOnAction(e -> priceUpdater());

		plus.setOnAction(this::increase);
		minus.setOnAction(this::decrease);

		addToOrder.setOnAction(this::addBurgerToOrder);
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
	 * Adds the corresponding bread type based off the user selection
	 * @return returns the bread selected
	 */
	private Bread breadSelect() {
		Bread bread = null;
		if (briocheBread.isSelected()) {
			bread = Bread.BRIOCHE;
		} else if (wheatBread.isSelected()) {
			bread = Bread.WHEAT_BREAD;
		} else if (pretzelBread.isSelected()) {
			bread = Bread.PRETZEL;
		}
		return bread;
	}

	/**
	 * Adds the user addOns to the array lst
	 * @return returns the addOns
	 */
	private ArrayList<AddOns> addOnsSelect() {
		ArrayList<AddOns> addOns = new ArrayList<>();

		if (lettuce.isSelected()) {
			addOns.add(AddOns.LETTUCE);
		} if (tomato.isSelected()) {
			addOns.add(AddOns.TOMATOES);
		} if (onion.isSelected()) {
			addOns.add(AddOns.ONIONS) ;
		} if (avocado.isSelected()) {
			addOns.add(AddOns.AVOCADO);
		} if (cheese.isSelected()) {
			addOns.add(AddOns.CHEESE);
		}

		return addOns;
	}

	/**
	 * Dynamically updates the price of the item
	 */
	private void priceUpdater() {

		boolean isDouble = doublePatty.isSelected();
		Bread bread = breadSelect();
		ArrayList<AddOns> addOns = addOnsSelect();


		Burger burger = new Burger(bread, addOns, quantity, isDouble);

		double total = burger.price();

		price.setText(String.format("Price: $%.2f", total));

	}

	/**
	 * Adds the burger to the order in current order
	 * @param event action event handler
	 */
	@FXML
	private void addBurgerToOrder(ActionEvent event) {
		boolean isDouble = doublePatty.isSelected();
		Bread bread = breadSelect();
		ArrayList<AddOns> addOns = addOnsSelect();


		Burger burger = new Burger(bread, addOns, quantity, isDouble);
		CurrentOrderController.getCurrentOrder().addItem(burger);

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Item Added");
		alert.setHeaderText(null);
		alert.setContentText("Burger added to your order!");
		alert.showAndWait();
	}


	/**
	 * initializes the data below when the program runs
	 */
	@FXML
	public void initialize() {
		setUpIcons();
		setUpToggle();
		setUpButtons();

		quantity = 1;
		updateQuantity();

		String imagePath = getClass().getResource("/image/brownBackground.jpg").toExternalForm();
		mainBackground.setStyle("-fx-background-image: url('" + imagePath + "'); " +
				"-fx-background-size: cover; " +
				"-fx-background-position: center;");

	}
}
