package test;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LineGUI {
    private LineRoute lineRoute;
    private Line line;

    public LineGUI(LineRoute lineRoute) {
        this.lineRoute = lineRoute;
        this.line = new Line(lineRoute.getStartNode().getX(), lineRoute.getStartNode().getY(),
                lineRoute.getEndNode().getX(), lineRoute.getEndNode().getY());
        this.line.setStroke(Color.BLACK);
    }

    public Line getLine() {
        return line;
    }

    public void addToPane(Pane pane) {
        pane.getChildren().add(line);
    }
    public void removeFromPane(Pane pane) {
        pane.getChildren().remove(line);
    }
}
