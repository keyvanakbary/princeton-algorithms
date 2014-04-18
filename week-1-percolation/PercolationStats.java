public class PercolationStats {
    private double[] results;

    public PercolationStats(int size, int numExperiments) {
        assertPositive(size);
        assertPositive(numExperiments);
        results = new double[numExperiments];
        runExperiments(size, numExperiments);
    }

    private void assertPositive(int num) {
        if (num < 1) {
            throw new IllegalArgumentException();
        }
    }

    private void runExperiments(int size, int numExperiments) {
        for (int i = 0; i < numExperiments; i++) {
            results[i] = runExperiment(size);
        }
    }

    private double runExperiment(int size) {
        Percolation p = new Percolation(size);
        int openSpaces = 0;
        do {
            int row = random(size);
            int col = random(size);
            if (!p.isOpen(row, col)){
                p.open(row, col);
                openSpaces++;
            }
        } while (!p.percolates());

        return (double) openSpaces/((double) size*size);
    }

    private int random(int size) {
        return StdRandom.uniform(1, size + 1);
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return mean() - confidence();
    }

    private double confidence() {
        return (1.96 * stddev() / Math.sqrt(results.length));
    }

    public double confidenceHi() {
        return mean() + confidence();
    }

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}