package geneticalgorithm;

import java.util.Random;

import components.Individual;

public class OrderedCrossOver extends CrossOver {
    public OrderedCrossOver(Individual parent1, Individual parent2) {
        super(parent1, parent2);
    }
	public Individual getChild(){
        int chromosomeLength = parent1.getLength();
        Individual child = new Individual(chromosomeLength, -1);
        Random rand = new Random();
        int startPos = rand.nextInt(chromosomeLength - 1) + 1;
        int endPos = rand.nextInt(chromosomeLength - 1) + 1;
        while(endPos==startPos) {
        	endPos = rand.nextInt(chromosomeLength - 1) + 1;
        }
        // Make the startPos less than endPos
        if (startPos > endPos) {
            int temp = startPos;
            startPos = endPos;
            endPos = temp;
        }

        // Loop and add the sub tour from parent1 to our child
        for (int i = startPos; i < endPos; i++) {
            child.setElement(i, parent1.getElement(i));
        }

        // Loop through parent2's city tour
        for (int i = 0; i < chromosomeLength; i++) {
            // If child doesn't have the city add it
            if (!child.getChromosome().contains(parent2.getElement(i))) {
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < chromosomeLength; ii++) {
                    // Spare position found, add city
                    if (child.getElement(ii) == -1) {
                        child.setElement(ii, parent2.getElement(i));
                        break;
                    }
                }
            }
        }
        return child;
	}
}
