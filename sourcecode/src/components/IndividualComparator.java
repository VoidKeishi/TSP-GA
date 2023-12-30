package components;

import java.util.Comparator;

public class IndividualComparator implements Comparator<Individual> {
	@Override
	public int compare(Individual i1, Individual i2) {
		return - Double.compare(i1.getFitness(),i2.getFitness());
	}
}
