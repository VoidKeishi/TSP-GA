package test;

import components.Individual;
import geneticalgorithm.CrossOver;
import geneticalgorithm.InverseMutation;
import geneticalgorithm.Mutation;
import geneticalgorithm.SwapMutation;

public class TestMutation {
	public static void main(String[] args) {
		Individual original = new Individual(5);
		System.out.println(original.toString());
		Mutation mutation = new SwapMutation(original);
		Mutation mutation2 = new InverseMutation(original);
		Individual mutated = mutation.mutate();
		System.out.println(mutated.toString());
		Individual mutated2 = mutation2.mutate();
		System.out.println(mutated2.toString());
	}
}
