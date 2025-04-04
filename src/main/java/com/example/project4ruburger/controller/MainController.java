package com.example.project4ruburger.controller;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * This is the main controller which sets up the main menu view
 * @author Natalia Peguero, Olivia Kamau
 */
public class MainController {

	@FXML private ImageView sandwichIcon;
	@FXML private ImageView sideIcon;
	@FXML private ImageView beverageIcon;
	@FXML private ImageView burgerIcon;
	@FXML private VBox mainBackground;
	@FXML private StackPane burgerBox;
	@FXML private StackPane sandwichBox;
	@FXML private StackPane beverageBox;
	@FXML private StackPane sideBox;

	/**
	 * Increases the scale of the rectangle when a user hovers over it
	 * @param stackPane the StackPane to get the rectangle form
	 */
	private void hover(StackPane stackPane) {
		Rectangle rectangle = (Rectangle) stackPane.getChildren().get(0);
		ScaleTransition in = new ScaleTransition(Duration.millis(300), stackPane);
		in.setToX(1.05);
		in.setToY(1.05);

		ScaleTransition out = new ScaleTransition(Duration.millis(300), stackPane);
		out.setToX(1.0);
		out.setToY(1.0);

		stackPane.setOnMouseEntered(e -> {
			in.playFromStart();
			rectangle.setFill(Color.web("#871826"));
		});
		stackPane.setOnMouseExited(e -> {
			out.playFromStart();
			rectangle.setFill(Color.web("#fffefa"));
		});
	}

	/**
	 * Loads a new scene in the current stage
	 * @param file	the FXML file name
	 * @param title	the title of the scene window
	 */
	private void loadScene(String file, String title) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project4ruburger/" + file));
			Parent root = loader.load();
			Stage stage = (Stage) burgerBox.getScene().getWindow();
			stage.setScene(new Scene(root, 950, 800));
			stage.setTitle(title);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Changes the scene based off the stackpane
	 * @param stackPane The stackpane clicked
	 * @param file the file it changes to
	 * @param title the title of the view that was switched to
	 */
	private void changeScene(StackPane stackPane, String file, String title) {
		hover(stackPane);
		stackPane.setOnMouseClicked(e -> {
			loadScene(file, title);
		});
	}

	/**
	 * Switches to the cart view
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
	 * Sets up the icons
	 */
	private void setUpIcons() {
		uploadIcons(sandwichIcon, "Sandwich.png");
		uploadIcons(burgerIcon, "Burger.png");
		uploadIcons(beverageIcon, "Beverages.png");
		uploadIcons(sideIcon, "Fries.png");

	}

	/**
	 * Initializes the controller
	 */
	public void initialize() {
		changeScene(burgerBox, "Burger-view.fxml", "RU Burger - Ordering Burgers ");
		changeScene(sandwichBox, "Sandwich-view.fxml", "RU Burger - Ordering Sandwiches");
		changeScene(beverageBox, "Beverage-view.fxml", "RU Burger - Ordering Beverages");
		changeScene(sideBox, "Side-view.fxml", "RU Burger - Ordering Sides");
		setUpIcons();

		String imagePath = getClass().getResource("/image/brownBackground.jpg").toExternalForm();
		mainBackground.setStyle("-fx-background-image: url('" + imagePath + "'); " +
				"-fx-background-size: cover; " +
				"-fx-background-position: center;");

	}


}