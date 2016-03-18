package com.hike.appBenchmark.distributeTests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.MessageSource;

import com.hike.appBenchmark.benchmarkProcess.BenchmarkDao;
import com.hike.appBenchmark.models.Percentile;
import com.hike.appBenchmark.models.Run;
import com.hike.appBenchmark.models.RunPercentile;
import com.hike.appBenchmark.utils.IOUtils;
import com.hike.appBenchmark.utils.PushReadingsToDbThread;
import com.hike.appBenchmark.utils.ShellUtil;

/**
 * Threads to handle distribution of tests
 * @author kumarpratyush
 *
 */
public class BenchmarkProcessInDeviceDistributor implements Runnable {

    private Percentile percentile;
    private BenchmarkProcessDistributeUtils benchmarkProcessUtils = new BenchmarkProcessDistributeUtils();
    private MessageSource messageSource;
    private int runId;
    private BenchmarkDao benchmarkDao;

    public BenchmarkProcessInDeviceDistributor(Percentile percentile, MessageSource messageSource, int runId, BenchmarkDao benchmarkDao) {
        this.percentile = percentile;
        this.messageSource = messageSource;
        this.runId = runId;
        this.benchmarkDao = benchmarkDao;
    }

    @Override
    public void run() {

        //start logcat thread
        String userName = System.getProperty(messageSource.getMessage("user.name.command", null, null));
        String sharedDirectory = messageSource.getMessage("home.path", null, null) + userName + messageSource.getMessage("apk.path", null, null) + "/";
        String fileNameForLogcat = sharedDirectory + runId + "/LogcatLogs_" + percentile.getPercentile() + ".txt";
        //make entry to run percentile table
        Run runObject = new Run();
        runObject.setRunId(runId);
        RunPercentile runPercentileObject = new RunPercentile(runObject, percentile);
        benchmarkDao.insertIntoRunPercentile(runPercentileObject);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        BenchmarkLogcatThread logcatThread = new BenchmarkLogcatThread(fileNameForLogcat);
        executorService.submit(logcatThread);

        //  benchmarkProcessUtils.setProperties(String.valueOf(percentile.getPercentile()));
        benchmarkProcessUtils.pushBackupFile(String.valueOf(percentile.getPercentile()), runId, percentile.getMsisdn());


        try {
            //start process for ui run
            //push test code
            List<String> commandParams =  new ArrayList<String>();
            commandParams.add(messageSource.getMessage("adb.command", null, null));
            commandParams.add(messageSource.getMessage("adb.push", null, null));
            String pathOfSrcJar = "/Users/kumarpratyush/Documents/marsWorkspace/BenchmarkDevice/bin/src.jar";
            commandParams.add(pathOfSrcJar);
            commandParams.add( messageSource.getMessage("data.local.tmp", null, null));
            String finalCommand = "";
            for(String eachCommandPart : commandParams) {
                finalCommand = finalCommand.concat(eachCommandPart).concat(" ");
            }
            ShellUtil.executeCommand(finalCommand);

            //set params to run test in device
            commandParams.clear();
            commandParams.add(getProperty("adb.command"));
            commandParams.add(getProperty("adb.shell"));
            commandParams.add(getProperty("uiautomator.command"));
            commandParams.add(getProperty("run.test.command"));
            commandParams.add(getProperty("src.jar.command"));
            commandParams.add(getProperty("bash.flag.c"));
            commandParams.add("com.hike.appBenchmark.distributeTests.BenchmarkProcessInDevice");
            commandParams.add("-e");
            commandParams.add("percentile");
            commandParams.add(String.valueOf(percentile.getPercentile()));
            commandParams.add("-e");
            commandParams.add("msisdn");
            commandParams.add(percentile.getMsisdn());
            commandParams.add("-e");
            commandParams.add("msisdn_to_test");
            commandParams.add(percentile.getChatMsisdn());
            IOUtils.convertBRtoString(ShellUtil.executeShellCommand(commandParams.toArray(new String[commandParams.size()])));

        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("came here");
        logcatThread.setFlag();
        System.out.println(logcatThread.testComplete);

        //spawn a thread to write readings to database
        ExecutorService readingsToDBService = Executors.newSingleThreadExecutor();
        PushReadingsToDbThread readingsToDb = new PushReadingsToDbThread(fileNameForLogcat, benchmarkDao, runPercentileObject);
        readingsToDBService.execute(readingsToDb);
    }

    private String getProperty(String code) {
        String message = this.messageSource.getMessage(code, null, null);
        return message;
    }

}


class BenchmarkLogcatThread extends Thread {

    public boolean testComplete = false;
    private String fileName;

    public BenchmarkLogcatThread(String fileName) {
        this.fileName = fileName;
    }

    public void setFlag() {
        testComplete = true;
    }


    @Override
    public void run() {

        //run buffer to write all data to logcat file
        try {
            File logFile = new File(fileName);
            if(logFile.exists()) {
                logFile.delete();
            }

            Process process = Runtime.getRuntime().exec("/Users/kumarpratyush/Documents/Setup/adt-bundle-mac-x86_64-20130911/sdk/platform-tools/adb logcat -s appOpeningBenchmark:* chatOpeningBenchmark:* chatScrollMsgDBBenchmark:* composeOpeningBenchmark:*");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder log=new StringBuilder();
            String line = null;
            Thread.currentThread();
            while (!testComplete) {
                line = null;
                if(bufferedReader.ready()) {
                    line = bufferedReader.readLine();
                    log.append("\n" + line);
                }
            }
            bufferedReader.close();

            BufferedWriter bwr = new BufferedWriter(new FileWriter(logFile));
            bwr.write(log.toString());
            bwr.flush();
            bwr.close();

        } catch(Exception e) {
            System.out.println("Not able to write logs to file");

            e.printStackTrace();
        }

    }
}

