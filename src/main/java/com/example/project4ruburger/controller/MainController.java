package com.example.project4ruburger.controller;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * @author Natalia Peguero, Olivia Kamau
 */
public class MainController {

	@FXML private Button cart;
	@FXML private Button orders;
	@FXML private StackPane burgerBox;
	@FXML private StackPane sandwichBox;
	@FXML private StackPane beverageBox;
	@FXML private StackPane sideBox;


	private void hover(StackPane stackPane) {
		ScaleTransition in = new ScaleTransition(Duration.millis(300), stackPane);
		in.setToX(1.05);
		in.setToY(1.05);

		ScaleTransition out = new ScaleTransition(Duration.millis(300), stackPane);
		out.setToX(1.0);
		out.setToY(1.0);

		stackPane.setOnMouseEntered(e -> in.playFromStart());
		stackPane.setOnMouseExited(e -> out.playFromStart());
	}

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

	private void changeScene(StackPane stackPane, String file, String title) {
		hover(stackPane);
		stackPane.setOnMouseClicked(e -> {
			//changeColor(stackPane);
			loadScene(file, title);
		});
	}

	@FXML
	public void goToCart(ActionEvent actionEvent) {
		loadScene("CurrentOrder-view.fxml", "RU Burger - Cart");
	}

	@FXML
	public void goToOrders(ActionEvent actionEvent) {
		loadScene("PlacedOrder-view.fxml", "RU Burger - Orders");
	}

	public void initialize() {
		changeScene(burgerBox, "Burger-view.fxml", "RU Burger - Ordering Burgers ");
		changeScene(sandwichBox, "Sandwich-view.fxml", "RU Burger - Ordering Sandwiches");
		changeScene(beverageBox, "Beverage-view.fxml", "RU Burger - Ordering Beverages");
		changeScene(sideBox, "Side-view.fxml", "RU Burger - Ordering Sides");
	}


}