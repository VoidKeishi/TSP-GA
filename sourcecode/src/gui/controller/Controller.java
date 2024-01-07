package gui.controller;
 
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import components.Individual;
import components.Node;
import components.Population;
import components.Route;
import geneticalgorithm.GeneticAlgorithm;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
 

public class Controller {
	// Parameter
    int populationSize = 100;
    int cityNum = 5;
    int crossOverPercentage = 70;
    int mutationPercentage = 30;
    int maxGenerations = 100;
    
    // Genetic Algorithm
    Population population;
    GeneticAlgorithm ga;
    Individual bestIndividual;
    Route bestRoute;
    
    // GUI
    List<Line> currentState;
    private boolean isRunning = false;
	Node[] nodes;
    
    @FXML
    private Pane visualizePane;
    
    @FXML
    private TextField tfCities;

    @FXML
    private TextField tfMaxGeneration;

    @FXML
    private TextField tfPopulation;

    @FXML
    private TextField tfCrossoverRate;

    @FXML
    private TextField tfMutationRate;

    @FXML
    private Label lbBestDistance;

    @FXML
    private Label lbGenerations;

    @FXML
    private Button btnStartStop;
    
    @FXML 
    private TextArea logArea;
    
    private void createLine(Node node1,Node node2) {
    	int x1 = node1.getX();
    	int y1 = node1.getY();
    	int x2 = node2.getX();
    	int y2 = node2.getY();
        Line line = new Line(x1,y1,x2,y2);
        currentState.add(line);
        visualizePane.getChildren().add(line);
    }
    
    private void deleteLine(Node node1,Node node2) {
    	for (Line line : currentState) {
    		if (line.getStartX() != node1.getX()) {
    			continue;
    		}
    		if (line.getStartY() != node1.getY()) {
    			continue;
    		}
    		if (line.getEndX() != node2.getX()) {
    			continue;
    		}
    		if (line.getEndY() != node2.getY()) {
    			continue;
    		}
    		currentState.remove(line);
            visualizePane.getChildren().remove(line);
    		break;
    	}
    }
    private void addRoute(Route route) {
        ArrayList<Node> routeNodes = route.getRoute();
        for (int i = 0; i < cityNum - 1; i++) {
            createLine(routeNodes.get(i), routeNodes.get(i+1));
        }
        createLine(routeNodes.get(0), routeNodes.get(cityNum-1));
    }
    private void removeRoute(Route route) {
        ArrayList<Node> routeNodes = route.getRoute();
        for (int i = 0; i < routeNodes.size() - 1; i++) {
            deleteLine(routeNodes.get(i), routeNodes.get(i + 1));
        }
        deleteLine(routeNodes.get(routeNodes.size() - 1), routeNodes.get(0));
    }
    
    // Helper method to show alerts
    private void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void help() {
        // Create a custom dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Help");
        dialog.setHeaderText("How to Use the Application");

        // Set the icon (optional)
        // dialog.setGraphic(new ImageView(...));

        // Set the content of the dialog
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);

        // Add steps to the content
        Label step1Label = new Label("Step 1:");
        TextArea step1Text = new TextArea("Enter the number of cities in the 'Cities' field.");
        step1Text.setWrapText(true);
        step1Text.setEditable(false);

        Label step2Label = new Label("Step 2:");
        TextArea step2Text = new TextArea("Set other parameters such as 'Max Generation,' 'Population Size,' etc.");
        step2Text.setWrapText(true);
        step2Text.setEditable(false);

        Label step3Label = new Label("Step 3:");
        TextArea step3Text = new TextArea("Click the 'Start' button to run the algorithm.");
        step3Text.setWrapText(true);
        step3Text.setEditable(false);

        content.addRow(0, step1Label, step1Text);
        content.addRow(1, step2Label, step2Text);
        content.addRow(2, step3Label, step3Text);

        dialog.getDialogPane().setContent(content);

        // Add buttons to the dialog (OK button only)
        dialog.getDialogPane().getButtonTypes().add(javafx.scene.control.ButtonType.OK);

        // Show the dialog
        dialog.showAndWait();
    }
    @FXML
    private void quit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to quit?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // User clicked OK, so we exit the application
                Platform.exit();
            }
        });
    }
    @FXML
    public void load() {
        if (isRunning) {
            // Process is running, display a warning dialog
            showAlert("Cannot load while the process is running.", AlertType.WARNING);
            return;
        }
        
        if(nodes != null) {
        	showAlert("Please reset before loading another data.", AlertType.WARNING);
            return;
        }

        if (currentState != null) {
            clearPane();
        }

        try {
            // Validate and parse input
            if (tfCities.getText() == null || tfPopulation.getText() == null ||
                tfCrossoverRate.getText() == null || tfMutationRate.getText() == null ||
                tfMaxGeneration.getText() == null) {
                // Handle the case where any text field is null
                showAlert("Please fill in all the fields.", AlertType.WARNING);
                return;
            }

            cityNum = Integer.parseInt(tfCities.getText());
            populationSize = Integer.parseInt(tfPopulation.getText());
            crossOverPercentage = Integer.parseInt(tfCrossoverRate.getText());
            mutationPercentage = Integer.parseInt(tfMutationRate.getText());
            maxGenerations = Integer.parseInt(tfMaxGeneration.getText());
            
            if (cityNum < 4 || populationSize <= 0 || crossOverPercentage <= 0 ||
                    mutationPercentage <= 0 || maxGenerations <= 0) {
                    showAlert("Please enter values greater than 0 and ensure the number of cities is at least 4.", AlertType.WARNING);
                    return;
            }
            
        } catch (NumberFormatException e) {
            // Handle the case where parsing fails
            showAlert("Invalid input. Please enter valid numeric values.", AlertType.ERROR);
            return;
        }

        // Now you can proceed with loading
        ga = new GeneticAlgorithm(populationSize, cityNum, crossOverPercentage, mutationPercentage);
        nodes = ga.generateNodes();
        for (Node node : nodes) {
            createNode(node);
        }
    }

    @FXML
    public void printRoute() {
    	
    }
    @FXML
    private void startStop() {
    	if (nodes == null || nodes.length == 0) {
            // Nodes are not available, show a warning dialog
            showAlert("Please load data first.", AlertType.WARNING);
            return;
        }
    	
        int generations = maxGenerations;
        if ("Start".equals(btnStartStop.getText())) {
            // Logic to start the process
            btnStartStop.setText("Pause");
            isRunning = true;
            
            // Initialize population
            population = ga.initializePopulation();

            // Update fitness
            ga.updateFitness(population, nodes);
            this.currentState = new ArrayList<Line>();

            // Create a time line to schedule the updates
            Timeline timeline = new Timeline();
            timeline.setCycleCount(generations);
            AtomicInteger i = new AtomicInteger(0);
            // Create a KeyFrame that will be executed each generation
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.1), event -> {
                population = ga.evolve(population);
                ga.updateFitness(population, nodes);
                population.sortByFitness();
                bestIndividual = population.getIndividual(0);
                if (bestRoute != null) {
                    removeRoute(bestRoute);
                }
                bestRoute = new Route(bestIndividual, nodes);
                addRoute(bestRoute);
                double roundedDistance = Math.ceil(bestRoute.totalDistance() * 100) / 100; // Round up to 2 decimal places
                lbBestDistance.setText(Double.toString(roundedDistance));
                lbGenerations.setText(Integer.toString(i.incrementAndGet()));
                logArea.appendText("#G" + i + " best individual: " + bestIndividual.toString() +"\n");
                
                // Check if the maximum number of generations is reached
                if (i.get() >= maxGenerations) {
                    btnStartStop.setVisible(false);  // Hide the start button
                    timeline.pause();
                    isRunning = false;
              
                    Platform.runLater(() -> {
                        // Show a dialog indicating the process is over
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Process Completed");
                        alert.setHeaderText(null);
                        alert.setContentText("Genetic Algorithm process has completed!");

                        alert.showAndWait();
                    });
                }
            });

            timeline.getKeyFrames().add(keyFrame);
            timeline.play();

            // Print the best solution
            population.sortByFitness();
            
            //set timeline for later use in else block
            btnStartStop.setUserData(timeline);
        } else {
            // Logic to stop the process
        	Timeline timeline = (Timeline) btnStartStop.getUserData();
        	//if currently running
            if (isRunning) {
                btnStartStop.setText("Resume");
                timeline.pause();
                isRunning = false;
            } else {
                btnStartStop.setText("Pause");
                timeline.play();
                isRunning = true;
            }
        }
    }
    
    @FXML 
    private void reset() {
    	if (isRunning) {
            // Display a warning dialog if the process is running
            showAlert("Cannot reset while the process is running. Please stop the process first.", Alert.AlertType.WARNING);
            return;
        }
    	Timeline timeline = (Timeline) btnStartStop.getUserData();
        if(timeline == null) {
        	showAlert("There's nothing to reset !", Alert.AlertType.WARNING);
        	return;
        }
    	
    	btnStartStop.setText("Start");
    	btnStartStop.setVisible(true);
        
        timeline.pause();
        isRunning = false;
    	nodes = null;
        
        logArea.clear();
        lbBestDistance.setText(null);
        lbGenerations.setText(null);
        clearPane();
    }
    
    
    
    private void createNode(Node node) {
    	int x = node.getX();
    	int y = node.getY();
        Circle point = new Circle(x, y, 5, Color.RED);
        point.setStroke(Color.BLACK);
        point.setStrokeWidth(1);
        point.setCenterX(x);
        point.setCenterY(y);
        visualizePane.getChildren().addAll(point);
    }
    
    private void clearPane() {
    	visualizePane.getChildren().clear();
    	currentState.clear();
    }
}