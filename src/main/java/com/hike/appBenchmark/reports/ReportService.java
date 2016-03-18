package com.hike.appBenchmark.reports;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

/**
 * interface for report service functions
 * @author kumarpratyush
 *
 */
@Service("reportService")
public interface ReportService {

    /**
     * Gets list of 'Action' objects and converts to String type
     * @return list of actions in string
     */
    List<String> getAllAvailableActions();

    /**
     * Gets list of all 'Run' objects from db, extracts apks and converts them to String type
     * @return list of apks in String type
     */
    List<String> getAllAvailableApks();

    /**
     * add the default tags for response data packet
     * @param response
     */
    void prepareDefaultResponseStructure(JSONObject response);

}
