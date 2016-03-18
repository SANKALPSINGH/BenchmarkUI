package com.hike.appBenchmark.reports;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hike.appBenchmark.models.RunData;

/**
 * Controller to view reports in graphical manner
 * @author kumarpratyush
 *
 */

@Controller
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportDao reportDao;

    @RequestMapping(value = "/")
    public String getDataForReport(Model model) {

        List<String> allAvailableActions = reportService.getAllAvailableActions();
        List<String> allAvailableApks = reportService.getAllAvailableApks();
        model.addAttribute("actions", allAvailableActions);
        model.addAttribute("apks", allAvailableApks);
        return "report";
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(value = "/getDataForGraph", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
    consumes =MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JSONObject getDataForGraph(@RequestBody JSONObject selectedConfiguration) {

        JSONObject response = new JSONObject();
        String baseApk = "4.0.8.81";
        String[] listOfApks;

        //get data for drawing graph based on given parameters
        String action = selectedConfiguration.get("action").toString();
        String allApk = selectedConfiguration.get("apk").toString();

        try {
            if(allApk.contains(",")) {
                //at least one apk selected
                listOfApks = allApk.split(",");
            } else {
                listOfApks = new String[1];
                listOfApks[0] = baseApk;
            }
            List<String> apksList = new ArrayList(Arrays.asList(listOfApks));
            if(!apksList.isEmpty()) {
                apksList.remove(0);
            }
            //add base apk if not added already
            if(!(apksList.contains(baseApk))) {
                apksList.add(baseApk);
            }

            //prepare default structure of the response packet
            reportService.prepareDefaultResponseStructure(response);
            JSONArray cols = (JSONArray) response.get("cols");
            JSONArray rows = (JSONArray) response.get("rows");
            //get data from db for each percentile
            //get data for each percentile
            for(String eachApk : apksList) {
                int runIdForApk = reportDao.getRunIdFromApk(eachApk).get(0).getRunId();
                List<RunData> runDataForApk = reportDao.getDataForApkAndAction(String.valueOf(runIdForApk), action);
                //add apk version to cols
                JSONObject colData = new JSONObject();
                colData.put("label", eachApk);
                colData.put("type", "number");
                cols.add(colData);

                //add data in rows
                for(int i = 0; i < runDataForApk.size(); i++) {
                    if(runDataForApk.get(i).getRunPercentile().getRunPercentile().getPercentile() == 50) {
                        if(runDataForApk != null && runDataForApk.size() >= i) {
                            addDataToAptCol(0, runDataForApk.get(i).getAverage(), rows);
                        } else {
                            addDataToAptCol(0, 0D, rows);
                        }
                    } else if(runDataForApk.get(i).getRunPercentile().getRunPercentile().getPercentile() == 80) {
                        if(runDataForApk != null && runDataForApk.size() >= i) {
                            addDataToAptCol(1, runDataForApk.get(i).getAverage(), rows);
                        } else {
                            addDataToAptCol(1, 0D, rows);
                        }
                    } else if(runDataForApk.get(i).getRunPercentile().getRunPercentile().getPercentile() == 95) {
                        if(runDataForApk != null && runDataForApk.size() >= i) {
                            addDataToAptCol(2, runDataForApk.get(i).getAverage(), rows);
                        } else {
                            addDataToAptCol(2, 0D, rows);
                        }
                    } else if(runDataForApk.get(i).getRunPercentile().getRunPercentile().getPercentile() == 99) {
                        if(runDataForApk != null && runDataForApk.size() >= i) {
                            addDataToAptCol(3, runDataForApk.get(i).getAverage(), rows);
                        } else {
                            addDataToAptCol(3, 0D, rows);
                        }
                    }
                }

            }
        } catch(Exception e) {
            System.out.println("Error while fetching data");
            e.printStackTrace();
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    private void addDataToAptCol(int index, Double value, JSONArray rows) {
        JSONObject aptRow = (JSONObject) rows.get(index);
        JSONObject percentileData = new JSONObject();
        percentileData.put("v", value);
        JSONArray tagForData = (JSONArray)aptRow.get("c");
        tagForData.add(percentileData);
    }

}
