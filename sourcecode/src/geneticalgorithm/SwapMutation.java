package geneticalgorithm;

import java.util.ArrayList;
import java.util.Random;
import components.Individual;

public class SwapMutation extends Mutation {
	public SwapMutation(Individual original) {
		super(original);
	}
	@Override
    public Individual mutate() {
        // Create a new individual with the same chromosome as the input individual
        Individual mutatedIndividual = original;
        // Get the size of the chromosome
        int chromosomeLength = mutatedIndividual.getLength();

        // Select two random genes
        Random rand = new Random();
        int index1 = rand.nextInt(chromosomeLength - 1) + 1;
        int index2 = rand.nextInt(chromosomeLength - 1) + 1;

        // Swap the genes
        int gene1 = mutatedIndividual.getElement(index1);
        int gene2 = mutatedIndividual.getElement(index2);
        mutatedIndividual.setElement(index2, gene1);
        mutatedIndividual.setElement(index1, gene2);

        return mutatedIndividual;
    }
}