package geneticalgorithm;
import components.Individual;

public abstract class CrossOver {
    protected Individual parent1;
    protected Individual parent2;

    public CrossOver(Individual parent1, Individual parent2) {
        this.parent1 = parent1;
        this.parent2 = parent2;
    }
    public abstract Individual getChild();
}
