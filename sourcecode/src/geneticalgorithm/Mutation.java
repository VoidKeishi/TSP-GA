
package geneticalgorithm;

import components.Individual;

public abstract class Mutation {
	protected Individual original;
	public Mutation(Individual original) {
		this.original = original;
	}
    public abstract Individual mutate();
}