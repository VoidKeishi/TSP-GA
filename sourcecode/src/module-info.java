/**
 * 
 */
/**
 * 
 */
module genetic_algorithm_tsp {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires java.desktop;
	
	opens gui.view to javafx.graphics, javafx.fxml;
	opens gui.app to javafx.graphics, javafx.fxml;
	opens gui.controller to javafx.fxml;
}