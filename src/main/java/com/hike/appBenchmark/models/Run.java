package com.hike.appBenchmark.models;

/**
 *
 * @author kumarpratyush
 *
 */

public class Run {

    private int runId;
    private String apkVersion;

    public Run(int runId, String apkVersion) {
        this.runId = runId;
        this.apkVersion = apkVersion;
    }

    public Run(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public Run() {

    }

    public int getRunId() {
        return runId;
    }
    public void setRunId(int runId) {
        this.runId = runId;
    }
    public String getApkVersion() {
        return apkVersion;
    }
    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }



}
