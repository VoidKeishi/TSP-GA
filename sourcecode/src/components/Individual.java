package components;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Individual {
	private ArrayList<Integer> chromosome;
	private double fitness;
	public static final Comparator<Individual> COMPARE_BY_FITNESS = new IndividualComparator();
	
	// Constructor
	// Using given chromosome
	public Individual(ArrayList<Integer> chromosome) {
		this.chromosome = chromosome;
	}
	// Generate random using Fisher-Yates shuffle Algorithm
	public Individual(int cityNum) {
	    ArrayList<Integer> chromosome = new ArrayList<>();
	    for (int gene = 0; gene < cityNum; gene++) {
	        chromosome.add(gene);
	    }
	    Random rand = new Random();
	    // Perform Fisher-Yates shuffle
	    for (int i = cityNum - 1; i >= 1; i--) {
	        // j chooses in range [1, i]
	        int j = rand.nextInt(i)+1;
	        // Swap elements at i and j
	        int temp = chromosome.get(i);
	        chromosome.set(i, chromosome.get(j));
	        chromosome.set(j, temp);
	    }
	    this.chromosome = chromosome;
	}
	// Generate empty chromosome
	public Individual(int cityNum, int placeholder) {
		ArrayList<Integer> chromosome = new ArrayList<>();
	    for (int gene = 0; gene < cityNum; gene++) {
	    	chromosome.add(placeholder);
	    }
	    this.chromosome = chromosome;
	}
	// Getter & Setter
	public ArrayList<Integer> getChromosome() {
		return this.chromosome;
	}
	public double getFitness() {
		
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public int getElement(int index) {
		return this.chromosome.get(index);
	}
	public void setElement(int index, int value) {
		this.chromosome.set(index, value);
	}
	public int getLength() {
		return this.chromosome.size();
	}
	// To string
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Integer i : chromosome) {
			s.append(i+1);
			s.append(" - ");
		}
		s.append(chromosome.get(0)+1);
		return s.toString();
	}
    public boolean contains(int value) {
        return chromosome.contains(value);
    }
}
