package com.hike.appBenchmark.diffStats;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.hike.appBenchmark.reports.ReportDao;
import com.hike.appBenchmark.reports.ReportService;

/**
 * controller class for getting difference stats from base apk
 * @author kumarpratyush
 *
 */

@Controller
@RequestMapping("/diffStats")
public class DiffStatsContoller {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private DiffDao diffDao;

    @RequestMapping(value = "")
    public String getApksForDropdown(Model model) {

        List<String> allAvailableApks = reportService.getAllAvailableApks();
        model.addAttribute("apks", allAvailableApks);
        return "diffStats";
    }

    @SuppressWarnings({"unchecked"})
    @RequestMapping(value = "/getDiffStats", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
    consumes =MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JSONObject getDataForDiff(@RequestBody JSONObject selectedConfiguration) {

        JSONObject response = new JSONObject();
        String baseApk = "4.0.8.81";
        String apk = selectedConfiguration.get("apk").toString();

        //get data for that apk
        try {
        int runIdForApk = reportDao.getRunIdFromApk(apk).get(0).getRunId();
        int runIdForBaseApk = reportDao.getRunIdFromApk(baseApk).get(0).getRunId();

        List<RunData> dataForApk = diffDao.getDataForAllPercentileForApk(String.valueOf(runIdForApk));
        List<RunData> dataForBase = diffDao.getDataForAllPercentileForApk(String.valueOf(runIdForBaseApk));

        Map<String, Double> mapForApk = getDataInMap(dataForApk);
        Map<String, Double> mapForBaseApk = getDataInMap(dataForBase);

        //final push to response packet
        for(Entry<String, Double> mapIterator : mapForApk.entrySet()) {
            Double baseValue = mapForBaseApk.get(mapIterator.getKey());
            Double newApkValue = mapIterator.getValue();
            Double diff = ((newApkValue - baseValue)/baseValue) * 100D;
            Double toBeTruncated = new Double(diff);
            Double truncatedDouble = new BigDecimal(toBeTruncated).setScale(2, BigDecimal.ROUND_HALF_UP)
                    .doubleValue();
            String diffVal = "";
            if(truncatedDouble >= 0D) {
                diffVal = "+" + truncatedDouble + "%";
            } else {
                diffVal = truncatedDouble + "%";
            }

            response.put(mapIterator.getKey(), diffVal);
        }
        } catch(Exception e) {
            System.out.println("Error while trying to get diff data");
            e.printStackTrace();
        }

        return response;
    }

    private Map<String, Double> getDataInMap(List<RunData> dataList) {

        Map<String, Double> dataInMap = new HashMap<String, Double>();
        try {
            for(RunData eachData : dataList) {
                String percentile = String.valueOf(eachData.getRunPercentile().getRunPercentile().getPercentile());
                if(eachData.getAction().getActions().equalsIgnoreCase("App Force Stop")) {
                    dataInMap.put(percentile+"OC", eachData.getAverage());
                } else if(eachData.getAction().getActions().equalsIgnoreCase("App Force Kill")) {
                    dataInMap.put(percentile+"OCR", eachData.getAverage());
                } else if(eachData.getAction().getActions().equalsIgnoreCase("Chat Thread Opening")) {
                    dataInMap.put(percentile+"CO", eachData.getAverage());
                } else if(eachData.getAction().getActions().equalsIgnoreCase("Chat Thread Scroll")) {
                    dataInMap.put(percentile+"CS", eachData.getAverage());
                } else if(eachData.getAction().getActions().equalsIgnoreCase("Contact Loading time in Compose screen")) {
                    dataInMap.put(percentile+"COS", eachData.getAverage());
                }
            }
        } catch(Exception e) {
            System.out.println("Not able to populate diff, probably the benchmarking of apk not done yet");
            e.printStackTrace();
        }
        return dataInMap;
    }

}
