package com.hike.appBenchmark.diffStats;

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

import com.hike.appBenchmark.base.ExecuteShell;
import com.hike.appBenchmark.distributeTests.BenchmarkProcessDistributeUtils;
import com.hike.appBenchmark.utils.IOUtils;
import com.hike.appBenchmark.utils.ShellUtil;

public class TestingProcessThread  implements Runnable {

    private MessageSource messageSource;

    public TestingProcessThread(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        String userName = System.getProperty(messageSource.getMessage("user.name.command", null, null));
        String fileNameForLogcat = messageSource.getMessage("home.path", null, null) + userName + messageSource.getMessage("apk.path", null, null) + "/testing/logcatLogs.txt";

        ExecuteShell shell = new ExecuteShell();
        shell.executeCommand("adb logcat -c");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        BenchmarkLogcatThread logcatThread = new BenchmarkLogcatThread(fileNameForLogcat, messageSource);
        executorService.submit(logcatThread);
        executorService.shutdown();

        List<String> commandParams =  new ArrayList<String>();
        String pathOfSrcJar = "/Users/" + userName + "/Documents/workspace/BenchmarkDevice/bin/";

        BenchmarkProcessDistributeUtils benchmarkProcessUtils = new BenchmarkProcessDistributeUtils(messageSource);
        benchmarkProcessUtils.pushFile(pathOfSrcJar, messageSource.getMessage("data.local.tmp", null, null), "src.jar");

        commandParams.clear();
        commandParams.add(getProperty("adb.command"));
        commandParams.add(getProperty("adb.shell"));
        commandParams.add(getProperty("uiautomator.command"));
        commandParams.add(getProperty("run.test.command"));
        commandParams.add(getProperty("src.jar.command"));
        commandParams.add(getProperty("bash.flag.c"));
        commandParams.add("com.hike.appBenchmark.distributeTests.AdbloglineTest");

        try {
            IOUtils.convertBRtoString(ShellUtil.executeShellCommand(commandParams.toArray(new String[commandParams.size()])));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private String getProperty(String code) {
        String message = this.messageSource.getMessage(code, null, null);
        return message;
    }

}

class BenchmarkLogcatThread extends Thread {

    public boolean testComplete = false;
    private String fileName;
    private MessageSource messageSource;

    public BenchmarkLogcatThread(String fileName, MessageSource messageSource) {
        this.fileName = fileName;
        this.messageSource = messageSource;
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

            String userName = System.getProperty(messageSource.getMessage("user.name.command", null, null));

            Process process = Runtime.getRuntime().exec("/Users/" + userName + "/Documents/Setup/adt-bundle-mac-x86_64-20130911/sdk/platform-tools/adb logcat -s piyush:*");
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
                    if(line.endsWith("false")) {
                        testComplete = true;
                    }
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

