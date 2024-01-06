package geneticalgorithm;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import components.Individual;
import components.Node;
import components.Population;
import components.Route;

public class GeneticAlgorithm {
	private int mutationNum;
	private int crossOverNum;
	private int populationSize;
    private int cityNum;
	public GeneticAlgorithm(int populationSize, int cityNum, int crossOverPercentage, int mutationPercentage) {
	    this.crossOverNum = (int) Math.round(crossOverPercentage * populationSize / 100.0);
	    this.mutationNum = (int) Math.round(mutationPercentage * populationSize / 100.0);
		this.populationSize = populationSize;
		this.cityNum = cityNum;
	}
	public Node[] generateNodes() {
		Node[] nodes = new Node[this.cityNum];
		for (int i = 0; i < this.cityNum; i++) {
			int x = (int) (100 * Math.random() + 10);
			int y = (int) (100 * Math.random() + 10);
			nodes[i] = new Node(x, y);
		}
		return nodes;
	}
	public Population initializePopulation() {
		return new Population(this.populationSize, cityNum);
	}
	public void updateFitness(Population population, Node[]nodes) {
		for (Individual individual : population.getPopulation()) {
			// If there is duplicate gene -> Fitness = -10^10
			Set<Integer> uniqueGenes = new HashSet<Integer>(individual.getChromosome());
			if (uniqueGenes.size() < individual.getLength()){
				individual.setFitness(- Math.pow(10,10));
			}
			// Fitness = - total distance
			else {
				Route route = new Route(individual, nodes);
				double fitness = - route.totalDistance();
				individual.setFitness(fitness);
			}
		}
	}
	public Population evolve(Population population) {
		Population result = new Population(population.getSize()*2);
		// Keep original population
	    for (int i = 0; i < population.getSize(); i++) {
	        result.setIndividual(i, population.getIndividual(i));
	    }
		// Generate by crossover
	    for (int i = population.getSize(); i < population.getSize() + crossOverNum; i++) {
	        int index1 = new Random().nextInt(population.getSize());
	        int index2 = new Random().nextInt(population.getSize());
	        Individual parent1 = population.getIndividual(index1);
	        Individual parent2 = population.getIndividual(index2);
	     // Random crossover type
	        CrossOver crossover;
	        int crossOverType = new Random().nextInt(1);
	        switch (crossOverType) {
	        	case 0:
	        		crossover = new OrderedCrossOver(parent1, parent2);
	        		break;
	        	default:
	        		crossover = new OrderedCrossOver(parent1, parent2);
	        		break;
	        }
	        Individual child = crossover.getChild();
	        result.setIndividual(i, child);
	    }
		// Generate by mutation
	    for (int i = population.getSize() + crossOverNum; i < population.getSize() + crossOverNum + mutationNum; i++) {
	        int index = new Random().nextInt(population.getSize());
	        Individual original = population.getIndividual(index);
	        // Random mutation type
	        Mutation mutation;
	        int mutationType = new Random().nextInt(2);
	        switch(mutationType){
	            case 0:
	                mutation = new SwapMutation(original);
	                break;
	            case 1:
	                mutation = new InverseMutation(original);
	                break;
	            default:
	                mutation = new SwapMutation(original);
	                break;
	        }
	        Individual mutatedIndividual = mutation.mutate();
	        result.setIndividual(i, mutatedIndividual);
	    }
		// Generate random
	    for (int i = population.getSize() + crossOverNum + mutationNum; i < result.getSize(); i++) {
	        result.setIndividual(i, new Individual(cityNum));
	    }
		// Select best to survive
	    Population newPopulation = new Population(population.getSize());
	    result.sortByFitness();
	    for (int i = 0; i < newPopulation.getSize(); i++) {
	        newPopulation.setIndividual(i, result.getIndividual(i));
	    }
		return newPopulation;
	}
	
}
