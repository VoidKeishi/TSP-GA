package gui.controller;

import java.util.ArrayList;
import java.util.List;

import gui.view.NodeGUI;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;


public class Controller {
	List<NodeGUI> pointCircles = new ArrayList<>();
    private int pointNum = 3;
    
    @FXML
    private Pane drawingPane;
    
    @FXML
    public void help() {
    	
    }
    @FXML
    public void quit() {
    	
    }
    @FXML
    public void load() {
    	for (int i = 0; i < pointNum; i++) {
    		NodeGUI newNode = new NodeGUI(drawingPane, (i+2)*10, (i+5)*10);
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
