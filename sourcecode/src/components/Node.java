package components;

public class Node {
	private int x;
	private int y;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public double getDistance(Node node) {
		double X = Math.pow((node.getX() - this.getX()), 2);
		double Y = Math.pow((node.getY() - this.getY()), 2);
		return Math.sqrt(Math.abs(X + Y));
	}
	public String toString(){
		return "x: " + getX() + " - y: " + getY();
	}
}