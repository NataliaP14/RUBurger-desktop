package com.example.project4ruburger.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Natalia Peguero, Olivia Kamau
 */
public class PlacedOrderController {
	public Button cart;
	public Button orders;
	public ImageView backIcon;
	public ComboBox orderDropdownComboBox;
	public VBox mainBackground;
	@FXML
	private Button back;


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

	private void setUpButtons() {
		orderDropdownComboBox.setOnMouseEntered(e -> {
			ListCell<?> buttonCell = orderDropdownComboBox.getButtonCell();
			if (buttonCell != null) {
				buttonCell.setStyle("-fx-text-fill: white; -fx-background-color: #6e0512;");
			}
		});

		orderDropdownComboBox.setOnMouseExited(e -> {
			ListCell<?> buttonCell = orderDropdownComboBox.getButtonCell();
			if (buttonCell != null) {
				buttonCell.setStyle("-fx-text-fill: black; -fx-background-color: transparent;");
			}
		});

	}

	@FXML
	private void uploadIcons(ImageView view, String file) {
		String imagePath = getClass().getResource("/image/" + file).toExternalForm();
		view.setImage(new Image(imagePath));
	}

	private void setUpIcons() {
		uploadIcons(backIcon, "Left.png");

	}

	@FXML
	public void initialize() {
		setUpIcons();
		setUpButtons();

		String imagePath = getClass().getResource("/image/brownBackground.jpg").toExternalForm();
		mainBackground.setStyle("-fx-background-image: url('" + imagePath + "'); " +
				"-fx-background-size: cover; " +
				"-fx-background-position: center;");
	}
}