package gui.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import test.NodeGUI;


public class Controller {
	List<NodeGUI> pointCircles = new ArrayList<>();
    private int pointNum = 3;
    
    @FXML
    private Pane visualizePane;
    
    @FXML
    public void help() {
    	
    }
    @FXML
    public void quit() {
    	
    }
    @FXML
    public void load() {
    	for (int i = 0; i < pointNum; i++) {
    		NodeGUI newNode = new NodeGUI(visualizePane, (i+2)*10, (i+5)*10);
    		pointCircles.add(newNode);
    	}
    }
    @FXML
    public void printRoute() {
    	
    }
    @FXML
    public void startStop() {
    	
    }
}
