package geneticalgorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import components.Individual;

public class PartiallyMappedCrossOver extends CrossOver {
    public PartiallyMappedCrossOver(Individual parent1, Individual parent2) {
        super(parent1, parent2);
    }

    public Individual getChild() {
        int chromosomeLength = parent1.getLength();
        Individual child = new Individual(chromosomeLength, -1);
        Random rand = new Random();

        int startPos = rand.nextInt(chromosomeLength - 1) + 1;
        int endPos = rand.nextInt(chromosomeLength - 1) + 1;
        while (endPos == startPos) {
            endPos = rand.nextInt(chromosomeLength - 1) + 1;
        }

        // Make sure startPos is less than endPos
        if (startPos > endPos) {
            int temp = startPos;
            startPos = endPos;
            endPos = temp;
        }

        // Copy the segment from parent1 to child
        for (int i = startPos; i < endPos; i++) {
            child.setElement(i, parent1.getElement(i));
        }

        // Create a mapping between the values in the segment and their positions
        Map<Integer, Integer> mapping = new HashMap<>();
        for (int i = startPos; i < endPos; i++) {
            mapping.put(parent1.getElement(i), parent2.getElement(i));
        }

        // Fill in the remaining positions in the child
        for (int i = 0; i < chromosomeLength; i++) {
            if (i < startPos || i >= endPos) {
                int currentGene = parent2.getElement(i);

                while (child.contains(currentGene)) {
                    currentGene = mapping.get(currentGene);
                }

                child.setElement(i, currentGene);
            }
        }

        return child;
    }
}
