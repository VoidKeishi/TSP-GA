package test;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NodeGUI {
	private Circle point;
	private int xCoor;
	private int yCoor;
	
    public NodeGUI(Pane root, int xCoor, int yCoor) {
    	this.xCoor = xCoor;
    	this.yCoor = yCoor;
        createNode(root, xCoor, yCoor);
    }

    private void createNode(Pane root, int x, int y) {
        point = new Circle(x, y, 5, Color.RED);
        point.setStroke(Color.BLACK);
        point.setStrokeWidth(1);
        point.setCenterX(x);
        point.setCenterY(y);
        root.getChildren().addAll(point);
    }
}
