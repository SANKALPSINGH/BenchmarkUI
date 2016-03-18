package com.hike.appBenchmark.models;

/**
 *
 * @author kumarpratyush
 *
 */

public class RunData {

    private RunPercentile runPercentile;
    private Actions action;
    private Double reading1;
    private Double reading2;
    private Double reading3;
    private Double reading4;
    private Double reading5;
    private Double total;
    private Double average;

    public RunData(RunPercentile runPercentile, Actions action, Double reading1, Double reading2, Double reading3, Double reading4, Double reading5, Double total, Double average) {
        this.runPercentile = runPercentile;
        this.action = action;
        this.reading1 = reading1;
        this.reading2 = reading2;
        this.reading3 = reading3;
        this.reading4 = reading4;
        this.reading5 = reading5;
        this.total = total;
        this.average = average;
    }

    public RunData() {}

    public RunPercentile getRunPercentile() {
        return runPercentile;
    }

    public void setRunPercentile(RunPercentile runPercentile) {
        this.runPercentile = runPercentile;
    }

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }

    public Double getReading1() {
        return reading1;
    }

    public void setReading1(Double reading1) {
        this.reading1 = reading1;
    }

    public Double getReading2() {
        return reading2;
    }

    public void setReading2(Double reading2) {
        this.reading2 = reading2;
    }

    public Double getReading3() {
        return reading3;
    }

    public void setReading3(Double reading3) {
        this.reading3 = reading3;
    }

    public Double getReading4() {
        return reading4;
    }

    public void setReading4(Double reading4) {
        this.reading4 = reading4;
    }

    public Double getReading5() {
        return reading5;
    }

    public void setReading5(Double reading5) {
        this.reading5 = reading5;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

}
