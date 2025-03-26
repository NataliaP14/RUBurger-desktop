package com.example.project4ruburger.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author Natalia Peguero, Olivia Kamau
 */
public class MainController {
	@FXML
	private Label welcomeText;

	@FXML
	protected void onHelloButtonClick() {
		welcomeText.setText("Welcome to JavaFX Application!");
	}
}