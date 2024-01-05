package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class ControllerTest {
	private List<NodeGUI> pointCircles = new ArrayList<>();
    private List<Node> nodeCoor = new ArrayList<>();
    private List<LineGUI> lineGUIs = new ArrayList<>();
    private int pointNum = 6;
    private int testIndex = 0;
    Integer[] currentRoute = {1, 2, 4, 6, 3, 5, 1};
    
    @FXML
    private Pane visualizePane;
    
    @FXML
    private TextArea logArea;
    
    @FXML
    private Label lbBestDistance;
    
    @FXML
    public void help() {
    	
    }
    @FXML
    public void quit() {
    	
    }
    @FXML
    public void load() {
    	Random random = new Random();
    	if(this.nodeCoor != null) {
            for (int i = 0; i < pointNum; i ++) {
            	Node newPoint = new Node(random.nextInt(300), random.nextInt(300));
            	nodeCoor.add(newPoint);
            	System.out.print("current note id: ");
            	System.out.println(newPoint.getId());
            	
            }
            System.out.println("Loaded points");
            return;
    	}
    }
    @FXML
    public void generatePointUI() {
    	for (int i = 0; i < nodeCoor.size(); i++) {
    		Node node = nodeCoor.get(i);
    		NodeGUI newNode = new NodeGUI(visualizePane, node.x, node.y);
    		pointCircles.add(newNode);
    	}
    }
    
    @FXML
    public void printRoute() {
        // Update currentRoute (replace this line with your logic to update the route)
    	updateCurrentRouteRandomly();
        
        // Remove all existing lines from the Pane
        clearLines();

        // Regenerate lines based on the new currentRoute
        drawLines();
        updateCurrentRoute();
        // Update bestDistance
        testIndex++;
        lbBestDistance.setText(Integer.toString(testIndex));
    }
    @FXML
    public void startStop() {
    	
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //-------------------------------------helper function------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    
    @FXML
    private void drawLines() {
        // Assuming currentRoute is a representation of the order of nodes
    	Integer[] currentRoute = this.currentRoute;
        for (int i = 0; i < currentRoute.length - 1; i++) {
            int startNodeId = currentRoute[i];
            int endNodeId = currentRoute[i + 1];

            Node startNode = findNodeById(startNodeId);
            Node endNode = findNodeById(endNodeId);

            if (startNode != null && endNode != null) {
                LineRoute lineRoute = new LineRoute(startNode, endNode);
                LineGUI lineGUI = new LineGUI(lineRoute);
                lineGUIs.add(lineGUI);
                lineGUI.addToPane(visualizePane);
            }
        }
    }
    
    private Node findNodeById(int id) {
        for (Node node : nodeCoor) {
            if (node.getId() == id) {
                return node;
            }
        }
        return null;
    }
    
    private void clearVisualizePane() {
        visualizePane.getChildren().clear();
        pointCircles.clear();
        lineGUIs.clear();
    }
    
    private void clearLines() {
        for (LineGUI lineGUI : lineGUIs) {
            lineGUI.removeFromPane(visualizePane);
        }
        lineGUIs.clear();
    }
    
    private void updateCurrentRoute() {
    	this.currentRoute = new Integer[]{2, 4, 5, 1, 3, 6, 2};
    }
    
    private void updateCurrentRouteRandomly() {
    	
    }

}
