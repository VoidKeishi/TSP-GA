package test;

import components.Individual;
import components.Node;
import components.Population;
import components.Route;
import geneticalgorithm.GeneticAlgorithm;

public class Console {
    public static void main(String[] args) {
        // Define parameters
        int populationSize = 100;
        int cityNum = 5;
        int crossOverPercentage = 70;
        int mutationPercentage = 30;

        // Create a GeneticAlgorithm object
        GeneticAlgorithm ga = new GeneticAlgorithm(populationSize, cityNum, crossOverPercentage, mutationPercentage);

        // Generate nodes
        Node[] nodes = ga.generateNodes();
        for (Node node : nodes) {
        	System.out.println(node.toString());
        }
        // Initialize population
        Population population = ga.initializePopulation();

        // Update fitness
        ga.updateFitness(population, nodes);

        // Evolve population for a certain number of generations
        int generations = 100;
        for (int i = 0; i < generations; i++) {
            population = ga.evolve(population);
            ga.updateFitness(population, nodes);
        }

        // Print the best solution
        population.sortByFitness();
        Individual bestSolution = population.getIndividual(0);
        System.out.println("Best solution: " + bestSolution.toString());
        Route bestRoute = new Route(bestSolution, nodes);
        System.out.println("Total distance: " + bestRoute.totalDistance());
    }
}