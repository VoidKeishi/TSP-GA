package components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {
	private ArrayList<Individual> population = new ArrayList<Individual>();
	// Constructor
		// Generate random
	public Population(int populationSize, int cityNum) {
		this.population = new ArrayList<Individual>();
		for (int individualCount = 0; individualCount < populationSize; individualCount++) {
			Individual individual = new Individual(cityNum);
			this.population.add(individual);
		}
	}
		// Create empty
	public Population(int populationSize) {
		this.population = new ArrayList<Individual>(Collections.nCopies(populationSize, null));
	}
	// Getter & Setter
	public ArrayList<Individual> getPopulation() {
		return population;
	}

	public void setIndividual(int index, Individual individual) {
		this.population.set(index, individual);
	}

	public Individual getIndividual(int index) {
		return this.population.get(index);
	}
	public int getSize() {
		return this.population.size();
	}

	public List<Individual> sortByFitness(){
		Collections.sort(population, Individual.COMPARE_BY_FITNESS);
		return population;
	}
}

