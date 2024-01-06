package gui.controller;
 
import java.util.ArrayList;
import java.util.List;

import components.Individual;
import components.Node;
import components.Population;
import components.Route;
import geneticalgorithm.GeneticAlgorithm;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
 

public class Controller {
	// Parameter
    int populationSize = 100;
    int cityNum = 5;
    int crossOverPercentage = 70;
    int mutationPercentage = 30;
    int generations = 100;
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
    private Label tfGenerations;

    @FXML
    private Button btnStartStop;
    
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
        for (int i = 0; i < cityNum - 1; i++) {
            deleteLine(routeNodes.get(i), routeNodes.get(i+1));
        }
        deleteLine(routeNodes.get(0), routeNodes.get(cityNum-1));
    }
    @FXML
    public void help() {
    	
    }
    @FXML
    public void quit() {
    	
    }
    @FXML
    public void load() {
    	
    	cityNum = Integer.parseInt(tfCities.getText());
        populationSize = Integer.parseInt(tfPopulation.getText());
        crossOverPercentage = Integer.parseInt(tfCrossoverRate.getText());
        mutationPercentage = Integer.parseInt(tfMutationRate.getText());
        generations = Integer.parseInt(tfGenerations.getText());
        		
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
        if ("Start".equals(btnStartStop.getText())) {
            // Logic to start the process
            btnStartStop.setText("Stop");
            // Initialize population
            population = ga.initializePopulation();

            // Update fitness
            ga.updateFitness(population, nodes);
            this.currentState = new ArrayList<Line>();
            // Evolve population for a certain number of generations
            int generations = 100;
            for (int i = 0; i < generations; i++) {
                population = ga.evolve(population);
                ga.updateFitness(population, nodes);
                population.sortByFitness();
                bestIndividual = population.getIndividual(0);
                if (bestRoute!=null) {
                    removeRoute(bestRoute);
                }
                bestRoute = new Route(bestIndividual,nodes);
                final Route displayRoute = bestRoute;
                javafx.application.Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        addRoute(displayRoute);
                    }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            // Print the best solution
            population.sortByFitness();
        } else {
            // Logic to stop the process
            btnStartStop.setText("Start");
        }
        isRunning = !isRunning;
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

}