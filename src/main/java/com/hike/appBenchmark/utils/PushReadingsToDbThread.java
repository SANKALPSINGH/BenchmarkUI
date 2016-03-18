package com.hike.appBenchmark.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.hike.appBenchmark.benchmarkProcess.BenchmarkDao;
import com.hike.appBenchmark.models.Actions;
import com.hike.appBenchmark.models.RunData;
import com.hike.appBenchmark.models.RunPercentile;

public class PushReadingsToDbThread implements Runnable {

    private String fileName;
    private BenchmarkDao benchmarkDao;
    private RunPercentile runPercentile;

    public PushReadingsToDbThread(String fileName, BenchmarkDao benchmarkDao, RunPercentile runPercentile) {
        this.fileName = fileName;
        this.benchmarkDao = benchmarkDao;
        this.runPercentile = runPercentile;
    }

    @Override
    public void run() {

        //read file and push readings to db
        List<Double> chatOpeningReadings = new ArrayList<Double>();
        List<Double> chatScrollingReadings = new ArrayList<Double>();
        List<Double> appOpeningReadings = new ArrayList<Double>();
        List<Double> killAppOpeningReadings = new ArrayList<Double>();
        List<Double> composeOpeningReadings = new ArrayList<Double>();
        List<Double> killAppResumeOpeningReadings = new ArrayList<Double>();

        int counterOfChatOpening = 0;
        int counterOfChatScrolling = 0;
        int counterOfAppOpening = 0;
        int counterOfComposeOpening = 0;
        boolean readingCaptured = false;
        boolean chatOpeningFinished = false;
        boolean chatScrollingFinished = false;
        boolean appOpeningFinished = false;
        boolean composeOpeningFinished = false;
        boolean appCreated = false;
        boolean eachScrollStarted = false;
        double startValue = 0D;
        double endValue = 0D;
        double timeTakenToOpenChat = 0D;
        double timeTakenToOpenComposeScreen = 0D;
        double totalTimeTakeForChatOpening = 0D;
        double totalTimeForChatScrolling = 0D;
        double totalTimeTakenForComposeOpening = 0D;
        double eachScrollValue = 0D;
        double totalTimeForAppOpening = 0D;
        int counterOfAppKillOpening = 0;
        double totalTimeTakeForAppCreate = 0D;
        double totalTimeTakenForAppResume = 0D;
        int counter = 0;
        double onCreateValue = 0D;
        double onCreateAndResumeValue = 0D;

        try {
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                if(line.contains("chatOpeningBenchmark") && counterOfChatOpening <= 5) {
                    //check if start or end
                    String[] lineSplit = line.split("=");
                    if(line.contains("start")) {
                        startValue = Double.parseDouble(lineSplit[lineSplit.length - 1].trim());
                        counter++;
                    } else if(line.contains("end") && counter != 0) {
                        endValue = Double.parseDouble(lineSplit[lineSplit.length - 1].trim());
                        readingCaptured = true;
                    }
                    if(readingCaptured) {
                        timeTakenToOpenChat = endValue - startValue;
                        if(!chatOpeningReadings.isEmpty()) {
                            totalTimeTakeForChatOpening += timeTakenToOpenChat;
                        }
                        readingCaptured = false;
                        chatOpeningReadings.add(timeTakenToOpenChat);
                        counterOfChatOpening++;
                    }
                    if(counterOfChatOpening > 5) {
                        chatOpeningFinished = true;
                        chatOpeningReadings.remove(0);
                    }
                }

                //readings for scrolling
                if(chatOpeningFinished) {
                    Double scrollValue = 0D;
                    if(line.contains("chatScrollMsgDBBenchmark") && counterOfChatScrolling < 5) {

                        eachScrollStarted = true;
                        String[] lineSplit = line.split("=");
                        scrollValue = Double.parseDouble(lineSplit[lineSplit.length - 1].trim());
                        eachScrollValue += scrollValue;
                    } else if(eachScrollStarted && line.contains("appOpeningBenchmark")) {
                        //all readings have been captured. Now push the value
                        eachScrollStarted = false;
                        totalTimeForChatScrolling += eachScrollValue;
                        chatScrollingReadings.add(eachScrollValue);
                        counterOfChatScrolling++;
                        eachScrollValue = 0D;
                    } if(counterOfChatScrolling >= 5) {
                        chatScrollingFinished = true;
                        chatOpeningFinished = false;
                        counterOfChatOpening = 0;
                        continue;
                    }
                }

                //readings for app opening benchmark
                if(chatScrollingFinished) {
                    if(line.contains("appOpeningBenchmark") && counterOfAppOpening < 5) {
                        //check if start or end
                        String[] lineSplit = line.split("=");
                        if(line.contains("onCreate and onResume")) {
                            Double appOpeningValue = Double.parseDouble(lineSplit[lineSplit.length - 1].trim());
                            readingCaptured = true;
                            if(readingCaptured) {
                                readingCaptured = false;
                                totalTimeForAppOpening += appOpeningValue;
                                appOpeningReadings.add(appOpeningValue);
                                counterOfAppOpening++;
                            }
                        }
                    } if(counterOfAppOpening >= 5) {
                        chatScrollingFinished = false;
                        appOpeningFinished = true;
                    }
                }

                //readings for kill app and then opening benchmark
                if(appOpeningFinished) {
                    if(line.contains("appOpeningBenchmark") && counterOfAppKillOpening < 5) {
                        //check if start or end

                        String[] lineSplit = line.split("=");
                        if(line.contains("HikeMessengerApp onCreate")) {
                            onCreateValue = Double.parseDouble(lineSplit[lineSplit.length - 1].trim());
                            appCreated = true;
                        } else if(line.contains("onCreate and onResume") && appCreated) {
                            onCreateAndResumeValue = Double.parseDouble(lineSplit[lineSplit.length - 1].trim());
                            readingCaptured = true;
                            appCreated = false;
                        }
                        if(readingCaptured) {
                            totalTimeTakeForAppCreate += onCreateValue;
                            totalTimeTakenForAppResume += onCreateAndResumeValue;
                            readingCaptured = false;
                            killAppOpeningReadings.add(onCreateValue);
                            killAppResumeOpeningReadings.add(onCreateAndResumeValue);
                            counterOfAppKillOpening++;
                        }
                    } if(counterOfAppKillOpening >=5) {
                        appOpeningFinished = false;
                        composeOpeningFinished = true;
                        counter = 0;
                    }
                }

                //readings for compose screen
                if(composeOpeningFinished) {
                    if(line.contains("composeOpeningBenchmark") && counterOfComposeOpening < 5) {
                        //check if start or end
                        String[] lineSplit = line.split("=");
                        if(line.contains("start")) {
                            startValue = Double.parseDouble(lineSplit[lineSplit.length - 1].trim());
                            counter++;
                        } else if(line.contains("end") && counter != 0) {
                            endValue = Double.parseDouble(lineSplit[lineSplit.length - 1].trim());
                            readingCaptured = true;
                        }
                        if(readingCaptured) {
                            timeTakenToOpenComposeScreen = endValue - startValue;
                            totalTimeTakenForComposeOpening += timeTakenToOpenComposeScreen;
                            System.out.println("total time after each reading : " + totalTimeTakenForComposeOpening);
                            readingCaptured = false;
                            composeOpeningReadings.add(timeTakenToOpenComposeScreen);
                            counterOfComposeOpening++;
                        }
                    } if(counterOfComposeOpening >= 5) {
                        chatOpeningFinished = false;
                    }
                }
            }
            br.close();

            //calculate average and push to map
            chatOpeningReadings.add(totalTimeTakeForChatOpening);
            chatOpeningReadings.add(totalTimeTakeForChatOpening/5D);
            chatScrollingReadings.add(totalTimeForChatScrolling);
            chatScrollingReadings.add(totalTimeForChatScrolling/5D);
            appOpeningReadings.add(totalTimeForAppOpening);
            appOpeningReadings.add(totalTimeForAppOpening/5D);
            killAppOpeningReadings.add(totalTimeTakeForAppCreate);
            killAppOpeningReadings.add(totalTimeTakeForAppCreate/5D);
            killAppResumeOpeningReadings.add(totalTimeTakenForAppResume);
            killAppResumeOpeningReadings.add(totalTimeTakenForAppResume/5D);
            composeOpeningReadings.add(totalTimeTakenForComposeOpening);
            composeOpeningReadings.add(totalTimeTakenForComposeOpening/5D);

            //push data to db
            List<Actions> allActions = benchmarkDao.getAllActions();
            for(Actions eachAction : allActions) {
                RunData runDataObject = new RunData();
                runDataObject.setRunPercentile(runPercentile);
                runDataObject.setAction(eachAction);
                if(eachAction.getActions().equalsIgnoreCase("Chat Opening")) {
                    prepareRunDataObject(runDataObject, chatOpeningReadings);
                } else if(eachAction.getActions().equalsIgnoreCase("Chat Scroll")) {
                    prepareRunDataObject(runDataObject, chatScrollingReadings);
                } else if(eachAction.getActions().equalsIgnoreCase("App Opening")) {
                    prepareRunDataObject(runDataObject, appOpeningReadings);
                } else if(eachAction.getActions().equalsIgnoreCase("App Kill and Reopen")) {
                    prepareRunDataObject(runDataObject, killAppOpeningReadings);
                } else if(eachAction.getActions().equalsIgnoreCase("Compose Screen Opening")) {
                    prepareRunDataObject(runDataObject, composeOpeningReadings);
                } else if(eachAction.getActions().equalsIgnoreCase("App Kill Resume")) {
                    prepareRunDataObject(runDataObject, killAppResumeOpeningReadings);
                }
                benchmarkDao.insertIntoRunData(runDataObject);
            }
        } catch(Exception e) {
            System.out.println("Error while pushing data to db");
            e.printStackTrace();
        }
    }

    private void prepareRunDataObject(RunData runDataObject, List<Double> data) {
        runDataObject.setReading1(data.get(0));
        runDataObject.setReading2(data.get(1));
        runDataObject.setReading3(data.get(2));
        runDataObject.setReading4(data.get(3));
        runDataObject.setReading5(data.get(4));
        runDataObject.setTotal(data.get(5));
        runDataObject.setAverage(data.get(6));
    }
}