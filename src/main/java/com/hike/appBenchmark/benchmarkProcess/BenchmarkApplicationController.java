/**
 *
 */
package com.hike.appBenchmark.benchmarkProcess;



import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hike.appBenchmark.jenkins.JenkinsService;
import com.hike.appBenchmark.models.Run;
import com.hike.appBenchmark.models.RunData;

/**
 * @author kumarpratyush
 * Class contains test cases to log benchmark data
 *
 */

@Controller
@RequestMapping("/triggerBenchmark")
public class BenchmarkApplicationController {

    @Autowired
    private BenchmarkDao benchmarkDao;

    @Autowired
    private JenkinsService jenkinsService;

    @Autowired
    private MessageSource messageSource;


    @RequestMapping(value = "")
    public String displayTriggerRun(Model model) {

        return "runtest";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/newBenchmarkRun", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
    consumes =MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JSONObject newBenchmarkRun(@RequestBody JSONObject selectedConfiguration) {

        JSONObject response = new JSONObject();
        String apkBranch = selectedConfiguration.get("apkBranch").toString();
        int runId = 0;
        String apkVersion = "0";
        try {
            // validate if correct form
            if(apkBranch == null || apkBranch.isEmpty()) {
                //invalid form. Send back error
                response.put("errorField", "No branch name provided to generate apk from.");
                response.put("runId", runId);
                return response;
            }

            //insert into run table
            Run runObject = new Run(apkVersion);
            benchmarkDao.insertRun(runObject);
            runId = runObject.getRunId();

            //fire thread to build apk from
            ExecutorService buildApkExecutorService = Executors.newFixedThreadPool(1);

            Runnable buildApkThread = new BuildApkThread(apkBranch, runId, jenkinsService, messageSource, benchmarkDao);
            buildApkExecutorService.execute(buildApkThread);
            buildApkExecutorService.shutdown();

        } catch(Exception e) {
            System.out.println("Error came while running benchmark process");
            e.printStackTrace();
        }
        response.put("runId", runId);
        return response;
    }

    @RequestMapping(value="/fixResults")
    public String fixResults(Model model) {

        List<RunData> alldata = benchmarkDao.getAllWantedValues();
        for(RunData eachData : alldata) {
            int id = eachData.getRunPercentile().getRunPercentileId();
            Double newTotal = eachData.getReading1() + eachData.getReading2() + eachData.getReading3() + eachData.getReading4() + eachData.getReading5();
            Double newAverage = newTotal/5.0;
            String action = eachData.getAction().getActions();
            benchmarkDao.updateData(newTotal, newAverage, id, action);
        }
        return "runTest";
    }

}
