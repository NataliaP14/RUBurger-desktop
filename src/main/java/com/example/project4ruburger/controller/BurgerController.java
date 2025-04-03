package com.example.project4ruburger.controller;

import com.example.project4ruburger.model.*;
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
 * @author Natalia Peguero, Olivia Kamau
 */
public class BurgerController {
	public Button cart;
	public Button orders;
	public CheckBox lettuce;
	public CheckBox tomato;
	public CheckBox onion;
	public CheckBox avocado;
	public CheckBox cheese;
	public VBox mainBackground;
	public ImageView backIcon;
	@FXML private RadioButton singlePatty, doublePatty;
	@FXML private RadioButton briocheBread, wheatBread, pretzelBread;
	@FXML private Rectangle rectangle;
	@FXML ImageView burgerIcon;
	@FXML private Button combo;
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
		uploadIcons(burgerIcon, "Burger.png");
		uploadIcons(backIcon, "Left.png");
	}

	private void setUpToggle() {
		singlePatty.setToggleGroup(patty);
		doublePatty.setToggleGroup(patty);

		briocheBread.setToggleGroup(bread);
		wheatBread.setToggleGroup(bread);
		pretzelBread.setToggleGroup(bread);
	}

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


	private void priceUpdater() {

		boolean isDouble = doublePatty.isSelected();
		Bread bread = breadSelect();
		ArrayList<AddOns> addOns = addOnsSelect();
		//quantity = Integer.parseInt(number.getText());

		Burger burger = new Burger(bread, addOns, quantity, isDouble);
		double total = burger.price();

		price.setText(String.format("Price: $%.2f", total));

	}


	@FXML
	private void addBurgerToOrder(ActionEvent event) {
		boolean isDouble = doublePatty.isSelected();
		Bread bread = breadSelect();
		ArrayList<AddOns> addOns = addOnsSelect();


		Burger burger = new Burger(bread, addOns, quantity, isDouble);		// Create the burger with all selected options
		CurrentOrderController.getCurrentOrder().addItem(burger);		// to add  burger to the current order using the static method

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Item Added");
		alert.setHeaderText(null);
		alert.setContentText("Burger added to your order!");
		alert.showAndWait();
	}



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
