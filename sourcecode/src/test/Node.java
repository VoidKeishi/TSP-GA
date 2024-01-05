package test;

import java.awt.Point;

public class Node extends Point {
	
	private int id;
	private static int nbPoint = 0;
	public Node(int xCoor, int yCoor) {
		this.x = xCoor;
		this.y = yCoor;
		this.id = ++nbPoint;
	}
	public int getId() {
		return id;
	}
}
