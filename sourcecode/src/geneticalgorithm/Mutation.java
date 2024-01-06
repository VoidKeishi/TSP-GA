package geneticalgorithm;

import components.Individual;

public abstract class Mutation {
	Individual original;
	public Mutation(Individual original) {
		this.original = original;
	}
    public abstract Individual mutate();
}