package gui.controller;
 
import java.util.ArrayList;
import java.util.Iterator;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
 

public class Controller {
	// Constant
	static final double FAST = 0.005;
	static final double MEDIUM = 0.02;
	static final double SLOW = 0.1;
	static final Color START_POINT = Color.ORANGE;
	static final Color POINT = Color.GREEN;
	static final Color NEW_LINE = Color.RED;
	static final Color OLD_LINE = Color.BLUE;
	
	// Parameter
    int populationSize = 100;
    int cityNum = 5;
    int crossOverPercentage = 70;
    int mutationPercentage = 30;
    int maxGenerations = 100;
    double delayTime = MEDIUM;
    
    // Genetic Algorithm
    Population population;
    GeneticAlgorithm ga;
    Individual bestIndividual;
    Route bestRoute;
    
    // GUI
    List<Line> currentState = new ArrayList<>();
    List<Line> previousState = new ArrayList<>();
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
    
    @FXML
    private ToggleGroup Speed;
    
    @FXML
    void spdFast(ActionEvent event) {
    	delayTime = FAST;
    }

    @FXML
    void spdMedium(ActionEvent event) {
    	delayTime = MEDIUM;
    }

    @FXML
    void spdSlow(ActionEvent event) {
    	delayTime = SLOW;
    }
    
    private void createNode(Node node, boolean isStart, int number) {
        int x = node.getX();
        int y = node.getY();
        Color color = isStart ? START_POINT : POINT; // Use green for the starting point, red for others
        Circle point = new Circle(x, y, 5, color);
        point.setStroke(Color.BLACK);
        point.setStrokeWidth(1);
        point.setCenterX(x);
        point.setCenterY(y);
        visualizePane.getChildren().addAll(point);

        // Create a label with the number and add it to the pane
        Label label = new Label(Integer.toString(number));
        label.setLayoutX(x + 10); // Adjust these values as needed
        label.setLayoutY(y);
        visualizePane.getChildren().add(label);
    }
    
    private void createLine(Node node1,Node node2,Color color) {
        Line line = new Line(node1.getX(),node1.getY(),node2.getX(),node2.getY());
        line.setStroke(color);
        currentState.add(line);
        visualizePane.getChildren().add(line);
    }
    

    private void addRoute(Route route) {
        ArrayList<Node> routeNodes = route.getRoute();
        for (int i = 0; i < cityNum - 1; i++) {
            createLine(routeNodes.get(i), routeNodes.get(i+1),NEW_LINE);
        }
        createLine(routeNodes.get(routeNodes.size() - 1), routeNodes.get(0),NEW_LINE);
    }
    
    private void updateRoutes() {
        if (previousState != null) {
            Iterator<Line> iterator = previousState.iterator();
            while (iterator.hasNext()) {
                Line line = iterator.next();
                visualizePane.getChildren().remove(line);
                iterator.remove();
            }
        }
        if (currentState != null) {
            Iterator<Line> iterator = currentState.iterator();
            while (iterator.hasNext()) {
                Line line = iterator.next();
                line.setStroke(OLD_LINE);
                previousState.add(line);
                iterator.remove();
            }
        }
    }
    
    
    // Helper method to show alerts
    private void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    public void help() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Help");
        dialog.setHeaderText("How to use the program");

        // Set the button types.
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        // Create the explanation label and add it to a dialog pane.
        String explanationText = "This program is a visualization of a Genetic Algorithm solving the Traveling Salesman Problem.\n\n" +
                                 "Here's how to use it:\n\n" +
                                 "1. Number of Cities: Enter the number of cities that the algorithm will try to find the shortest route for. The cities will be randomly generated on the map.\n" +
                                 "2. Max Generations: Enter the maximum number of generations that the algorithm will run for. A generation is a complete cycle of creating a new population from the current one.\n" +
                                 "3. Population Size: Enter the size of the population in each generation. The population is a set of possible routes.\n" +
                                 "4. Crossover %: Enter the percentage of the next generation that will be created by crossover. Crossover is a process where two routes are combined to create a new one.\n"+
                                 "5. Mutation %: Enter the percentage of the next generation that will be created by mutation. Mutation is a process where a route is slightly altered to create a new one.\n\n"+
                                 "After entering these parameters, click 'Load' to generate the cities. Then, click 'Start' to begin the algorithm. You can pause the algorithm at any time by clicking 'Pause'.\n"+
                                 "The algorithm can be reset by clicking 'Reset', which will clear the map and allow you to enter new parameters.\n"+
                                 "The 'Fast', 'Medium', and 'Slow' buttons control the speed of the visualization.\n"+
                                 "The best route found so far and the number of generations passed are displayed at the bottom of the screen.";
        Label explanation = new Label(explanationText);
        dialog.getDialogPane().setContent(explanation);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        Image icon = new Image(getClass().getResourceAsStream("/gui/view/help.png"));
        stage.getIcons().add(icon);
        // Show the dialog and wait for the user to close it
        dialog.showAndWait();
    }

    @FXML
    public void quit() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setHeaderText("Quit the application");
        alert.setContentText("Are you sure you want to quit?");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        Image icon = new Image(getClass().getResourceAsStream("/gui/view/quit.png"));
        stage.getIcons().add(icon);
        // Handle the result of the confirmation alert.
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // User chose OK, so quit the application.
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
        for (int i = 0; i < nodes.length; i++) {
            createNode(nodes[i], i == 0, i + 1); // +1 if you want the numbers to start from 1 instead of 0
        }
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
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(delayTime), event -> {
                population = ga.evolve(population);
                ga.updateFitness(population, nodes);
                population.sortByFitness();
                bestIndividual = population.getIndividual(0);
                updateRoutes();
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
    	btnStartStop.setText("Start");
    	btnStartStop.setVisible(true);
        Timeline timeline = (Timeline) btnStartStop.getUserData();
        timeline.pause();
        isRunning = false;
    	nodes = null;
        
        logArea.clear();
        lbBestDistance.setText(null);
        lbGenerations.setText(null);
        clearPane();
    }
    
    private void clearPane() {
    	visualizePane.getChildren().clear();
    	currentState.clear();
    }
}