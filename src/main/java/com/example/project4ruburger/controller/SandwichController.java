package com.example.project4ruburger.controller;

import com.example.project4ruburger.model.Bread;
import com.example.project4ruburger.model.Protein;
import com.example.project4ruburger.model.Beverage;
import com.example.project4ruburger.model.Side;
import com.example.project4ruburger.model.Sandwich;
import com.example.project4ruburger.model.Combo;
import com.example.project4ruburger.model.AddOns;
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
 * The sandwich controller handles all the actions of the sandwich page, which creates the sandwich
 * based off the user customization, users additionally have the option to change it to a combo
 * @author Natalia Peguero, Olivia Kamau
 */
public class SandwichController {
	@FXML private ImageView sandwichIcon;
	@FXML private RadioButton roastBeef;
	@FXML private RadioButton salmon;
	@FXML private RadioButton chicken;
	@FXML private ComboBox breadComboBox;
	@FXML private CheckBox lettuce;
	@FXML private CheckBox tomato;
	@FXML private CheckBox onion;
	@FXML private CheckBox avocado;
	@FXML private CheckBox cheese;
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


	ToggleGroup protein = new ToggleGroup();
	private int quantity;

	/**
	 * Loads to a new scene
	 * @param file the view to load
	 * @param title the title to set the view to
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
	 * switches back to the main menu
	 * @param actionEvent event handler
	 */
	@FXML
	private void backToMainMenu(ActionEvent actionEvent) {
		loadScene("Main-view.fxml", "RU Burger - Main Menu");
	}

	/**
	 * switches to the current order view
	 * @param actionEvent event handler
	 */
	@FXML
	private void goToCart(ActionEvent actionEvent) {
		loadScene("CurrentOrder-view.fxml", "RU Burger - Cart");
	}

	/**
	 * Switches to the placed order view
	 * @param actionEvent event handler
	 */
	@FXML
	private void goToOrders(ActionEvent actionEvent) {
		loadScene("PlacedOrder-view.fxml", "RU Burger - Orders");
	}

	/**
	 * Loads to the combo view
	 * @param combo the combo to load
	 */
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

	/**
	 * Switches to the combo page, by creating a new combo object and calling the load combo scene
	 * @param actionEvent event handler
	 */
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
	 * Sets up the icons and changes the style of the minus and plus button
	 */
	private void setUpIcons() {
		minus.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		plus.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

		uploadIcons(minusIcon, "Minus.png");
		uploadIcons(plusIcon, "Plus.png");
		uploadIcons(sandwichIcon, "Sandwich.png");
		uploadIcons(backIcon, "Left.png");
	}

	/**
	 * Sets up the toggles
	 */
	private void setUpToggle() {
		roastBeef.setToggleGroup(protein);
		salmon.setToggleGroup(protein);
		chicken.setToggleGroup(protein);

	}

	/**
	 * Sets up all the buttons from the view
	 */
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

		breadComboBox.setOnMouseEntered(e -> {
			ListCell<?> buttonCell = breadComboBox.getButtonCell();
			if (buttonCell != null) {
				buttonCell.setStyle("-fx-text-fill: white; -fx-background-color: #6e0512;");
			}
		});

		breadComboBox.setOnMouseExited(e -> {
			ListCell<?> buttonCell = breadComboBox.getButtonCell();
			if (buttonCell != null) {
				buttonCell.setStyle("-fx-text-fill: black; -fx-background-color: transparent;");
			}
		});

	}

	/**
	 * Increase the quantity
	 * @param actionEvent event handler
	 */
	private void increase(ActionEvent actionEvent) {
		quantity++;
		updateQuantity();
	}

	/**
	 * Decrease the quantity
	 * @param actionEvent event handler
	 */
	private void decrease(ActionEvent actionEvent) {
		if (quantity > 1) {
			quantity--;
		}
		updateQuantity();
	}

	/**
	 * Dynamically updates the quantity
	 */
	private void updateQuantity() {
		number.setText(String.valueOf(quantity));
		priceUpdater();
	}

	/**
	 * Adds the addons to the arraylist based off user selection
	 * @return the addons
	 */
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

	/**
	 * Gets the protein from the enum class based off user selection
	 * @return the protein
	 */
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

	/**
	 * Updates the price by creating a new object to dynamically change it
	 */
	private void priceUpdater() {

		Bread bread = (Bread) breadComboBox.getSelectionModel().getSelectedItem();

		Protein protein = proteinSelect();

		ArrayList<AddOns> addOns = addOnsSelect();

		Sandwich sandwich = new Sandwich(quantity, bread, protein, addOns);

		double total = sandwich.price();

		price.setText(String.format("Price: $%.2f", total));

	}

	/**
	 * Changes the bread to Capital case
	 * @param bread the bread select
	 * @return returns the bread
	 */
	private String changeBread(Bread bread) {
		return switch (bread) {
			case BRIOCHE -> "Brioche";
			case WHEAT_BREAD -> "Wheat Bread";
			case PRETZEL-> "Pretzel";
			case BAGEL -> "Bagel";
			case SOURDOUGH -> "Sourdough";
		};
	}

	/**
	 * Adds the sandwich to the order
	 * @param event event handler
	 */
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

	/**
	 * Initializes the the data and functions from the controller when the program runs
	 */
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
