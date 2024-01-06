package geneticalgorithm;
import java.util.Random;
import components.Individual;

public class InverseMutation extends Mutation {
    public InverseMutation(Individual original) {
        super(original);
    }

    @Override
    public Individual mutate() {
        // Create a new individual with the same chromosome as the input individual
        Individual mutatedIndividual = new Individual(original.getChromosome().size(), -1);

        // Copy the original chromosome to the mutated individual
        for (int i = 0; i < original.getChromosome().size(); i++) {
            mutatedIndividual.getChromosome().set(i, original.getChromosome().get(i));
        }

        // Get the size of the chromosome
        int chromosomeLength = mutatedIndividual.getChromosome().size();

        // Select two random points
        Random rand = new Random();
        int index1 = rand.nextInt(chromosomeLength - 1) + 1;
        int index2 = rand.nextInt(chromosomeLength - 1) + 1;

        // Make sure index1 is less than index2
        if (index1 > index2) {
            int temp = index1;
            index1 = index2;
            index2 = temp;
        }

        // Reverse the sequence of genes between index1 and index2
        while (index1 < index2) {
            int temp = mutatedIndividual.getChromosome().get(index1);
            mutatedIndividual.getChromosome().set(index1, mutatedIndividual.getChromosome().get(index2));
            mutatedIndividual.getChromosome().set(index2, temp);
            index1++;
            index2--;
        }

        return mutatedIndividual;
    }
}