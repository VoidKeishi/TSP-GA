package test;

import components.Individual;
import geneticalgorithm.CrossOver;
import geneticalgorithm.OrderedCrossOver;
import geneticalgorithm.PartiallyMappedCrossOver;

public class TestCrossOver {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Individual parent1 = new Individual(10);
		Individual parent2 = new Individual(10);
		System.out.println(parent1.toString());
		System.out.println(parent2.toString());
		CrossOver breed = new OrderedCrossOver(parent1, parent2);
		CrossOver breed2 = new PartiallyMappedCrossOver(parent1, parent2);
		Individual child = breed.getChild();
		System.out.println(child.toString());
		child = breed2.getChild();
		System.out.println(child.toString());
	}
}
