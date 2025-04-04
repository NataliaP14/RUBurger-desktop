package com.example.project4ruburger.controller;

import com.example.project4ruburger.model.Side;
import com.example.project4ruburger.model.Size;
import com.example.project4ruburger.model.Sides;
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
 *  The side controller handles all the actions of the side page, which creates the side
 *  based off the user customization
 * @author Natalia Peguero, Olivia Kamau
 */
public class SideController {
	@FXML private ImageView sideIcon;
	@FXML private ComboBox sideComboBox;
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
	 * Loads to a new scene
	 * @param file the view file to load
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
	 * Uploads the icon
	 * @param view the view to upload the icon to
	 * @param file the file of the icon
	 */
	@FXML
	private void uploadIcons(ImageView view, String file) {
		String imagePath = getClass().getResource("/image/" + file).toExternalForm();
		view.setImage(new Image(imagePath));
	}

	/**
	 * Sets up the icon
	 */
	private void setUpIcons() {
		minus.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		plus.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

		uploadIcons(minusIcon, "Minus.png");
		uploadIcons(plusIcon, "Plus.png");
		uploadIcons(sideIcon, "Fries.png");
		uploadIcons(backIcon, "Left.png");
	}

	/**
	 * Sets up the buttons
	 */
	private void setUpButtons() {
		sizeComboBox.setOnAction(e->priceUpdater());
		sideComboBox.setOnAction(e->priceUpdater());
		plus.setOnAction(this::increase);
		minus.setOnAction(this::decrease);

		addToOrder.setOnAction(this::addSideToOrder);

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

		sideComboBox.setOnMouseEntered(e -> {
			ListCell<?> buttonCell = sideComboBox.getButtonCell();
			if (buttonCell != null) {
				buttonCell.setStyle("-fx-text-fill: white; -fx-background-color: #6e0512;");
			}
		});

		sideComboBox.setOnMouseExited(e -> {
			ListCell<?> buttonCell = sideComboBox.getButtonCell();
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
	 * Dynamically updates the price through creating a new Side object and geting the price
	 */
	private void priceUpdater() {
		Size size = (Size) sizeComboBox.getSelectionModel().getSelectedItem();
		Side side = (Side) sideComboBox.getSelectionModel().getSelectedItem();

		if (size == null || side == null) { return; }

		Sides sides = new Sides(quantity, side, size);
		double total = sides.price();

		price.setText(String.format("Price: $%.2f", total));

	}

	/**
	 * Adds the side to the order to currentOrder
	 * @param event event handler
	 */
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

	/**
	 * Initializes the functions and data from the controller
	 */
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

		String imagePath = getClass().getResource("/image/brownBackground.jpg").toExternalForm();
		mainBackground.setStyle("-fx-background-image: url('" + imagePath + "'); " +
				"-fx-background-size: cover; " +
				"-fx-background-position: center;");

	}
}
