package com.hike.appBenchmark.benchmarkProcess;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.MessageSource;

import com.hike.appBenchmark.distributeTests.BenchmarkProcessInDeviceDistributor;
import com.hike.appBenchmark.jenkins.JenkinsService;
import com.hike.appBenchmark.models.Percentile;
import com.hike.appBenchmark.models.Run;

public class BuildApkThread implements Runnable {

    private String apkBranch;
    private int runId;
    private JenkinsService jenkinsService;
    private MessageSource messageSource;
    private BenchmarkDao benchmarkDao;

    public BuildApkThread(String apkBranch, int runId, JenkinsService jenkinsService, MessageSource messageSource, BenchmarkDao benchmarkDao) {
        this.apkBranch = apkBranch;
        this.runId = runId;
        this.jenkinsService = jenkinsService;
        this.messageSource = messageSource;
        this.benchmarkDao = benchmarkDao;
    }

    public void run() {

        //TODO CHANGE TO FALSE
        boolean apkSuccess = true;

        //apkSuccess = jenkinsService.getLatestapk(apkBranch, runId);
        jenkinsService.moveApk(runId);
        if (apkSuccess) {
            setApkVersion(runId);

            ExecutorService runBenchmarkExecutorService = Executors.newFixedThreadPool(1);
            //get list of percentiles available
            List<Percentile> percentileObjects = benchmarkDao.getAllPercentiles();
            for(Percentile eachPercentile : percentileObjects) {
                if(eachPercentile.getPercentile() == 50) {
                    BenchmarkProcessInDeviceDistributor processInDevice = new BenchmarkProcessInDeviceDistributor(eachPercentile, messageSource, runId, benchmarkDao);
                    runBenchmarkExecutorService.execute(processInDevice);
                }
            }
            runBenchmarkExecutorService.shutdown();
        }//TODO handle failure part here
    }

    public String fetchApkVersion() {

        String apkVersion = null;
        try {
            String userName = System.getProperty(messageSource.getMessage("user.name.command", null, null));
            File folder = new File(messageSource.getMessage("home.path", null, null) + userName + messageSource.getMessage("jenkins.job.apk.path", null, null));
            //File folder = new File("/Users/kumarpratyush/Downloads/apks");
            File[] listOfApk = folder.listFiles();
            for (int i = 0; i < listOfApk.length; i++) {
                if (listOfApk[i].isFile()) {
                    String fileName = listOfApk[i].getName();
                    if (fileName.contains("obfuscated") && !(fileName.contains("customDev"))) {
                        apkVersion = fileName.split("-")[4].replace('_', '.');
                        return apkVersion;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Could not fetch apk version");
            e.printStackTrace();
        }
        return apkVersion;

    }

    public void setApkVersion(int runId){
        String buildVersion=fetchApkVersion();
        Run run= new Run();
        run.setRunId(runId);
        run.setApkVersion((buildVersion));
        benchmarkDao.updateBuildVersionForRun(run);
    }

}
