package services;

public class ProjectCounter {
    private int Count = 0;
    // Sets the maximum allowed number of opened projects.
    public final int MaxCount = 1;

    public ProjectCounter() {


    }

    public int IncreaseCounter() {
        Count++;
        return (Count > MaxCount) ? -1 : Count;
    }

    public int DecreaseCounter() {
        Count--;
        return (Count > MaxCount) ? -1 : Count;
    }
}
