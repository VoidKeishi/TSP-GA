package test;

import components.Individual;
import components.Node;
import components.Population;
import geneticalgorithm.GeneticAlgorithm;

public class TestComponent {

	public static void main(String[] args) {
        int populationSize = 100;
        int cityNum = 50;
        int crossOverPercentage = 70;
        int mutationPercentage = 30;
		GeneticAlgorithm ga = new GeneticAlgorithm(populationSize, cityNum, crossOverPercentage, mutationPercentage);

        // Generate nodes
        Node[] nodes = ga.generateNodes();
        for (Node node : nodes) {
        	System.out.println(node.toString());
        }
        Population population = ga.initializePopulation();
        System.out.println(population.getSize());
	}

}
