package com.example.project4ruburger.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * @author Natalia Peguero, Olivia Kamau
 */
public class Main extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/project4ruburger/Main-view.fxml"));
		Parent root = fxmlLoader.load();
		Scene scene = new Scene(root, 950, 800);
		stage.setTitle("RU Burger - Main Menu");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}