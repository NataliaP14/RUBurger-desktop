module com.example.project4ruburger {
	requires javafx.controls;
	requires javafx.fxml;


	opens com.example.project4ruburger.controller to javafx.fxml;
	exports com.example.project4ruburger.model;

}