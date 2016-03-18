package com.hike.appBenchmark.reports;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.hike.appBenchmark.models.Actions;
import com.hike.appBenchmark.models.Run;

public class ReportServiceImpl implements ReportService {

    private ReportDao reportDao;

    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    public List<String> getAllAvailableActions() {

        List<String> allActions = new ArrayList<String>();
        List<Actions> allActionsObjects = reportDao.getAllActions();
        if(allActionsObjects != null && !allActionsObjects.isEmpty()) {
            for(Actions eachAction : allActionsObjects) {
                allActions.add(eachAction.getActions());
            }
        }
        return allActions;
    }

    public List<String> getAllAvailableApks() {

        List<String> allApks = new ArrayList<String>();
        List<Run> allApkObjects = reportDao.getAllApksAvailable();
        if(allApkObjects != null && !allApkObjects.isEmpty()) {
            for(Run eachApk : allApkObjects) {
                allApks.add(eachApk.getApkVersion());
            }
        }
        return allApks;
    }

    @SuppressWarnings("unchecked")
    public void prepareDefaultResponseStructure(JSONObject response) {

        String[] percentiles = {"50", "80", "95", "99"};

        //add cols
        JSONArray cols = new JSONArray();
        JSONObject colHeader = new JSONObject();
        colHeader.put("label", "Percentile");
        colHeader.put("type", "string");
        cols.add(colHeader);
        response.put("cols", cols);
        //add rows
        JSONArray rows = new JSONArray();
        for(int i=0; i < percentiles.length; i++) {
            JSONArray percentileData = new JSONArray();
            JSONObject percentileLabel = new JSONObject();
            percentileLabel.put("v", percentiles[i]);
            percentileData.add(percentileLabel);
            JSONObject rowData = new JSONObject();
            rowData.put("c", percentileData);
            rows.add(rowData);
        }
        response.put("rows", rows);
    }

}
