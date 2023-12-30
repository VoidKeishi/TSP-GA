package components;

import java.util.ArrayList;

public class Route {
	private ArrayList<Node> route = new ArrayList<Node>();
	private Individual individual;
	public Route (Individual individual, Node[] nodes) {
		ArrayList<Integer> chromosome = individual.getChromosome();
		for (int i = 0; i < nodes.length; i++ ) {
			this.route.add(nodes[chromosome.get(i)]);
		}
		this.individual = individual;
	}
	public ArrayList<Node> getRoute() {
		return this.route;
	}
	public Individual getIndividual() {
		return individual;
	}
	public double totalDistance() {
		double totalDistance = 0;
		for (int i = 0; i < this.route.size() -1 ; i++) {
			totalDistance += this.route.get(i).getDistance(this.route.get(i+1));
		}
		totalDistance += this.route.get(this.route.size() - 1).getDistance(this.route.get(0));
		return totalDistance;
	}
}
