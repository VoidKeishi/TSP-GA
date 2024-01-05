package test;

public class LineRoute {
    private Node startNode;
    private Node endNode;

    public LineRoute(Node startNode, Node endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public double getLength() {
        double deltaX = endNode.getX() - startNode.getX();
        double deltaY = endNode.getY() - startNode.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }
}

