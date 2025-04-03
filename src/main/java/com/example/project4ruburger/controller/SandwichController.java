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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Natalia Peguero, Olivia Kamau
 */
public class SandwichController {
	public Button cart;
	public Button orders;
	public ImageView sandwichIcon;
	public Button combo;
	public RadioButton roastBeef;
	public RadioButton salmon;
	public RadioButton chicken;
	public ComboBox breadComboBox;
	public CheckBox lettuce;
	public CheckBox tomato;
	public CheckBox onion;
	public CheckBox avocado;
	public CheckBox cheese;
	public Button minus;
	public ImageView minusIcon;
	public Label number;
	public Button plus;
	public ImageView plusIcon;
	public Label price;
	public Button addToOrder;
	public VBox mainBackground;
	public ImageView backIcon;
	@FXML private Button back;

	ToggleGroup protein = new ToggleGroup();
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
			Sandwich sandwich = combo.getSandwich();
			if (sandwich != null) { controller.sandwichDetails.setText(sandwich.toString());}
			Stage stage = (Stage) back.getScene().getWindow();
			stage.setScene(new Scene(root, 950, 800));
			stage.setTitle("RU Burger - Combo");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void goToCombo(ActionEvent actionEvent) {
		Bread bread = (Bread) breadComboBox.getSelectionModel().getSelectedItem();

		Protein protein = proteinSelect();

		ArrayList<AddOns> addOns = addOnsSelect();

		Sandwich sandwich = new Sandwich(quantity, bread, protein, addOns);

		Beverage drink = new Beverage(1, Size.MEDIUM, Flavor.COLA);

		Side side = Side.CHIPS;

		Combo combo = new Combo(quantity, sandwich, drink, side);
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
		uploadIcons(sandwichIcon, "Sandwich.png");
		uploadIcons(backIcon, "Left.png");
	}

	private void setUpToggle() {
		roastBeef.setToggleGroup(protein);
		salmon.setToggleGroup(protein);
		chicken.setToggleGroup(protein);

	}

	private void setUpButtons() {
		roastBeef.setOnAction(e ->priceUpdater());
		salmon.setOnAction(e ->priceUpdater());
		chicken.setOnAction(e ->priceUpdater());

		lettuce.setOnAction(e ->priceUpdater());
		tomato.setOnAction(e ->priceUpdater());
		onion.setOnAction(e ->priceUpdater());
		avocado.setOnAction(e ->priceUpdater());
		cheese.setOnAction(e ->priceUpdater());

		plus.setOnAction(this::increase);
		minus.setOnAction(this::decrease);

		addToOrder.setOnAction(this::addSandwichToOrder);
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

	private ArrayList<AddOns> addOnsSelect() {
		ArrayList<AddOns> addOns = new ArrayList<>();

		if (lettuce.isSelected()) {
			addOns.add(AddOns.LETTUCE);
		}
		if (tomato.isSelected()) {
			addOns.add(AddOns.TOMATOES);
		}
		if (onion.isSelected()) {
			addOns.add(AddOns.ONIONS) ;
		}
		if (avocado.isSelected()) {
			addOns.add(AddOns.AVOCADO);
		}
		if (cheese.isSelected()) {
			addOns.add(AddOns.CHEESE);
		}

		return addOns;
	}

	private Protein proteinSelect() {
		Protein protein = null;
		if (roastBeef.isSelected()) {
			protein = Protein.ROAST_BEEF;
		} else if (salmon.isSelected()) {
			protein = Protein.SALMON;
		} else if (chicken.isSelected()) {
			protein = Protein.CHICKEN;
		}
		return protein;
	}


	private void priceUpdater() {

		Bread bread = (Bread) breadComboBox.getSelectionModel().getSelectedItem();

		Protein protein = proteinSelect();

		ArrayList<AddOns> addOns = addOnsSelect();

		Sandwich sandwich = new Sandwich(quantity, bread, protein, addOns);

		double total = sandwich.price();

		price.setText(String.format("Price: $%.2f", total));

	}

	private String changeBread(Bread bread) {
		return switch (bread) {
			case BRIOCHE -> "Brioche";
			case WHEAT_BREAD -> "Wheat Bread";
			case PRETZEL-> "Pretzel";
			case BAGEL -> "Bagel";
			case SOURDOUGH -> "Sourdough";
		};
	}

	@FXML
	private void addSandwichToOrder(ActionEvent event) {
		Protein protein = proteinSelect();
		Bread bread = (Bread) breadComboBox.getValue();
		ArrayList<AddOns> addOns = addOnsSelect();


		Sandwich sandwich = new Sandwich(quantity, bread, protein, addOns);		// Create the sandwich with all selected options
		CurrentOrderController.getCurrentOrder().addItem(sandwich);		// to add  burger to the current order using the static method

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Item Added");
		alert.setHeaderText(null);
		alert.setContentText("Sandwich added to your order!");
		alert.showAndWait();
	}


	@FXML
	public void initialize() {
		setUpIcons();
		setUpToggle();
		setUpButtons();

		quantity = 1;
		updateQuantity();

		ObservableList<Bread> breads = FXCollections.observableArrayList(Bread.values());
		breadComboBox.setItems(breads);
		breadComboBox.getSelectionModel().selectFirst();

		breadComboBox.setButtonCell(new ListCell<Bread>() {
			@Override
			protected void updateItem(Bread bread, boolean empty) {
				super.updateItem(bread, empty);
				if (empty || bread == null) {
					setText("Select Bread");
				} else {
					setText(changeBread(bread));
				}
			}
		});

		String imagePath = getClass().getResource("/image/brownBackground.jpg").toExternalForm();
		mainBackground.setStyle("-fx-background-image: url('" + imagePath + "'); " +
				"-fx-background-size: cover; " +
				"-fx-background-position: center;");

	}




}
