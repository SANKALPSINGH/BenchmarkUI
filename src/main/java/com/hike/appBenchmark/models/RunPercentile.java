package com.hike.appBenchmark.models;

/**
 *
 * @author kumarpratyush
 *
 */

public class RunPercentile {

    private int runPercentileId;
    private Run run;
    private Percentile runPercentile;

    public RunPercentile(int runPercentileId, Run run, Percentile runPercentile) {
        this.runPercentileId = runPercentileId;
        this.run = run;
        this.runPercentile = runPercentile;
    }

    public RunPercentile(Run run, Percentile runPercentile) {
        this.run = run;
        this.runPercentile = runPercentile;
    }

    public RunPercentile() {}

    public int getRunPercentileId() {
        return runPercentileId;
    }

    public void setRunPercentileId(int runPercentileId) {
        this.runPercentileId = runPercentileId;
    }

    public Run getRun() {
        return run;
    }

    public void setRun(Run run) {
        this.run = run;
    }

    public Percentile getRunPercentile() {
        return runPercentile;
    }

    public void setRunPercentile(Percentile runPercentile) {
        this.runPercentile = runPercentile;
    }

}
