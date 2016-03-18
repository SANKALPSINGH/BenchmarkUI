package com.hike.appBenchmark.distributeTests;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import com.hike.appBenchmark.base.ExecuteShell;

public class BenchmarkProcessDistributeUtils {

    public void setProperties(String valueToSet) {


        try {
            FileInputStream in = new FileInputStream("local.properties");
            Properties props = new Properties();
            props.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream("local.properties");
            props.setProperty("percentile", valueToSet);
            props.store(out, null);
            out.close();
        } catch(Exception e) {
            System.out.println("Not able to set properties.");
            e.printStackTrace();
        }
    }

    public void pushBackupFile(String benchmarkOnPercentile, int runId, String msisdn) {

        //TODO
        String defaultPathSuffix = "percentile_" + msisdn + "/";
        String pathToPushBackupFileTo = "/storage/sdcard0/Hike/Backup/";
        String appendageForBackupPath = "Backup/";
        //String appendageForStickersPath = "Stickers/";
        try {
            String pathForPerfDataPrefix = "/Users/kumarpratyush/Documents/workspace/Perf_test_data/";
            String finalPathForPerfData = pathForPerfDataPrefix + benchmarkOnPercentile + defaultPathSuffix + appendageForBackupPath;

            //delete pre existing files
            deleteAllResources();

            //push all resources

            pushFile(finalPathForPerfData, pathToPushBackupFileTo, "chats.backup");
            pushFile(finalPathForPerfData, pathToPushBackupFileTo, "pref.backup");
            pushFile(finalPathForPerfData, pathToPushBackupFileTo, "data");
            pushFile("/Users/kumarpratyush/Desktop/appbenchmark/"+runId + "/", "/data/local/tmp/", "android-client.apk");
            pushFile("", "/data/local/tmp/", "local.properties");
            pushFile(pathForPerfDataPrefix + benchmarkOnPercentile + defaultPathSuffix, "/data/local/tmp", "Contacts.vcf");
            //pushStickers(pathForPerfDataPrefix + benchmarkOnPercentile + defaultPathSuffix + appendageForStickersPath, "/data/local/tmp/Stickers/", "");

        } catch(Exception e) {
            System.out.println("Not able to push backup file to device");
            e.printStackTrace();
        }

    }

    public void deleteAllResources() {

        System.out.println("Deleting all pre existing resources!");
        String pathToBackupFiles = "/storage/sdcard0/Hike/Backup/";
        String pathForResources = "/data/local/tmp/";

        try {
            //use adb shell rm -rf
            ExecuteShell executeShellCommand = new ExecuteShell();
            executeShellCommand.ExecuteShellCommand("/Users/kumarpratyush/Documents/Setup/adt-bundle-mac-x86_64-20130911/sdk/platform-tools/adb shell rm -rf", pathToBackupFiles);
            executeShellCommand.ExecuteShellCommand("/Users/kumarpratyush/Documents/Setup/adt-bundle-mac-x86_64-20130911/sdk/platform-tools/adb shell mkdir", pathToBackupFiles);
            executeShellCommand.ExecuteShellCommand("/Users/kumarpratyush/Documents/Setup/adt-bundle-mac-x86_64-20130911/sdk/platform-tools/adb shell rm -rf", pathForResources + "hike.apk");
            executeShellCommand.ExecuteShellCommand("/Users/kumarpratyush/Documents/Setup/adt-bundle-mac-x86_64-20130911/sdk/platform-tools/adb shell rm -rf", pathForResources + "Contacts.vcf");
            executeShellCommand.ExecuteShellCommand("/Users/kumarpratyush/Documents/Setup/adt-bundle-mac-x86_64-20130911/sdk/platform-tools/adb shell rm -rf", pathForResources + "local.properties");
            executeShellCommand.ExecuteShellCommand("/Users/kumarpratyush/Documents/Setup/adt-bundle-mac-x86_64-20130911/sdk/platform-tools/adb shell rm -rf", pathForResources + "Stickers/");

        } catch(Exception e) {
            System.out.println("Unable to push file inside device");
            e.printStackTrace();
        }
    }

    public void pushFile(String source, String destination, String fileName) {
        System.out.println("Pushing file inside device");

        try {
            //use adb push
            ExecuteShell executeShellCommand = new ExecuteShell();
            executeShellCommand.ExecuteShellCommand("/Users/kumarpratyush/Documents/Setup/adt-bundle-mac-x86_64-20130911/sdk/platform-tools/adb push", source+fileName, destination);

        } catch(Exception e) {
            System.out.println("Unable to push file inside device");
            e.printStackTrace();
        }
    }

}
