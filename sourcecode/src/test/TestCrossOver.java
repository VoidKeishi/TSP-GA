package test;

import components.Individual;
import geneticalgorithm.CrossOver;
import geneticalgorithm.OrderedCrossOver;

public class TestCrossOver {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Individual parent1 = new Individual(5);
		Individual parent2 = new Individual(5);
		System.out.println(parent1.toString());
		System.out.println(parent2.toString());
		CrossOver breed = new OrderedCrossOver(parent1, parent2);
		Individual child = breed.getChild();
		System.out.println(child.toString());
	}
}
